DELIMITER //

drop procedure if exists `sp_COMCalcOrder`//

create procedure `sp_COMCalcOrder`(
	in `order_id` int,
	in `capacity` int,
	in `class_id` int,
	in `features_id` varchar(255),
    out `proposal_count` int) 
begin

	declare exit handler for sqlexception begin
		get diagnostics condition 1 @sqlstate = returned_sqlstate, @errno = mysql_errno, @text = message_text;
        rollback;
        set @full_error = concat('ERR:', @errno, '(', @sqlstate, '): + Error calculating order. + ', @text);
        resignal set mysql_errno = @errno, message_text = @full_error;
    end;
    
    start transaction;
    
-- -------------------------------------------
-- order is completted, and can't be edited --
-- -------------------------------------------
        
		if not ( select o.`cash` from `order` o where o.`id` = `order_id` ) is null then
			signal sqlstate '44000' set message_text = 'Forbidden to edit finilazied order';
        end if;
        
        if (select count(*) from `proposal` p where p.`order_id` = `order_id`) > 0 then
			signal sqlstate '44000' set message_text = 'Order is in the process elsewhere';
        end if;
        
        set @price = sf_COMCalcPrice(`order_id`);

-- ----------------------------------------------------------------------------------------------------------------------------------        
-- updated `order`.`price` must be equal to the `order`.`cash` (according to cars used in order) when customer commits(pays) order --
-- ----------------------------------------------------------------------------------------------------------------------------------
        
        update `order` o 
        set o.`price` = @price, o.`end_at` = adddate(o.`start_at`, interval @price minute) 
        where o.`id` = `order_id`;
        
-- -------------------------------------------------------
-- parsing string of feature's id into temporarry table --
-- -------------------------------------------------------

        create temporary table if not exists `#requested_feature`(`id` int);
        delete from `#requested_feature`;
        
        set @src = concat(`features_id`, ',');
        set @features_count = 0;

        set @i = locate(',', @src);
        
        set @sub_str = substring(@src, 1, @i - 1);
        
        while regexp_like(@sub_str, '^([0-9]{1,})$') > 0 do
            insert into `#requested_feature` (`id`) values (cast(@sub_str as unsigned));
            set @ii = locate(',', @src, @i + 1);
            set @sub_str = substring(@src, @i + 1, @ii - @i - 1);
            set @i = @ii;
            set @features_count = @features_count + 1;
        end while;
        
        set @start_at = (select od.`start_at` from `order` od where od.`id` = `order_id`);
        
        set @proposal_id = 0;

-- --------------------------------------------------------------------------------------
-- selecting one car according to the input parameters and which is available to order --
-- --------------------------------------------------------------------------------------
    
		insert into `proposal`(`proposal_id`, `order_id`, `car_id`, `time_to_wait`)(
        select @proposal_id, `order_id`, ca.`id`, null
		from
			`car` ca join `state` st on ca.`state_id` = st.`id`
			join `car_feature` cf on ca.`id` = cf.`car_id`
            join `#requested_feature` rf on cf.`feature_id` = rf.`id`
            left join (
				select ca.`id`
                from `car` ca join `executor` ex on ca.`id` = ex.`car_id`
                join `order` od on od.`id` = ex.`order_id`
                where @start_at between od.`start_at` and od.`end_at`) agg on ca.`id` = agg.`id`
        where
			ca.`capacity` >= `capacity` and ca.`class_id` = `class_id` and st.`description` = 'active' and agg.`id` is null
        group by ca.`id`
        having count(*) >= @features_count
        order by ca.`mark` desc
        limit 1);
        
		set @row_returned = (select count(*) from `proposal` p where p.`order_id` = `order_id`);

        if @row_returned = 0 or @row_returned is null then
        
			set @proposal_id = @proposal_id + 1;
-- ---------------------------------------------------------------------------------------------            
-- select one car(according to input parameters) from busy cars(in order) with `time_to_wait` --
-- ---------------------------------------------------------------------------------------------

            insert into `proposal`(`proposal_id`, `order_id`, `car_id`, `time_to_wait`) (
            select @proposal_id, `order_id`, ca.`id`, min(timediff(agg.`end_at`, @start_at))
            from
                `car` ca join `state` st on ca.`state_id` = st.`id`
				join `car_feature` cf on ca.`id` = cf.`car_id`
				join `#requested_feature` rf on cf.`feature_id` = rf.`id`
                join (
					select c.`id`, max(o.`end_at`) as `end_at`
					from `car` c join `executor` e on c.`id` = e.`car_id`
					join `order` o on e.`order_id` = o.`id`
					where @start_at between o.`start_at` and o.`end_at` or @start_at < o.`start_at`
					group by c.`id`
                ) agg on ca.`id` = agg.`id`
            where
                ca.`capacity` >= `capacity` and
                st.`description` = 'active'
            group by ca.`id`
            having count(*) >= @features_count
            order by max(timediff(agg.`end_at`, @start_at)) asc
            limit 1);
            
-- ---------------------------------------------------------------
-- select one car despite class from avalible(not in trip) cars --
-- ---------------------------------------------------------------
    
			SET @proposal_id = @proposal_id + 1;

			insert into `proposal`(`proposal_id`, `order_id`, `car_id`, `time_to_wait`)(
			select @proposal_id, `order_id`, ca.`id`, null
			from
				`car` ca join `state` st on ca.`state_id` = st.`id`
				join `car_feature` cf on ca.`id` = cf.`car_id`
				join `#requested_feature` rf on cf.`feature_id` = rf.`id`
				left join (
					select ca.`id`
					from `car` ca join `executor` ex on ca.`id` = ex.`car_id`
					join `order` od on od.`id` = ex.`order_id`
					where @start_at between od.`start_at` and od.`end_at`) agg on ca.`id` = agg.`id`
			where
				ca.`capacity` >= `capacity` and ca.`class_id` <> `class_id` and st.`description` = 'active' and agg.`id` is null
			group by ca.`id`
			having count(*) >= @features_count
			order by ca.`mark` desc
			limit 1);

-- -----------------------------------------------------------------------------------------------------------------------------            
-- searching and inserting to the `proposal` cars which are available and total capacity of which corresponds to required one --
-- -----------------------------------------------------------------------------------------------------------------------------
    
            set @proposal_id = @proposal_id + 1;

            insert into `proposal`(`proposal_id`, `order_id`, `car_id`, `time_to_wait`)
            (select @proposal_id, `order_id`, agg.`car_id`, null
				from (
						select ca.`id` as `car_id`,
							sum(ca.`capacity`) over() as `total`,
							sum(ca.`capacity`) over (order by ca.`id`, ca.`capacity`) as `cumul_total`
						from `car` ca join `state` st on ca.`state_id` = st.`id`
						left join (
							select ca.`id`
							from `car` ca join `executor` ex on ca.`id` = ex.`car_id`
							join `order` od on od.`id` = ex.`order_id`
							where @start_at between od.`start_at` and od.`end_at`) ag on ca.`id` = ag.`id`
						join `car_feature` cf on ca.`id` = cf.`car_id`
						join `#requested_feature` rf on cf.`feature_id` = rf.`id`
						where ca.`class_id` = `class_id` and st.`description` = 'active' and ag.`id` is null
						group by ca.`id`
						having count(*) >= @features_count
						limit 10
					) agg
				where ((agg.`cumul_total` - `capacity`) <= `capacity` ) and agg.`total` >= `capacity`);
            
            select * from `proposal`;
            
        end if;

-- -------------------------------------------------        
-- locking cars whichs are in process of ordering --
-- -------------------------------------------------
        
        update `car` c join `proposal` p on c.`id` = p.`car_id`
		set c.`state_id` = (select `id` from `state` where `description` = 'order')
        where p.`order_id` = `order_id`;
			
-- -------------------------------------------
-- selecting out parameter `proposal_count` --
-- -------------------------------------------
            
   		select count(*) into `proposal_count`
  		from `proposal` p join `car` c on p.`car_id` = c.`id`
   		where p.`order_id` = `order_id`;
        
        commit;
    
END//

DELIMITER ;
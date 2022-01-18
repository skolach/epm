        select
			null,
			p.`order_id`, 
			p.`proposal_id`, 
			group_concat(c.`number` separator ', ') as `number`,
			max(p.`time_to_wait`) as `time_to_wait`, 
			sum(o.`price`) as `price`,
            concat(max(cl.`name`), ' (', group_concat(fl.`description` separator ', '), ')') as `description`
        from `proposal` p join `car` c on p.`car_id` = c.`id` 
        join `order` o on p.`order_id` = o.`id` 
        join `class` cl on c.`class_id` = cl.`id`
        join `car_feature` cf on c.`id` = cf.`car_id`
        join `feature` f on cf.`feature_id` = f.`id`
        join `feature_localization` fl on f.`id` = fl.`feature_id`
        join `locale` l on fl.`locale_id` = l.`id`
        where p.`order_id` = 97 and l.`code` = 'uk_UA'
        group by p.`order_id`, p.`proposal_id`
        
        

        
select * from `car_feature`

select * from `proposal`

select * from `car`

delete from `proposal`; update `car` set `state_id` = 1



select agg.`car_id`
				from (
						select ca.`id` as `car_id`,
							sum(ca.`capacity`) over() as `total`,
							sum(ca.`capacity`) over (order by ca.`id`, ca.`capacity`) as `cumul_total`
						from `car` ca join `state` st on ca.`state_id` = st.`id`
						left join `executor` ex on ca.`id` = ex.`car_id`
						left join `order` od on od.`id` = ex.`order_id`
						join `car_feature` cf on ca.`id` = cf.`car_id`
						where
							ca.`class_id` = `class_id` and st.`description` = 'active'
							and (not '2021-09-30 16:30' between od.`start_at` and od.`end_at` or od.`id` is null)
						group by ca.`id`
						having count(*) >= 1
						limit 10
					) agg where ((agg.`cumul_total` - 4) <= 4 ) and agg.`total` >= 4
					-- ) agg where (4 between 0 and agg.`cumul_total` or agg.`cumul_total` <= 4 ) and agg.`total` >= 4



INSERT into `order`(`user_id`, `created_at`, `start_at`, `end_at`, `price`, `route_discount`, `user_discount`, `cash`)
VALUES (1, now(), now(), date_add(now(), interval 3 DAY_HOUR), null, 10, 20, null);

-- select * from `order`;

	insert into `route`(`order_id`, `point_order`, `point_name`)
		values (59, 1, '111');
	insert into `route`(`order_id`, `point_order`, `point_name`)
		values (59, 2, '222');



select * from `proposal`
select * from `car`
select * from `order`

				-- `order_id`, `capacity`, `class_id`, `features_id`
call sp_COMCalcOrder(62, 4, 1, '1');

delete from `proposal`; update `car` set `state_id` = 1;

select * from `order` o join `executor` e on o.`id` = e.`order_id` join `car` c on e.`car_id` = c.`id` order by o.`id`

delete from `order` 




delete from `proposal`; update `car` set `state_id` = (select s.`id` from `state` s where s.`description` = 'active' );

select * from `proposal`;

delete from `order` where `cash` is null

				-- `order_id`, `capacity`, `class_id`, `features_id`
call sp_COMCalcOrder(40,		5,			1,			'1');

call sp_COMCommitOrder(50, 0);

-- delete from `order`

select * from `proposal`;

select * from `order` o left join `executor` e	on o.`id` = e.`order_id`;
    
select * from `car` c join `car_feature` cf on c.`id` = cf.`car_id`;
    
update `car` set `class_id` = 1 where `id` = 3;

update `car` set `class_id` = 2 where `id` = 3;

insert into `car` (`number`, `capacity`, `mark`, `state_id`, `class_id`)
	VALUES('ppprraaa', 1, 3, 1, 1);
    
insert into `car_feature`(`car_id`, `feature_id`)
values(7,1);



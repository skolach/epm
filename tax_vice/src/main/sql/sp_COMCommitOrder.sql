DELIMITER //

DROP PROCEDURE IF EXISTS `sp_COMCommitOrder`//

CREATE PROCEDURE `sp_COMCommitOrder`(
	IN `order_id` INT,
	IN `proposal_id` INT
)
BEGIN

	DECLARE EXIT HANDLER FOR SQLEXCEPTION BEGIN
		GET DIAGNOSTICS CONDITION 1 @sqlstate = RETURNED_SQLSTATE, @errno = MYSQL_ERRNO, @text = MESSAGE_TEXT;
        ROLLBACK;
        SET @full_error = concat('ERR:', @errno, '(', @sqlstate, '): +Error commiting(finalizing) order. ', @text);
        RESIGNAL SET MYSQL_ERRNO = @errno, MESSAGE_TEXT = @full_error;
    END;
    
    START TRANSACTION;
    
		-- order is completted, and can't be edited
  
		IF NOT ( SELECT o.`cash` FROM `order` o WHERE o.`id` = @order_id ) IS NULL THEN
			SIGNAL SQLSTATE '44000' SET MESSAGE_TEXT = 'Forbidden to edit commited(finilazied) order';
        END IF;
        
        if not exists (select 1 from `proposal` p where p.`order_id` = `order_id`) then
			signal sqlstate '44000' set message_text = 'Nothing to commit. Maybe order was discarded due to time expired.';
        end if;
        
		set @cash = ( select sum(o.`price`)
			from `order` o join `proposal` p
				on o.`id` = p.`order_id`
			where p.`order_id` = `order_id` and p.`proposal_id` = `proposal_id`);
        
        set	@timeToWait = ( select p.`time_to_wait`
			from `order` o join `proposal` p
				on o.`id` = p.`order_id`
			where p.`order_id` = `order_id` and p.`proposal_id` = `proposal_id` limit 0,1);
            
        insert into `executor`(`order_id`, `car_id`)
        select 
			`order_id`,
            `car_id`
        from `proposal` p
        where p.`order_id` = `order_id` and p.`proposal_id` = `proposal_id`;
        
        select @cash, @timeToWait;
        
        update `car` c join `proposal` p
			on p.`car_id` = c.`id`
		set c.`state_id` = (select `id` from `state` where `description` = 'active')
        where p.`order_id` = `order_id`;
        
        delete
        from `proposal` p
        where p.`order_id` = `order_id`;
        
		if not @timeToWait is null then
        
			select addtime(o.`start_at`, @timeToWait) from `order` o where o.`id` = `order_id`;
            select addtime(o.`end_at`, @timeToWait) from `order` o where o.`id` = `order_id`;
        
        	update `order` o
			set o.`cash` = @cash,
				o.`start_at` = addtime(o.`start_at`, @timeToWait),
                o.`end_at` = addtime(o.`end_at`, @timeToWait)
			where o.`id` = `order_id`;
        else
			update `order` o
			set o.`cash` = @cash
			where o.`id` = `order_id`;
        end if;

	COMMIT;

END//

DELIMITER ;
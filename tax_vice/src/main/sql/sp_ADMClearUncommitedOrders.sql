DELIMITER //

drop procedure if exists `sp_ADMClearUncommitedOrders`//

create procedure `sp_ADMClearUncommitedOrders`()

begin

	start transaction;
    
    set @now = now();

	update `car` c join `proposal` p
		on c.`id` = p.`car_id`
	set c.`state_id` = (select s.`id` from `state` s where s.`description` = 'active')
	where @now > adddate(p.`created_at`, interval 10 second);

	delete o
	from `order` o join `proposal` p
		on o.`id` = p.`order_id`
	where o.`cash` is null and @now > adddate(p.`created_at`, interval 10 second);
    
    commit;

end//

DELIMITER ;
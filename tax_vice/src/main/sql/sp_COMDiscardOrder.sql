DELIMITER //

DROP PROCEDURE IF EXISTS `sp_COMDiscardOrder`//

CREATE PROCEDURE `sp_COMDiscardOrder`(
	IN `order_id` INT)
BEGIN

	DECLARE EXIT HANDLER FOR SQLEXCEPTION BEGIN
		GET DIAGNOSTICS CONDITION 1 @sqlstate = RETURNED_SQLSTATE, @errno = MYSQL_ERRNO, @text = MESSAGE_TEXT;
        ROLLBACK;
        SET @full_error = concat('ERR:', @errno, '(', @sqlstate, '): + Error discarding(rolback) order. + ', @text);
        RESIGNAL SET MYSQL_ERRNO = @errno, MESSAGE_TEXT = @full_error;
    END;
    
    START TRANSACTION;
    
		-- order is completted, and can't be edited
        
		IF NOT ( SELECT o.`cash` FROM `order` o WHERE o.`id` = `order_id` ) IS NULL THEN
			SIGNAL SQLSTATE '44000' SET MESSAGE_TEXT = 'Forbidden to remove commited(finilazied) order';
        END IF;
        
        update `car` c join `proposal` p
			on p.`car_id` = c.`id`
		set c.`state_id` = (select `id` from `state` where `description` = 'active')
        where p.`order_id` = `order_id`;
        
        delete
        from `proposal` p
        where p.`order_id` = `order_id`;
        
        delete
        from `order` o
        where o.`id` = `order_id`;

	COMMIT;

END//

DELIMITER ;
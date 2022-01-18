DELIMITER //

DROP FUNCTION IF EXISTS `sf_COMCalcPrice`//

CREATE FUNCTION `sf_COMCalcPrice`(`order_id` INT) RETURNS DECIMAL(10,2) DETERMINISTIC
BEGIN

	SET @len = sf_COMCalcRoute(`order_id`);
    
	RETURN (
        
		SELECT cast(@len  - (u.`discount`/ 100) * @len as DECIMAL(10,2))
        FROM `user` u JOIN `order` o
			ON u.`id` = o.`user_id`
		WHERE o.`id` = `order_id`);

END//

DELIMITER ;
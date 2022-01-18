DELIMITER //

DROP FUNCTION IF EXISTS `sf_COMCalcRoute`//

CREATE FUNCTION `sf_COMCalcRoute`(`order_id` INT) RETURNS INT DETERMINISTIC
BEGIN

	RETURN (SELECT sum(floor(1 + rand()*(50-1)))
	FROM `route` r
	WHERE r.`order_id` = `order_id`);

END//

DELIMITER ;
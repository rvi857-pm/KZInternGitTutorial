DELIMITER || 
drop function if exists getState;
CREATE FUNCTION getState ( buyerId varchar(100) )
	RETURNS varchar(100)
	READS SQL DATA
	BEGIN

		DECLARE stateValue varchar(100);
		set stateValue = (select state from geo where id = buyerId);
		RETURN stateValue;
		
	END || 
DELIMITER ;
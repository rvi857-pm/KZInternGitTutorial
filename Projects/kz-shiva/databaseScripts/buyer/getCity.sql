DELIMITER || 
drop function if exists getCity;
CREATE FUNCTION getCity ( buyerId varchar(100) )
	RETURNS varchar(100)
	READS SQL DATA
	BEGIN

		DECLARE cityValue varchar(100);

		call updateGeo(buyerId);
		set cityValue = (select city from geo where id = buyerId);
		RETURN cityValue;

	END || 
DELIMITER ;
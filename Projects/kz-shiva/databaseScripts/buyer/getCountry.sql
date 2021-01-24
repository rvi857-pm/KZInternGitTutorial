DELIMITER || 
drop function if exists getCountry;
CREATE FUNCTION getCountry ( buyerId varchar(100) )
	RETURNS varchar(100)
	READS SQL DATA
	BEGIN

		DECLARE countryValue varchar(100);
		set countryValue = (select country from geo where id = buyerId);
		RETURN countryValue;
		
	END || 
DELIMITER ;
DELIMITER || 
drop function if exists getSource;
CREATE FUNCTION getSource ( buyerId varchar(100) )
	RETURNS varchar(100)
	READS SQL DATA
	BEGIN

		set @sourceValue = (select source from variables where id = buyerId);
		RETURN @sourceValue;

	END ||
DELIMITER ;
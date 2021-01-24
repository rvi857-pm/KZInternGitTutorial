DELIMITER || 
drop function if exists getSource;
CREATE FUNCTION getSource ( buyerId varchar(100) )
	RETURNS varchar(100)
	READS SQL DATA
	BEGIN

		DECLARE sourceVal varchar(100);
		DECLARE tempVar varchar(100);
		call updateVariables(buyerId);

		set tempVar = (select source from variables where id = buyerId);
		if tempVar = "bluekai" then
			set sourceVal = tempVar;
		elseif tempVar = "bombora" then
			set sourceVal = "bombora";
		elseif tempVar = "kickfire" then
			set sourceVal = "reverse_ip_lookup";
		elseif tempVar = "ip" then
			set sourceVal = "ip_range_match";
		end if;
		RETURN sourceVal;

	END ||
DELIMITER ;
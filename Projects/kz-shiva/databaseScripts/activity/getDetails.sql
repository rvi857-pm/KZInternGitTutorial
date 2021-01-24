DELIMITER || 
drop function if exists getDetails;
CREATE FUNCTION getDetails ( 
		type varchar(100),
		creative varchar(100),
		website varchar(100)
	)
	RETURNS varchar(100)
	READS SQL DATA
	BEGIN

		DECLARE returnValue varchar(100);
		if type = "Form Fill" then
			set returnValue = website;
		elseif type = "Website Visit" then
			set returnValue = website;
		elseif type = "ad click" then
			set returnValue = creative;
		elseif type = "Live chat" then
			set returnValue = website;
		end if;
		RETURN returnValue;
		
	END || 
DELIMITER ;
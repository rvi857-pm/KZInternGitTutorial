DELIMITER || 
drop function if exists getJobLevel;
CREATE FUNCTION getJobLevel ( buyerId varchar(100) )
	RETURNS varchar(100)
	READS SQL DATA
	BEGIN

		DECLARE jobLevelValue varchar(100);
        set jobLevelValue = (select job_level from variables where id = buyerId);
        RETURN jobLevelValue;

	END ||
DELIMITER ;
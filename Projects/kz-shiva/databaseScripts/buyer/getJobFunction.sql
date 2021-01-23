DELIMITER || 
drop function if exists getJobFunction;
CREATE FUNCTION getJobFunction ( buyerId varchar(100) )
	RETURNS varchar(100)
	READS SQL DATA
	BEGIN

        set @jobLevelFunction = (select job_function from variables where id = buyerId);
        RETURN @jobLevelFunction;

	END ||
DELIMITER ;
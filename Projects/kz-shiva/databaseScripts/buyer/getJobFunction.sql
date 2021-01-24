DELIMITER || 
drop function if exists getJobFunction;
CREATE FUNCTION getJobFunction ( buyerId varchar(100) )
	RETURNS varchar(100)
	READS SQL DATA
	BEGIN

		DECLARE jobFunctionValue varchar(100);
        set jobFunctionValue = (select job_function from variables where id = buyerId);
        RETURN jobFunctionValue;

	END ||
DELIMITER ;
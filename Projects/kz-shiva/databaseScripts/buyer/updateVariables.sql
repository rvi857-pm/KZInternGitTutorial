DELIMITER || 
drop procedure if exists updateVariables;
CREATE PROCEDURE updateVariables (
		IN buyerId varchar(100)
	)
	BEGIN

		DECLARE sourceVal varchar(100);
		DECLARE jobLevelVal varchar(100);
		DECLARE jobFuncVal varchar(100);

		drop temporary table if exists variablesProto;
		create temporary table variablesProto as select * from temp where temp.opencx_buyer_id = buyerId limit 1;

        set sourceVal = (select domain from variablesProto);
		set jobLevelVal = (select job_level from variablesProto);
		set jobFuncVal = (select job_function from variablesProto);

        insert into variables values(buyerId, sourceVal, jobLevelVal, jobFuncVal);

	END ||
DELIMITER ;
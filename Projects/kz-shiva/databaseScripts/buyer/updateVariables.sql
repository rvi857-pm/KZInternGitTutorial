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
		create temporary table variablesProto as
			select count(opencx_buyer_id), domain, job_level, job_function from temp
			where opencx_buyer_id = buyerId and (domain <> "" or job_level <> "" or job_function <>"")
			group by domain, job_level, job_function
			order by count(opencx_buyer_id)
			DESC;

		if (select count(*) from variablesProto) >= 1 then
			set sourceVal = (select domain from variablesProto limit 1);
			set jobLevelVal = (select job_level from variablesProto limit 1);
			set jobFuncVal = (select job_function from variablesProto limit 1);
		else 
			set sourceVal = "";
			set jobLevelVal = "";
			set jobFuncVal = "";
		end if;

        insert into variables values(buyerId, sourceVal, jobLevelVal, jobFuncVal);

	END ||
DELIMITER ;
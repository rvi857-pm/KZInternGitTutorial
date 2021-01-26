DELIMITER ||

DROP FUNCTION IF EXISTS getScore;
CREATE FUNCTION getScore( accountId varchar(100) )
	RETURNS float
	DETERMINISTIC
	BEGIN

		DECLARE score float;
		DECLARE n INT DEFAULT 0;
		DECLARE i INT DEFAULT 0;
		DECLARE temp varchar(100);
		DECLARE x float;
		DECLARE y float;
		DECLARE z varchar(100);
		set score = 0;

		drop temporary table if exists buyerProto;
		create temporary table buyerProto as 
		select id, job_level from buyer where account_id = accountId;
		
		drop temporary table if exists activityProto;
		create temporary table activityProto (
			id varchar(100),
			type varchar(100),
			result float
		);

		set n = (select count(*) from buyerProto);

		while i < n do
			set temp = ( select id from buyerProto limit i, 1 );
			insert into activityProto(id, type) 
			select buyer_id, activity_type from activity where buyer_id = temp;
			set i = i + 1;
		end while;

		update activityProto set result = getScoreForActivity(activityProto.type);

		set i = 0;
		set n = ( select count(*) from buyerProto );

		while i < n do
			set temp = ( select id from buyerProto limit i, 1 );
			set x = (select sum(result) from activityProto group by id having id = temp);
			set z = (select job_level from buyerProto where id = temp);
			set y = getScoreForBuyer(z);
			set score = score + (x * y);
			set i = i + 1;
		end while;

		RETURN score;

	END ||

DELIMITER ;
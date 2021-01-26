DELIMITER ||

DROP FUNCTION IF EXISTS getScoreForBuyer;
CREATE FUNCTION getScoreForBuyer( level varchar(100))
	RETURNS float
	DETERMINISTIC
	BEGIN

		DECLARE score float;

		if level = "C-Level" then
			set score = 2;
		elseif level = "Owner,Board Member" then
			set score = 1.75;
		elseif level = "VP,Director" then
			set score = 1.5;
		elseif level = "Manager" then
			set score = 1.25;
		else 
			set score = 1;
		end if;
		RETURN score;

	END ||

DELIMITER ;
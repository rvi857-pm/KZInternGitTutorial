DELIMITER ||

DROP FUNCTION IF EXISTS getScoreForActivity;
CREATE FUNCTION getScoreForActivity( type varchar(100))
	RETURNS float
	DETERMINISTIC
	BEGIN

		DECLARE score float;

		if type = "Ad Click" then
			set score = 1;
		elseif type = "Website Visit" then
			set score = 0.1;
		elseif type = "Form Fill" then
			set score = 3;
		elseif type = "Live Chat" then
			set score = 3;
		end if; 
		RETURN score;

	END ||

DELIMITER ;
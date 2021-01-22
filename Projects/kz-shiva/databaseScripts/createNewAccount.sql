DELIMITER || 
drop function if exists createNewAccount;
CREATE FUNCTION createNewAccount (buyerId varchar(100)) 
	RETURNS varchar(100) 
	DETERMINISTIC 
	BEGIN
		DECLARE id varchar(100),
		DECLARE account_name varchar(100),
		DECLARE ip_domain varchar(100),
		DECLARE city varchar(100),
		DECLARE state varchar(100),
		DECLARE country varchar(100),
		DECLARE type varchar(100),
		DECLARE salesforce_id varchar(100)

		set id = 

		insert into account( id, account_name, ip_domain, city, state, country, type, salesforce_id ) 
		values(

		)

	RETURN value;
END || 
DELIMITER ;
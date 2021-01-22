DELIMITER || 
drop function if exists getId;
CREATE FUNCTION getId (
	account_name varchar(100),
	ip_domain varchar(100),
	city varchar(100),
	state varchar(100),
	country varchar(100),
	type varchar(100),
	salesforce_id varchar(100))
	RETURNS varchar(100) 
	DETERMINISTIC 
	BEGIN

		DECLARE identifier varchar(100);
		set identifier = bin_to_uuid(unhex(md5(concat_ws(' ', account_name, ip_domain, city, state, country, type, salesforce_id))));

	RETURN identifier;
END || 
DELIMITER ;
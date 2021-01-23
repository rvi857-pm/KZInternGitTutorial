DELIMITER || 
drop procedure if exists getVariables;
CREATE PROCEDURE getVariables (
		IN buyerId varchar(100),
		OUT name varchar(100),
		OUT ip_domain varchar(100),
		OUT cityVal varchar(100),
		OUT stateVal varchar(100),
		OUT countryVal varchar(100),
		OUT externalId varchar(100),
		OUT creative varchar(100)
	)
	BEGIN

		drop temporary table if exists input;
		create temporary table input as select * from temp where temp.opencx_buyer_id = buyerId limit 1;

		set	name = (select account_name from input);
		set	ip_domain = (select account_ip_domain from input);
		set cityVal = (select city from input);
		set stateVal = (select state from input);
		set countryVal = (select country from input);
		set externalId = (select external_account_id from input);
		set	creative = (select creative_name from input);

	END || 
DELIMITER ;
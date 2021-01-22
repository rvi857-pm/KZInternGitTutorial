DELIMITER || 
drop procedure if exists getVariables;
CREATE PROCEDURE getVariables (
		IN buyerId varchar(100),
		OUT extId varchar(100),
		OUT name varchar(100),
		OUT ip_domain varchar(100),
		OUT creative varchar(100)
	)
	BEGIN

		drop temporary table if exists input;
		create temporary table input as select * from temp where temp.opencx_buyer_id = buyerId limit 1;

		set extId = (select external_account_id from input);	
		set	name = (select account_name from input);
		set	ip_domain = (select account_ip_domain from input);
		set	creative = (select creative_name from input);

	END || 
DELIMITER ;
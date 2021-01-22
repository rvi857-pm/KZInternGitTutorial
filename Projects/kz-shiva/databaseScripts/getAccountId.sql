DELIMITER || 
drop function if exists getAccountId;
CREATE FUNCTION getAccountId (buyerId varchar(100)) 
	RETURNS varchar(100) 
	DETERMINISTIC 
	BEGIN
		drop temporary table if exists input;
		create temporary table temp1 as select id, type from account where extId like concat(account.salesforce_id, '%');
		DECLARE value varchar(100);
		DECLARE len varchar(100);

		set extId = (select external_account_id from temp where temp.opencx_buyer_id = buyerId limit 1);
		set	name = (select account_name from temp where temp.opencx_buyer_id = buyerId limit 1);
		set	ip_domain = (select account_ip_domain from temp where temp.opencx_buyer_id = buyerId limit 1);
		set	creative = (select creative_name from temp where temp.opencx_buyer_id = buyerId limit 1);

		drop temporary table if exists temp1;
		create temporary table temp1 as select id, type from account where extId like concat(account.salesforce_id, '%');
		set len = ( select count(*) from temp1 );
		if len > 1 then
			set value = ( select id from temp1 where concat('%', lower(type), '%') like lower(creative) );
		elseif len = 1 then
			set value = ( select id from temp1 limit 1 );
		end if;

		if value = NULL then
			drop temporary table if exists temp1;
			create table temp1 as select id, type from account where name = account.name;
			set len = ( select count(*) from temp1 );
			if len > 1 then
				set value = ( select id from temp1 where concat('%', lower(type), '%') like lower(creative) );
			elseif len = 1 then
				set value = (select id from temp1 limit 1);
			end if;
		end if;

		if value = NULL then
			drop temporary table if exists temp1;
			create table temp1 as select id, type from account where ip_domain = account.ip_domain;
			set len = ( select count(*) from temp1 );
			if len > 1 then
				set value = ( select id from temp1 where concat('%', lower(type), '%') like lower(creative) );
			elseif len = 1 then
				set value = (select id from temp1 limit 1);
			end if;
		end if;

		if value = NULL then
			set value = createNewAccount(buyerId);

		RETURN value;
	END || 
DELIMITER ;
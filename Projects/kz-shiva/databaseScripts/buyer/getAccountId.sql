DELIMITER || 

drop function if exists getAccountId;
CREATE FUNCTION getAccountId (buyerId varchar(100)) 
	RETURNS varchar(100) 
	DETERMINISTIC 
	BEGIN

		DECLARE value varchar(100);
		DECLARE len varchar(100);
		
		call getVariables(buyerId, @name, @ip_domain, @city, @state, @country, @externalId, @creative);

		drop temporary table if exists temp1;
		create temporary table temp1 as select id, type from account where @externalId like concat(account.salesforce_id, '%');
		set len = ( select count(*) from temp1 );
		if len > 1 then
			set value = ( select id from temp1 where concat('%', lower(type), '%') like lower(@creative) );
		elseif len = 1 then
			set value = ( select id from temp1 limit 1 );
		end if;

		if isnull(value) = 1 then
			drop temporary table if exists temp1;
			create temporary table temp1 as select id, type from account where @name = account.name;
			set len = ( select count(*) from temp1 );
			if len > 1 then
				set value = ( select id from temp1 where concat('%', lower(type), '%') like lower(@creative) );
			elseif len = 1 then
				set value = (select id from temp1 limit 1);
			end if;
		end if;

		if isnull(value) = 1 then
			drop temporary table if exists temp1;
			create temporary table temp1 as select id, type from account where @ip_domain = account.ip_domain;
			set len = ( select count(*) from temp1 );
			if len > 1 then
				set value = ( select id from temp1 where concat('%', lower(type), '%') like lower(@creative) );
			elseif len = 1 then
				set value = (select id from temp1 limit 1);
			end if;
		end if;

		if isnull(value) = 1 then
			call createNewAccount(buyerId, @value);
			set value = @value;
		end if;

		RETURN value;
	END || 
DELIMITER ;
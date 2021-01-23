DELIMITER || 
drop procedure if exists createNewAccount;
CREATE PROCEDURE createNewAccount (
		IN buyerId varchar(100),
		OUT value varchar(100)
	)
	BEGIN

		call getVariables(buyerId, @name, @ip_domain, @city, @state, @country, @externalId, @creative);
		set @id = bin_to_uuid( unhex( md5( concat_ws(' ', @name, @ip_domain, @city, @state, @country, @type, @externalId))));
		insert into account values( @id, @name, @ip_domain, @city, @state, @country, @type, @externalId );
		set value = @id;

	END || 
DELIMITER ;
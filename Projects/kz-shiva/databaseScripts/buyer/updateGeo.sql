DELIMITER || 
drop procedure if exists updateGeo;
CREATE PROCEDURE updateGeo (
		IN buyerId varchar(100)
	)
	BEGIN

		DECLARE cityVal varchar(100);
		DECLARE stateVal varchar(100);
		DECLARE countryVal varchar(100);

		drop temporary table if exists geoProto;

		create temporary table geoProto as
			select count(opencx_buyer_id), city, state, country from temp
			where opencx_buyer_id = buyerId and (city <> "" or state <> "" or country <>"")
			group by city, state, country
			order by count(opencx_buyer_id)
			DESC;

		if (select count(*) from geoProto) >= 1 then
			set cityVal = (select city from geoProto limit 1);
			set stateVal = (select state from geoProto limit 1);
			set countryVal = (select country from geoProto limit 1);
		else 
			set cityVal = "";
			set stateVal = "";
			set countryVal = "";
		end if;

		insert into geo values(buyerId, cityVal, stateVal, countryVal);
	END || 
DELIMITER ;
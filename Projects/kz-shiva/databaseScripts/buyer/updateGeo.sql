DELIMITER || 
drop procedure if exists updateGeo;
CREATE PROCEDURE updateGeo (
		IN buyerId varchar(100)
	)
	BEGIN

		drop temporary table if exists geoProto;

		create temporary table geoProto as
			select count(opencx_buyer_id), city, state, country from temp
			where opencx_buyer_id = buyerId
			group by city, state, country
			order by count(opencx_buyer_id)
			DESC limit 1;

		set cityVal = (select city from geoProto);
		set stateVal = (select state from geoProto);
		set countryVal = (select country from geoProto);

		insert into geo (buyerId, cityVal, stateVal, countryVal);
	END || 
DELIMITER ;
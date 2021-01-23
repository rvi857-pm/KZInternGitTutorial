-- creates table account
create table account ( name varchar(100), ip_domain varchar(100), city varchar(100), state varchar(100), country varchar(100), type varchar(100), salesforce_id varchar(100), one varchar(100), two varchar(100));

-- load data into the table account
load data local infile '/home/shiva/Kwanzoo/accounts_master.csv' into table account  fields terminated by ',' enclosed by '"' lines terminated by '\n' ignore 1 lines;

-- alter table as required
alter table account drop column one;
alter table account drop column two;
alter table account add column id varchar(100) NOT NULL;
delete from account where name = "";

-- hash value as the primary id of table
update account set id = bin_to_uuid( unhex( md5( concat_ws(' ', name, ip_domain, city, state, country, type, salesforce_id))));
alter table account modify column id varchar(100) FIRST;
alter table account add PRIMARY KEY(id);
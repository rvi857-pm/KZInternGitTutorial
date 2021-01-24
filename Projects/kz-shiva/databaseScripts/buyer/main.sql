-- initializes buyer table
drop table if exists buyer;
create table buyer(
    id varchar(100) NOT NULL PRIMARY KEY,
    account_id varchar(100),
    city varchar(100),
    state varchar(100),
    country varchar(100),
    source varchar(100),
    job_level varchar(100),
    job_function varchar(100)
);

-- updates geographical values
drop table if exists geo;
create table geo (
    id varchar(100),
    city varchar(100),
    state varchar(100),
    country varchar(100)
);

drop table if exists variables;
create table variables (
    id varchar(100),
    source varchar(100),
    job_level varchar(100),
    job_function varchar(100)
);

-- Fills the table with the primary key
insert into buyer(id)
select distinct opencx_buyer_id from temp;

-- Declare the functions and procedures
source buyer/getVariables.sql;
source account/createNewAccount.sql;
source buyer/getAccountId.sql;
source buyer/updateGeo.sql;
source buyer/getCity.sql;
source buyer/getState.sql;
source buyer/getCountry.sql;
source buyer/updateVariables.sql;
source buyer/getSource.sql;
source buyer/getJobLevel.sql;
source buyer/getJobFunction.sql;

-- updates table with right account_id value and sets it as a foreign key
update buyer set account_id  = getAccountId(id);
alter table buyer add constraint FK_account foreign key(account_id) references account(id);

update buyer set city = getCity(id);
update buyer set state = getState(id);
update buyer set country = getCountry(id);

update buyer set source = getSource(id);
update buyer set job_level = getJobLevel(id);
update buyer set job_function = getJobFunction(id);
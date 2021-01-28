-- initializes activity table
drop table if exists activity;
create table activity (
    id varchar(100),
    buyer_id varchar(100),
    date_time datetime,
    activity_type varchar(100),
    details varchar(100)
);

source activity/getDetails.sql;

insert into activity(buyer_id, date_time, activity_type, details)
select opencx_buyer_id, from_unixtime(floor(date_and_time/1000)), activity_type, getDetails(activity_type, creative_name, website) from temp;

-- set buyer_id as foreign key
alter table activity add constraint FK_buyer foreign key(buyer_id) references buyer(id); 

-- hash value as the primary id of table
update activity set id = uuid();
alter table activity modify column id varchar(100) NOT NULL;

-- sets id as the primary key
alter table activity add PRIMARY KEY(id);
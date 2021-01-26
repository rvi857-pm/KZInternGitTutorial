-- add a new column in account to store score values
alter table account add column score float;

-- update the score values using getScore Function.
update account set score = getScore(id);
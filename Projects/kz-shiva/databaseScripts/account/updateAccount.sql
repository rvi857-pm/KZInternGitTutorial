-- add a new column in account to store score values
-- alter table account add column score float;

-- Declare the required functions
source account/getScoreForActivity.sql;
source account/getScoreForBuyer.sql;
source account/getScore.sql;

-- update the score values using getScore Function.
update account set score = getScore(id);
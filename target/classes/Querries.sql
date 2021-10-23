/*CREATE TABLE Vehicle (id bigserial PRIMARY KEY, model varchar(50), assemblyDate date, userInfoId bigserial REFERENCES userinfo)
*//*CREATE TABLE PersonalInfo (id bigserial PRIMARY KEY, email varchar(60), address varchar(250), phoneNumber varchar(20))*/
/*CREATE TABLE UserInfo (id bigserial PRIMARY KEY, firstName varchar(60), lastName varchar(60), age int4, PersonalInfoId bigserial REFERENCES personalinfo)
*/
/*INSERT INTO vehicle (model, assemblydate, userinfoid) VALUES ('car2', generate_series('1900-01-01'::timestamp, '2021-01-01'::timestamp, '24 hours'), 2);*/
/*INSERT INTO personalinfo (email, address, phonenumber) VALUES (random()::text, random()::text, random()::text)*/

/*SELECT id INTO ids
FROM personalinfo
ORDER BY random()
LIMIT 1;

DROP EXTENSION tsm_system_rows;

CREATE EXTENSION tsm_system_rows;
SELECT * FROM personalinfo TABLESAMPLE system_rows(1);

/*INSERT INTO userinfo(firstname, lastname, age, personalinfoid)
VALUES (personalinfo.ids)*/

CREATE FUNCTION getRandomID() RETURNS bigint AS '
    SELECT id
    FROM personalinfo
    ORDER BY random()
    LIMIT 1
' LANGUAGE sql;

INSERT INTO userinfo(firstname, lastname, age, personalinfoid) VALUES ('a', 'b', 31 , getRandomID()) RETURNING firstname, personalinfoid*/


DROP TABLE IF EXISTS Users CASCADE;
/*CREATE FUNCTION getRandomID() RETURNS bigint AS '
    SELECT id
    FROM personalinfo
    ORDER BY random()
    LIMIT 1
' LANGUAGE sql;*/

CREATE TABLE Users (id bigserial PRIMARY KEY, Name varchar(80), age int4, PersonalInfoId bigint REFERENCES personalInfo (id));

INSERT INTO Users (name,age, personalinfoid)
VALUES
    ('Miranda Cotton',2, getRandomID()),
    ('Freya Vazquez',3, getRandomID()),
    ('Dahlia Mckee',0, getRandomID()),
    ('Quinlan Santos',8, null),
    ('Reuben Bruce',10, getRandomID()),
    ('Nathan Good',6, null),
    ('Driscoll Haley',8, getRandomID()),
    ('Hannah Page',9, getRandomID()),
    ('Priscilla Wall',3, null),
    ('Carolyn Holt',10, getRandomID());
INSERT INTO Users (name,age, personalinfoid)
VALUES
    ('Wallace Gates',4, getRandomID()),
    ('Mia Adams',2, null),
    ('Emerald Herrera',8, getRandomID()),
    ('Amaya Cox',2, getRandomID()),
    ('Tobias Cooper',4, getRandomID()),
    ('Cassidy Graves',6, null),
    ('Patience Chang',6, getRandomID()),
    ('Norman Tucker',1, getRandomID()),
    ('Anthony Horton',8, null),
    ('Grady Johns',5, getRandomID());
INSERT INTO Users (name,age, personalinfoid)
VALUES
    ('Vielka Case',3, getRandomID()),
    ('Darius Farley',3, null),
    ('Ali Atkinson',9, getRandomID()),
    ('Gil Rodgers',0, null),
    ('Octavia Castro',1, getRandomID()),
    ('Hollee Hines',7, getRandomID()),
    ('Cooper Rowe',0, null),
    ('Sierra Kirby',7, getRandomID()),
    ('Brenden Hawkins',8, getRandomID()),
    ('Freya Rodgers',4, getRandomID());
INSERT INTO Users (name,age, personalinfoid)
VALUES
    ('Dahlia Patel',7, getRandomID()),
    ('Isaiah Aguilar',7, getRandomID()),
    ('Inez Reeves',3, getRandomID()),
    ('Carlos Carpenter',2, getRandomID()),
    ('Ashely Finley',0, getRandomID()),
    ('Sade Moody',7, null),
    ('Steel West',2, getRandomID()),
    ('Lois Harper',4, getRandomID()),
    ('Rhoda Byrd',3, null),
    ('Geoffrey Mercer',3, getRandomID());
INSERT INTO Users (name,age, personalinfoid)
VALUES
    ('Zeph Farrell',9, getRandomID()),
    ('Octavia Glenn',4, null),
    ('Rhonda Alvarez',6, getRandomID()),
    ('Bruce Floyd',6, getRandomID()),
    ('Ann Crawford',4, getRandomID()),
    ('Lacy Simon',4, null),
    ('Joy Haley',4, getRandomID()),
    ('Melanie Ferrell',0, getRandomID()),
    ('Reuben Hunter',5, null),
    ('Mariam Joyner',8, getRandomID());
INSERT INTO Users (name,age, personalinfoid)
VALUES
    ('Jacqueline Vang',8, getRandomID()),
    ('Rachel Byers',7, getRandomID()),
    ('Michael Wise',4, getRandomID()),
    ('Maile Franco',0, null),
    ('Lila Mosley',6, getRandomID()),
    ('Aaron Black',8, null),
    ('Nelle Summers',5, getRandomID()),
    ('Xenos Blevins',5, null),
    ('Brian Leach',7, getRandomID()),
    ('Maris Justice',8, getRandomID());
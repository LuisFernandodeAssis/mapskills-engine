-- db.changelog-1.4.0.03-update-student.sql 04/11/2017
-- Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
-- Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.

ALTER TABLE MAPSKILLS.STUDENT ADD NAME VARCHAR(250);

UPDATE MAPSKILLS.STUDENT S SET NAME = (SELECT U.NAME FROM MAPSKILLS.USER U WHERE U.ID = S.ID_USER);

ALTER TABLE MAPSKILLS.STUDENT CHANGE ID_USER ID_LOGIN BIGINT(20);
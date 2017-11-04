-- db.changelog-1.4.0.01-update-administrator.sql 03/11/2017
-- Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
-- Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.

ALTER TABLE MAPSKILLS.ADMINISTRATOR ADD NAME VARCHAR(250);

UPDATE MAPSKILLS.ADMINISTRATOR A SET NAME = (SELECT U.NAME FROM MAPSKILLS.USER U WHERE U.ID = A.ID_USER);

ALTER TABLE MAPSKILLS.ADMINISTRATOR CHANGE ID_USER ID_LOGIN BIGINT(20);
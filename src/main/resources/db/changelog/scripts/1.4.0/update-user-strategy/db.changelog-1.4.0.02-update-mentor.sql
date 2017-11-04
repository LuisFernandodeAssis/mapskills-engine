-- db.changelog-1.4.0.02-update-mentor.sql 04/11/2017
-- Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
-- Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.

ALTER TABLE MAPSKILLS.MENTOR ADD NAME VARCHAR(250);

UPDATE MAPSKILLS.MENTOR M SET NAME = (SELECT U.NAME FROM MAPSKILLS.USER U WHERE U.ID = M.ID_USER);

ALTER TABLE MAPSKILLS.MENTOR CHANGE ID_USER ID_LOGIN BIGINT(20);
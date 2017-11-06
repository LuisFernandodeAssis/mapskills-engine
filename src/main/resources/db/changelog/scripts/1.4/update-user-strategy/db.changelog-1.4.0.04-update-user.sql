-- db.changelog-1.4.0.04-update-user.sql 04/11/2017
-- Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
-- Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.

ALTER TABLE MAPSKILLS.USER DROP COLUMN NAME;

ALTER TABLE MAPSKILLS.USER RENAME TO MAPSKILLS.LOGIN;
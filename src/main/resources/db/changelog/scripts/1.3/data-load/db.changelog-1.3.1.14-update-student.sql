-- db.changelog-1.3.1.14-update-student.sql 19/04/2018
-- Copyright (c) 2018, Fatec-Jessen Vidal. All rights reserved.
-- Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.

UPDATE STUDENT
	SET	INSTITUTION_CODE = SUBSTR(RA, 1, 3),
		COURSE_CODE = SUBSTR(RA, 4, 3),
        START_YEAR = CONCAT('20', SUBSTR(RA, 7, 2)),
        START_SEMESTER = SUBSTR(RA, 9, 1),
        CODE = SUBSTR(RA, 10)		
WHERE ID != 0;
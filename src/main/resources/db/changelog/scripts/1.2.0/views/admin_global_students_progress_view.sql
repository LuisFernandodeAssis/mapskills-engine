-- admin_global_students_progress_view.sql 1.2.0 29/04/2017
-- Copyright (c) 2016, Fatec-Jessen Vidal. All rights reserved.
-- Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.

CREATE OR REPLACE VIEW ADMIN_GLOBAL_STUDENTS_PROGRESS_VIEW AS
	SELECT
		SUBSTR(STUD.STU_RA, 7, 3) AS ANO_SEMESTRE,
	    INSTITUTION.INS_LEVEL AS LEVEL,
	    (SELECT COUNT(*) FROM STUDENT 
			WHERE STU_IS_COMPLETED = 0 AND INS_CODE = INSTITUTION.INS_CODE
	        AND SUBSTR(STUDENT.STU_RA, 7, 3) = SUBSTR(STUD.STU_RA, 7, 3) GROUP BY INS_CODE) AS NOT_FINALIZED,
	    (SELECT COUNT(*) FROM STUDENT
			WHERE STU_IS_COMPLETED = 1 AND INS_CODE = INSTITUTION.INS_CODE
	        AND SUBSTR(STUDENT.STU_RA, 7, 3) = SUBSTR(STUD.STU_RA, 7, 3) GROUP BY INS_CODE) AS FINALIZED,
		COUNT(*) TOTAL
	FROM STUDENT STUD INNER JOIN INSTITUTION ON STUD.INS_CODE = INSTITUTION.INS_CODE
	GROUP BY SUBSTR(STUD.STU_RA, 7, 3), INSTITUTION.INS_LEVEL;
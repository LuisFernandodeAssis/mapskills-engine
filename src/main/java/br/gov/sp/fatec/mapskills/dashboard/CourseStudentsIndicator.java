/*
 * @(#)CourseStudentIndicator.java 1.0 1 17/09/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */

package br.gov.sp.fatec.mapskills.dashboard;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

import lombok.Getter;

/**
 * A classe {@link CourseStudentsIndicator}
 *
 * @author Marcelo
 * @version 1.0 17/09/2017
 */
@Getter
@Entity
@Immutable
@Table(name = "MAPSKILLS.INTITUTION_STUDENTS_PROGRESS_VIEW")
public class CourseStudentsIndicator implements StudentIndicator {
	
	@Id
	private Long id;
	
	@Column(name = "YEAR_SEMESTER")
	private final String yearSemester;
	
	@Column(name = "INS_CODE")
	private final String institutionCode;
	
	@Column(name = "LEVEL")
	private final String institutionLevel;
	
	@Column(name = "CRS_CODE")
	private final String courseCode;
	
	@Column(name = "COURSE")
	private final String courseName;
	
	@Column(name = "NOT_FINALIZED")
	private final Integer notFinalized;
	
	@Column(name = "FINALIZED")
	private final Integer finalized;
	
	private CourseStudentsIndicator() {
		this.yearSemester = null;
		this.institutionCode = null;
		this.institutionLevel = null;
		this.courseCode = null;
		this.courseName = null;
		this.notFinalized = null;
		this.finalized = null;
	}
}
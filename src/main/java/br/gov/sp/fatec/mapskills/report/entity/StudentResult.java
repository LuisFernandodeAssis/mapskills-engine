/*
 * @(#)StudentResult.java 1.0 18/03/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.report.entity;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

import br.gov.sp.fatec.mapskills.domain.institution.InstitutionLevel;
import lombok.Getter;

/**
 * 
 * A classe {@link StudentResult} representa a view
 * da base de dados com as informações para os relatorios simples.
 *
 * @author Marcelo
 * @version 1.0 18/03/2017
 */
@Getter
@Entity
@Immutable
@Table(name = "MAPSKILLS.CSV_REPORT_VIEW")
public class StudentResult {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="STUDENT_ID")
	private final Long studentId;
	
	@Column(name="STUDENT_RA")
	private final String studentRA;
	
	@Column(name="STUDENT_NAME")
	private final String studentName;
	
	@Column(name="COURSE_CODE")
	private final String courseCode;
	
	@Column(name="COURSE_NAME")
	private final String courseName;
	
	@Column(name="INSTITUTION_CODE")
	private final String institutionCode;
	
	@Column(name="INSTITUTION_COMPANY")
	private final String institutionCompany;
	
	@Enumerated
	@Column(name="INSTITUTION_LEVEL")
	private final InstitutionLevel institutionLevel;
	
	@Column(name="YEAR_SEMESTER")
	private final String yearSemester;
	
	@OneToMany
	@JoinColumn(name = "ID_STUDENT")
	@OrderBy("skillName")
	private final List<StudentResultIndicator> studentIndicators = new LinkedList<>();
	
	private StudentResult() {
		this.studentId = null;
		this.studentRA = null;
		this.studentName = null;
		this.courseName = null;
		this.institutionCode = null;
		this.institutionCompany = null;
		this.institutionLevel = null;
		this.yearSemester = null;
		this.courseCode = null;
	}
	
	public List<StudentResultIndicator> getStudentIndicators() {
		return Collections.unmodifiableList(studentIndicators);
	}
}
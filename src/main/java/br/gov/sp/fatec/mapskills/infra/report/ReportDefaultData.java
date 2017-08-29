/*
 * @(#)ReportDefaultData.java 1.0 18/03/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.infra.report;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Immutable;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import br.gov.sp.fatec.mapskills.domain.institution.InstitutionLevel;
import lombok.Getter;

/**
 * 
 * A classe {@link ReportDefaultData} representa a view
 * da base de dados com as informações para os relatorios simples.
 *
 * @author Marcelo
 * @version 1.0 18/03/2017
 */
@Getter
@Entity
@Immutable
@Table(name = "CSV_REPORT_VIEW")
public class ReportDefaultData {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="STUDENT_ID", updatable = false, nullable = false) private final Long studentId;
	@Column(name="STUDENT_RA", updatable = false, nullable = false) private final String studentRA;
	@Column(name="STUDENT_NAME", updatable = false, nullable = false) private final String studentName;
	@Column(name="COURSE_CODE", updatable = false, nullable = false) private final String courseCode;
	@Column(name="COURSE", updatable = false, nullable = false) private final String courseName;
	@Column(name="INSTITUTION_CODE", updatable = false, nullable = false) private final String institutionCode;
	@Column(name="INSTITUTION", updatable = false, nullable = false) private final String institutionCompany;
	@Column(name="INSTITUTION_LEVEL", updatable = false, nullable = false) private final String institutionLevel;
	@Column(name="YEAR_SEMESTER", updatable = false, nullable = false) private final String yearSemester;
	@Transient private final List<Object> scores = new LinkedList<>();
	
	@SuppressWarnings("unused")
	private ReportDefaultData() {
		this(null, null, null, null, null, null, null, null, null);
	}
	
	public ReportDefaultData(final Long studentId, final String studentRA, final String studentName,
			final String courseCode, final String courseName,final String institutionCode,
			final String institutionCompany, final InstitutionLevel institutionLevel, final String yearSemester) {

		this.studentId = studentId;
		this.studentRA = studentRA;
		this.studentName = studentName;
		this.courseName = courseName;
		this.institutionCode = institutionCode;
		this.institutionCompany = institutionCompany;
		this.institutionLevel = ObjectUtils.isEmpty(institutionLevel) ? null : institutionLevel.name();
		this.yearSemester = yearSemester;
		this.courseCode = courseCode;
	}
	
	public InstitutionLevel getInstitutionLevel() {
		return InstitutionLevel.valueOf(institutionLevel);
	}
	
	public void setScores(final List<Object> scores) {
		if(!CollectionUtils.isEmpty(scores)) {
			this.scores.addAll(scores);			
		}
	}
	
	public List<Object> getScores() {
		return Collections.unmodifiableList(this.scores);
	}

}

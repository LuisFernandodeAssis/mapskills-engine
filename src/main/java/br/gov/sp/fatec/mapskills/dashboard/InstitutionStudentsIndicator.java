/*
 * @(#)InstitutionStudentsIndicator.java 1.0 1 17/09/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */

package br.gov.sp.fatec.mapskills.dashboard;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

import br.gov.sp.fatec.mapskills.domain.institution.InstitutionLevel;
import lombok.Getter;

/**
 * A classe {@link InstitutionStudentsIndicator}
 *
 * @author Marcelo
 * @version 1.0 17/09/2017
 */
@Getter
@Entity
@Immutable
@Table(name = "MAPSKILLS.ADMIN_LEVEL_STUDENTS_PROGRESS_VIEW")
public class InstitutionStudentsIndicator implements StudentIndicator {
	
	@Id
	private Long id;
	
	@Column(name = "YEAR_SEMESTER")
	private final String yearSemester;
	
	@Column(name = "CODE")
	private final String institutionCode;
	
	@Enumerated
	@Column(name = "LEVEL")
	private final InstitutionLevel level;
	
	@Column(name = "COMPANY")
	private final String institutionName;
	
	@Column(name = "NOT_FINALIZED")
	private final Double notFinalized;
	
	@Column(name = "FINALIZED")
	private final Double finalized;
	
	@Column(name = "TOTAL")
	private final Integer total;	
	
	private InstitutionStudentsIndicator() {
		this.yearSemester = null;
		this.institutionCode = null;
		this.level = null;
		this.institutionName = null;
		this.notFinalized = null;
		this.finalized = null;
		this.total = null;
	}
}
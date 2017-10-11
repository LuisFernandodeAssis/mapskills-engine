/*
 * @(#)GlobalStudentsIndicator.java 1.0 1 17/09/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */

package br.gov.sp.fatec.mapskills.dashboard;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

import br.gov.sp.fatec.mapskills.domain.institution.InstitutionLevel;
import lombok.Getter;

/**
 * A classe {@link GlobalStudentsIndicator}
 *
 * @author Marcelo
 * @version 1.0 17/09/2017
 */
@Getter
@Entity
@Immutable
@Table(name = "MAPSKILLS.ADMIN_GLOBAL_STUDENTS_PROGRESS_VIEW")
public class GlobalStudentsIndicator implements StudentIndicator {
	
	@Id
	private Long id;
	
	@Column(name = "YEAR_SEMESTER")
	private final String yearSemester;
	
	@Column(name = "LEVEL")
	@Enumerated(value = EnumType.STRING)
	private final InstitutionLevel level;
	
	@Column(name = "NOT_FINALIZED")
	private final Integer notFinalized;
	
	@Column(name = "FINALIZED")
	private final Integer finalized;
	
	@Column(name = "TOTAL")
	private final Integer total;
	
	private GlobalStudentsIndicator() {
		this.yearSemester = null;
		this.level = null;
		this.notFinalized = null;
		this.finalized = null;
		this.total = null;
	}
	
	public boolean isInstitutionLevelSuperior() {
		return level.isSuperior();
	}

}
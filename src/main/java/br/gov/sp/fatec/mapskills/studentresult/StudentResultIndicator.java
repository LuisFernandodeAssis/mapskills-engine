/*
 * @(#)StudentResultIndicator.java 1.0 1 08/10/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */

package br.gov.sp.fatec.mapskills.studentresult;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;

/**
 * A classe {@link StudentResultIndicator} representa
 * o resultado de uma competencia avaliada por um aluno.
 *
 * @author Marcelo
 * @version 1.0 08/10/2017
 */
@Getter
@Entity
@Table(name = "MAPSKILLS.STUDENT_RADAR_RESULT_VIEW")
public class StudentResultIndicator {
	
	@Id
	@Column(name = "ID")
	private final Long id;
	
	@Column(name = "ID_STUDENT")
	private final Long studentId;
	
	@Column(name = "SKILL_NAME")
	private final String skillName;
	
	@Column(name = "SKILL_DESCRIPTION")
	private final String skillDescription;
	
	@Column(name = "TOTAL")
	private final Long total;
	
	private StudentResultIndicator() {
		this.id = null;
		this.studentId = null;
		this.skillName = null;
		this.skillDescription = null;
		this.total = null;
	}
}
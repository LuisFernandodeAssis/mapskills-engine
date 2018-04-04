/*
 * @(#)AcademicRegistry.java 1.0 10/11/2016
 *
 * Copyright (c) 2016, Fatec Jessen Vidal. All rights reserved.
 * Fatec Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */

package br.gov.sp.fatec.mapskills.domain.user.student;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.StringUtils;

import lombok.Getter;
import lombok.ToString;

/**
 * a classe <code>AcademicRegistry</code> eh um Value Object que representa a RA do aluno,
 * utilizada para recuperar informacoes do mesmo.
 * 
 * @author Marcelo
 * @version 1.0 10/11/2016
 */
@Getter
@Embeddable
@ToString(of = {"fullRa"})
public class AcademicRegistry {

	@Column(name = "RA")
	@Size(min = 13, max = 14)
	private final String fullRa;
	
	@Column(name = "INSTITUTION_CODE")
	private final String institutionCode;
	
	@Column(name = "COURSE_CODE")
	private final String courseCode;
	
	@Column(name = "START_YEAR")
	private final Integer startYear;
	
	@Column(name = "START_SEMESTER")
	private final Short startSemester;
	
	@Column(name = "CODE")
	private final String studentCode;
	
	@SuppressWarnings("unused")
	private AcademicRegistry() {
		this("00000000000000");
	}
		
	public AcademicRegistry(final String ra) {
		validate(ra);
		this.fullRa = ra;
		this.institutionCode = ra.substring(0, 3);
		this.courseCode = ra.substring(3, 6);
		this.startYear = Integer.parseInt("20" + ra.substring(6, 8));
		this.startSemester = Short.parseShort(ra.substring(8, 9));
		this.studentCode = ra.substring(9);
	}
	
	/**
	 * Valida o numero do registro academico do aluno.
	 * 
	 * @param ra Numero do RA a ser validado pela aplicacao.
	 */
	private void validate(final String ra) {
		if(!StringUtils.isNumeric(ra) || ra.length() < 13 || ra.length() > 14) {
			throw new AcademicRegistryException(ra);
		}
	}	
}
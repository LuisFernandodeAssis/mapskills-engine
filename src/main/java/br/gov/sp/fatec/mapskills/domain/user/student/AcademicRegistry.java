/*
 * @(#)AcademicRegistry.java 1.0 10/11/2016
 *
 * Copyright (c) 2016, Fatec Jessen Vidal. All rights reserved. Fatec Jessen Vidal
 * proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.domain.user.student;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Size;

import br.gov.sp.fatec.mapskills.domain.MapSkillsException;
import lombok.Getter;
/**
 * a classe <code>AcademicRegistry</code> eh
 * um Value Object que representa a RA do aluno,
 * que eh utilizada para recuperar algumas informacoes
 * do mesmo.
 * 
 * @author Marcelo
 *
 */
@Getter
@Embeddable
public class AcademicRegistry implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Column(name = "STU_RA")
	@Size(min=13, max=14)
	private String ra;
	
	@Column(name = "INS_CODE", nullable = false)
	private String institutionCode;
	
	@Column(name = "CRS_CODE", nullable = false)
	private String courseCode;
	
	@SuppressWarnings("unused")
	private AcademicRegistry() throws MapSkillsException {
		this("0000000000000");
	}
		
	public AcademicRegistry(final String ra) throws MapSkillsException {
		validate(ra);
		this.ra = ra;
		this.institutionCode = ra.substring(0, 3);
		this.courseCode = ra.substring(3, 6);
	}
	
	public String getYear() {
		return ra.substring(6, 8);
	}
	
	public String getSemester() {
		return ra.substring(8, 9);
	}
	
	public String getStudentCode() {
		return ra.substring(9);
	}
	
	/**
	 * metodo que valida o ra contido no documento xlsx
	 * @param ra
	 * @throws MapSkillsException
	 */
	private void validate(final String ra) throws MapSkillsException {
		try {
			Long.parseLong(ra);
			if(ra.length() < 13 || ra.length() > 14) {
				throw new RAInvalidException(ra);
			}
		} catch (final NumberFormatException e) {
			throw new RAInvalidException(ra);
		}
	}
	
}

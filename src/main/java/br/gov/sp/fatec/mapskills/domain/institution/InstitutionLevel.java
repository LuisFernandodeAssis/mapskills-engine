/*
 * @(#)InstitutionLevel.java 1.0 26/02/2017
 *
 * Copyright (c) 2016, Fatec Jessen Vidal. All rights reserved. Fatec Jessen Vidal
 * proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.domain.institution;
/**
 * 
 * O enum <code>InstitutionLevel</code> representa
 * os niveis de grau que uma instituicao pode assumir.
 * @author Marcelo Inácio
 *
 */
public enum InstitutionLevel {
	
	TECHNICAL("TECNICO"), SUPERIOR("SUPERIOR");
	
	private final String level;
	
	private InstitutionLevel(final String level) {
		this.level = level;
	}
	
	public String getLevel() {
		return level;
	}
	
	public boolean isSuperior() {
		return this.equals(InstitutionLevel.SUPERIOR);
	}
	
	public static InstitutionLevel build(final String level) {
		return level.equals(SUPERIOR.getLevel()) ? SUPERIOR : TECHNICAL;
	}

}

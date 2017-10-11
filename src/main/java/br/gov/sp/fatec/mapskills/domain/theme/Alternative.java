/*
 * @(#)Alternative.java 1.0 01/11/2016
 *
 * Copyright (c) 2016, Fatec Jessen Vidal. All rights reserved. Fatec Jessen Vidal
 * proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.domain.theme;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
/**
 * 
 * A classe {@link Alternative} representa
 * uma alternativa de uma questao, dentro
 * da aplicacao.
 *
 * @author Marcelo
 * @version 1.0 21/05/2017
 */
@Getter
@Entity
@Table(name = "MAPSKILLS.ALTERNATIVE")
public class Alternative {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "DESCRIPTION", nullable = false)
	private String description;
	
	@Column(name = "SKILL_VALUE", nullable = false)
	private Integer skillValue;
		
	@SuppressWarnings("unused")
	private Alternative() {
		this(null, null, null);
	}
	
	public Alternative(final Long id, final String description, final Integer skillValue) {
		this.id = id;
		this.description = description;
		this.skillValue = skillValue;
	}

	public void update(final Alternative updateAlternative) {
		this.description = updateAlternative.getDescription();
		this.skillValue = updateAlternative.getSkillValue();
	}
}
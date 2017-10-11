/*
 * @(#)Skill.java 1.0 01/11/2016
 *
 * Copyright (c) 2016, Fatec Jessen Vidal. All rights reserved.
 * Fatec Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.domain.skill;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
/**
 * 
 * A classe {@link Skill} representa uma competencia
 * que o aluno pode conter no contexto da aplicacao.
 *
 * @author Marcelo
 * @version 1.0 01/11/2016
 */
@Getter
@Entity
@Table(name = "MAPSKILLS.SKILL")
public class Skill {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "NAME", nullable = false)
	private String name;
	
	@Column(name = "DESCRIPTION")
	private String description;
	
	@SuppressWarnings("unused")
	private Skill() {
		this(null, null);
	}
	
	public Skill(final String name, final String description) {
		this.name = name;
		this.description = description;
	}

	public void update(final Skill updateSkill) {
		this.name = updateSkill.getName();
		this.description = updateSkill.getDescription();
	}
}
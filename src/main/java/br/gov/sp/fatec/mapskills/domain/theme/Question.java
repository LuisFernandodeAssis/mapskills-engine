/*
 * @(#)Question.java 1.0 01/11/2016
 *
 * Copyright (c) 2016, Fatec Jessen Vidal. All rights reserved.
 * Fatec Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.domain.theme;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.util.CollectionUtils;

import br.gov.sp.fatec.mapskills.domain.skill.Skill;
import lombok.Getter;
/**
 * 
 * A classe {@link Question} representa uma
 * questao que um cena possa haver ou nao.
 *
 * @author Marcelo
 * @version 1.0 21/05/2017
 */
@Entity
@Table(name = "MAPSKILLS.QUESTION")
public class Question {

	@Getter
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "ID_QUESTION")
	private final List<Alternative> alternatives = new ArrayList<>(4);
	
	@ManyToOne
	@JoinColumn(name = "ID_SKILL")
	private Skill skill;
			
	@SuppressWarnings("unused")
	private Question() {
		this(Collections.emptyList(), null);
	}
	
	public Question(final List<Alternative> alternatives, final Skill skill) {
		addAllAlternatives(alternatives);
		this.skill = skill;
	}
	
	public Skill getSkill() {
		return skill;
	}
	
	public Long getSkillId() {
		return skill.getId();
	}

	public List<Alternative> getAlternatives() {
		return Collections.unmodifiableList(alternatives);
	}
	
	public void update(final Question updateQuestion) {
		this.skill = updateQuestion.getSkill();
		updateAlternatives(updateQuestion.getAlternatives());
	}
	
	public void updateAlternatives(final List<Alternative> updateAlternatives) {
		this.alternatives.stream().forEach(alternative -> {
			int index = 0;
			alternative.update(updateAlternatives.get(index++));
		});
	}
	
	private void addAllAlternatives(final List<Alternative> alternatives) {
		if(!CollectionUtils.isEmpty(alternatives)) {
			this.alternatives.clear();
			this.alternatives.addAll(alternatives);			
		}
	}
}
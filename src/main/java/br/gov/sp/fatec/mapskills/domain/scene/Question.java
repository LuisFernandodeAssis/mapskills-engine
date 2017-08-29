/*
 * @(#)Question.java 1.0 01/11/2016
 *
 * Copyright (c) 2016, Fatec Jessen Vidal. All rights reserved.
 * Fatec Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.domain.scene;

import java.io.Serializable;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.util.CollectionUtils;
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
public class Question implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "QUE_ID")
	private Long id;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "QUE_ID")
	private final List<Alternative> alternatives = new ArrayList<>(4);
	
	@Column(name = "SKI_ID", nullable = false)
	private Long skillId;
			
	@SuppressWarnings("unused")
	private Question() {
		this(null, null, null);
	}
	
	public Question(final Long id, final List<Alternative> alternatives, final Long skillId) {
		this.id = id;
		addAllAlternatives(alternatives);
		this.skillId = skillId;
	}
	
	public void setId(final Long id) {
		this.id = id;
	}
	
	public Long getId() {
		return id;
	}
	
	public Long getSkillId() {
		return skillId;
	}

	public List<Alternative> getAlternatives() {
		return Collections.unmodifiableList(alternatives);
	}
	
	private void addAllAlternatives(final List<Alternative> alternatives) {
		if(!CollectionUtils.isEmpty(alternatives)) {
			this.alternatives.clear();
			this.alternatives.addAll(alternatives);			
		}
	}

}

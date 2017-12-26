/*
 * @(#)Scene.java 1.0 04/01/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.domain.theme;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.util.ObjectUtils;

import lombok.Getter;

/**
 * 
 * A classe {@link Scene} representa uma cena de tema
 * de jogo da aplicacao.
 * 
 * @see GameTheme
 * @author Marcelo
 * @version 1.0 04/01/2017
 */
@Getter
@Entity
@Table(name = "MAPSKILLS.SCENE")
public class Scene {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	
	/** 
	 * Posição da qual a cena deve ser exibida,
	 * 'underline' utilizado devido a palavra 
	 * reservada do banco de dados.
	 */
	@Column(name = "_INDEX", nullable = false)
	private Integer index;
	
	@Column(name = "TEXT", nullable = false)
	private String text;
	
	@Column(name = "IMAGE_NAME", nullable = false)                                                                     
	private String imageName;
	
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "ID_QUESTION")
	private Question question;
	
	@SuppressWarnings("unused")
	private Scene() {
		this(null, null, null, null, null);
	}
	
	public Scene(final Long id, final Integer index, final String text,
			final String imageName, final Question question) {
		this(index, text, imageName, question);
		this.id = id;
	}
	
	public Scene(final Integer index, final String text,
			final String imageName, final Question question) {
		this.index = index;
		this.text = text;
		this.imageName = imageName;
		this.question = question;
	}
	
	public void deleteQuestion() {
		this.question = null;
	}
	
	public String update(final Scene updateScene) {
		this.text = updateScene.getText();
		final String oldImageName = this.imageName;
		this.imageName = updateScene.getImageName();
		updateQuestion(updateScene.getQuestion());
		return oldImageName;
	}
	
	public void setIndex(final Integer newIndex) {
		this.index = newIndex;
	}
	
	private void updateQuestion(final Question updateQuestion) {
		if(ObjectUtils.isEmpty(updateQuestion)) {
			deleteQuestion();
		} else if(ObjectUtils.isEmpty(this.question)) {
			this.question = updateQuestion;
		} else {
			this.question.update(updateQuestion);
		}
	}
}
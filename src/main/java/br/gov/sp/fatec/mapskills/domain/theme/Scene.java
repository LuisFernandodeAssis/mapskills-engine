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

@Getter
@Entity
@Table(name = "MAPSKILLS.SCENE")
public class Scene {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "INDEX", nullable = false)
	private Integer index;
	
	@Column(name = "TEXT", nullable = false)
	private String text;
	
	@Column(name = "URL_BACKGROUND", nullable = false)                                                                     
	private String urlBackground;
	
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name="ID_QUESTION")
	private Question question;
	
	@SuppressWarnings("unused")
	private Scene() {
		this(null, null, null, null);
	}
	
	public Scene(final Integer index, final String text,
			final String urlBackground, final Question question) {
		this.index = index;
		this.text = text;
		this.urlBackground = urlBackground;
		this.question = question;
	}
	
	public void deleteQuestion() {
		this.question = null;
	}
	
	public void update(final Scene updateScene) {
		this.text = updateScene.getText();
		this.urlBackground = updateScene.getUrlBackground();
		updateQuestion(updateScene.getQuestion());
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
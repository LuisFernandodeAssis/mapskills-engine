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

import br.gov.sp.fatec.mapskills.restapi.wrapper.SceneWrapper;
import lombok.Getter;

/**
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
	
	public String update(final Long themeId, final SceneWrapper sceneWrapper) {
		final Scene scene = sceneWrapper.getScene();
		this.text = scene.getText();
		final String oldImageName = this.imageName;
		if (sceneWrapper.containsBase64()) {
			this.imageName = scene.getImageName();
			formatImageName(themeId);
		}
		updateQuestion(scene.getQuestion());
		return oldImageName;
	}
	
	public void setIndex(final Integer newIndex) {
		this.index = newIndex;
	}

	/**
	 * Atribui um index a cena de acordo com próximo disponível no tema
	 * e formata o nome da imagem.
	 * 
	 * @param index
	 * 		posicao a ser exibida no jogo.
	 * @param themeId
	 * 		id do tema.
	 */
	public void prepareContext(final Integer index, final Long themeId) {
		setIndex(index);
		formatImageName(themeId);
	}
	
	/**
	 * <p>Formata o nome da imagem adicionando um prefixo com o id do {@link GameTheme}
	 * e index da {@link Scene} afim de que seja unica no contexto.</p>
	 * Ex.:
	 * <ul>
	 * <li> id do tema = 10
	 * <li> index da cena = 70
	 * <li> imageName = scene0005.jpg
	 * </ul>
	 * <p>
	 * O nome da imagem será 10_70_scene0005.jpg
	 * <p>
	 * @param themeId
	 * 		id do tema.
	 */
	private void formatImageName(final Long themeId) {
		this.imageName = String.format("%d_%d_%s", themeId, index, imageName).toLowerCase();		
	}
	
	/**
	 * Atualiza a questão atribuída a cena.
	 * 
	 * @param updateQuestion
	 * 		questao atualizada.
	 */
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
/*
 * @(#)GameTheme.java 1.0 01/11/2016
 *
 * Copyright (c) 2016, Fatec Jessen Vidal. All rights reserved.
 * Fatec Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.domain.theme;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import br.gov.sp.fatec.mapskills.domain.ObjectNotFoundException;
import br.gov.sp.fatec.mapskills.domain.event.DomainEvent;
import br.gov.sp.fatec.mapskills.domain.studentquestioncontext.StudentQuestionContext;
import br.gov.sp.fatec.mapskills.restapi.wrapper.FileContextWrapper;
import br.gov.sp.fatec.mapskills.restapi.wrapper.SceneWrapper;
import lombok.Getter;
/**
 * 
 * A classe {@link GameTheme} representa um tema
 * de jogo que pode aplicado pelo mentor.
 *
 * @author Marcelo
 * @version 1.0 01/11/2016
 */
@Getter
@Entity
@Table(name = "MAPSKILLS.GAME_THEME")
public class GameTheme {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "NAME", nullable = false)
	private String name;
		
	@Column(name = "ACTIVE", nullable = false)
	private Boolean active = false;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "ID_GAME_THEME")
	@OrderBy("index")
	private final List<Scene> scenes = new LinkedList<>();
	
	@SuppressWarnings("unused")
	private GameTheme() {
		this(null);
	}
	
	public GameTheme(final String name) {
		this.name = name;
	}
	
	public void update(final GameTheme updateTheme) {
		this.name = updateTheme.getName();
	}
	
	public void disable() {
		this.active = false;
	}
	
	public void enable() {
		this.active = true;
	}	

	public void updateStatus(final boolean status) {
		this.active = status;
	}
	
	public boolean isActive() {
		return active;
	}
	
	public void addScene(final Scene newScene) {
		newScene.prepareContext(getNextIndex(), id);
		this.scenes.add(newScene);
	}
	
	public void deleteQuestion(final Long sceneId) {
		final Optional<Scene> optionalScene = getSceneById(sceneId);
		if(!optionalScene.isPresent()) {
			throw new ObjectNotFoundException("cena de ID = " + sceneId + "não encontrada");
		}
		final Scene scene = optionalScene.get();
		scene.deleteQuestion();
	}
	
	public DomainEvent deleteScene(final Long sceneId) {
		final Optional<Scene> aScene = getSceneById(sceneId);
		if(!aScene.isPresent()) {
			throw new ObjectNotFoundException("cena de ID = " + sceneId + "não encontrada");
		}
		final Scene scene = aScene.get();
		this.scenes.remove(scene);
		return new SceneWasDeletedEvent(scene.getImageName());
	}
	
	public void updateSceneIndexes(final List<Scene> updateScenes) {
		updateScenes.stream().forEach(aScene -> {
			Optional<Scene> scene = getSceneById(aScene.getId());
			scene.get().setIndex(aScene.getIndex());
		});
	}
	
	public List<Scene> getScenesNotAnswered(final Optional<StudentQuestionContext> lastContext) {
		if (!lastContext.isPresent()) {
			return this.scenes;
		}
		final Optional<Scene> lastSceneAnswered = getSceneById(lastContext.get().getSceneId());
		final int index = this.scenes.indexOf(lastSceneAnswered.get());
		return this.scenes.subList(index + 1, this.scenes.size());
	}
	
	public DomainEvent updateScene(final Long sceneId, final SceneWrapper sceneWrapper) {
		final Optional<Scene> aScene = getSceneById(sceneId);
		if (aScene.isPresent()) {
			final Scene scene = aScene.get();
			final String oldImageName = scene.update(this.id, sceneWrapper);
			return new SceneWasUpdatedEvent(new SceneUpdateContext(
					new FileContextWrapper(sceneWrapper.getBase64(), scene.getImageName()), oldImageName));
		}
		return null;
	}
	
	private Integer getNextIndex() {
		final Long nextIndex = this.scenes.stream().count() + 1L;
		return new Integer(nextIndex.intValue());
	}
	
	private Optional<Scene> getSceneById(final Long sceneId) {
		return this.scenes.stream().filter(scene -> scene.getId().equals(sceneId)).findFirst();
	}
}
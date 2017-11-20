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
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import br.gov.sp.fatec.mapskills.domain.ObjectNotFoundException;
import br.gov.sp.fatec.mapskills.domain.studentquestioncontext.StudentQuestionContext;
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
	@JoinTable(name = "MAPSKILLS.SCENE",
		joinColumns = @JoinColumn(name = "ID_GAME_THEME"),
		inverseJoinColumns = @JoinColumn(name = "ID"))
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
		active = false;
	}
	
	public void enable() {
		active = true;
	}
	
	public boolean isActive() {
		return active;
	}
	
	public void addScene(final Scene newScene) {
		newScene.setIndex(getNextIndex());
		scenes.add(newScene);
	}
	
	public void deleteQuestion(final Long sceneId) {
		final Optional<Scene> optionalScene = getSceneById(sceneId);
		if(!optionalScene.isPresent()) {
			throw new ObjectNotFoundException("cena de ID = " + sceneId + "não encontrada");
		}
		final Scene scene = optionalScene.get();
		scene.deleteQuestion();
	}
	
	public void deleteScene(final Long sceneId) {
		final Optional<Scene> optionalScene = getSceneById(sceneId);
		if(!optionalScene.isPresent()) {
			throw new ObjectNotFoundException("cena de ID = " + sceneId + "não encontrada");
		}
		final Scene scene = optionalScene.get();
		scenes.remove(scene);		
	}
	
	public void updateSceneIndexes(final List<Scene> updateScenes) {
		updateScenes.stream().forEach(aScene -> {
			Optional<Scene> scene = getSceneById(aScene.getId());
			scene.get().setIndex(aScene.getIndex());
		});
	}
	
	public List<Scene> getScenesNotAnswered(final List<StudentQuestionContext> context) {
		final List<Scene> scenesNotAnswered = new LinkedList<>();
		for (final Scene scene : this.scenes) {
			if(context.stream().noneMatch(c -> c.getSceneId().equals(scene.getId()))) {
				scenesNotAnswered.add(scene);
			}
		}
		return scenesNotAnswered;
	}
	
	private Integer getNextIndex() {
		final Long nextIndex = scenes.stream().count() + 1L;
		return new Integer(nextIndex.intValue());
	}
	
	private Optional<Scene> getSceneById(final Long sceneId) {
		return scenes.stream().filter(scene -> scene.getId().equals(sceneId)).findFirst();
	}
}
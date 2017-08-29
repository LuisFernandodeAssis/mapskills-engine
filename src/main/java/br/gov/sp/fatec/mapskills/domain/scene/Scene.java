/*
 * @(#)Scene.java 1.0 04/01/2017
 *
 * Copyright (c) 2016, Fatec-Jessen Vidal. All rights reserved.Fatec-Jessen Vidal 
 * proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.domain.scene;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;

@Getter
@Entity
@Table(name = "MAPSKILLS.SCENE")
public class Scene implements Serializable, Comparable<Scene> {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SCN_ID")
	private Long id;
	
	@Column(name = "SCN_INDEX", nullable = false)
	private Integer index = -1;
	
	@Column(name = "SCN_TEXT", nullable = false)
	private String text;
	
	@Column(name = "SCN_URL_BACKGROUND", nullable = false)
	private String urlBackground;
	
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name="QUE_ID")
	private Question question;
	
	@Column(name = "GTH_ID", nullable = false)
	private Long gameThemeId;
	
	@SuppressWarnings("unused")
	private Scene() {
		this(null, null, null, null, null, null);
	}
	
	public Scene(final Long id, final Integer index, final String text, final String urlBackground,
			final Question question, final Long gameThemeId) {
		this.id = id;
		this.index = index;
		this.text = text;
		this.urlBackground = urlBackground;
		this.question = question;
		this.gameThemeId = gameThemeId;
	}
	
	public void setIndex(final Integer index) {
		this.index = index;
	}
	
	public void setId(final Long id) {
		this.id = id;
	}
	
	public void deleteQuestion() {
		this.question = null;
	}
	
	/**
	 * Metodo eh utilizado para realizar a ordenacao das cenas através do index da cena.
	 */
	@Override
	public int compareTo(final Scene scene) {
		return this.index < scene.getIndex() ? -1 : (this.index == scene.index ? 0 : 1);
	}
	
	@Override
	public boolean equals(final Object scene) {
		return this.equals(scene);
	}
	
	@Override
    public int hashCode() {
        return Integer.parseInt(String.valueOf(this.id)) ;
    }

}

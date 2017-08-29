/*
 * @(#)GameTheme.java 1.0 01/11/2016
 *
 * Copyright (c) 2016, Fatec Jessen Vidal. All rights reserved.
 * Fatec Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.domain.theme;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
/**
 * 
 * A classe {@link GameTheme} representa um tema
 * de jogo que pode aplicado pelo mentor.
 *
 * @author Marcelo
 * @version 1.0 01/11/2016
 */
@Getter
@Setter
@Entity
@Table(name = "MAPSKILLS.GAME_THEME")
public class GameTheme {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "GTH_ID")
	private Long id;
	
	@Column(name = "GTH_NAME", nullable = false)
	private String name;
		
	@Column(name = "GTH_IS_ACTIVE", nullable = false)
	private Boolean active = false;
	
	@SuppressWarnings("unused")
	private GameTheme() {
		this(null);
	}
	
	public GameTheme(final String name) {
		this.name = name;
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

}

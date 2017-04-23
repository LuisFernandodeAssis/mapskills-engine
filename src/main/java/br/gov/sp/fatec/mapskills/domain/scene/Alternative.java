/*
 * @(#)Alternative.java 1.0 01/11/2016
 *
 * Copyright (c) 2016, Fatec Jessen Vidal. All rights reserved. Fatec Jessen Vidal
 * proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.domain.scene;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
@Entity
@Table(name = "ALTERNATIVE")
public class Alternative implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "alt_id")
	private long id;
	
	@Column(name = "alt_description", nullable = false)
	private String description;
	
	@Column(name = "alt_skill_value", nullable = false)
	private int skillValue;
		
	public Alternative() {
		// CONSTRUCTOR DEFAULT
	}
	
	public Alternative setId(final long id) {
		this.id = id;
		return this;
	}

	public void changeDescription(final String newDescription) {
		this.description = newDescription;
	}
	
	public void changeSkillValue(final int newSkillValue) {
		this.skillValue = newSkillValue;
	}

}

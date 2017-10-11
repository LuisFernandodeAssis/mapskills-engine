/*
 * @(#)Mentor.java 1.0 01/11/2016
 *
 * Copyright (c) 2016, Fatec Jessen Vidal. All rights reserved.
 * Fatec Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.domain.institution;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import br.gov.sp.fatec.mapskills.domain.user.Login;
import br.gov.sp.fatec.mapskills.domain.user.ProfileType;
import br.gov.sp.fatec.mapskills.domain.user.User;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 * A classe {@link Mentor}
 *
 * @author Marcelo
 * @version 1.0 01/11/2016
 */
@Getter
@Entity
@Table(name = "MAPSKILLS.MENTOR")
@PrimaryKeyJoinColumn(name = "ID_USER")
@DiscriminatorValue("1")
public class Mentor extends User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	
	@Setter
	@ManyToOne
	@JoinColumn(name = "ID_INSTITUTION")
	private Institution institution;

	@SuppressWarnings("unused")
	private Mentor() {
		this(null, null, null);
	}
	
	public Mentor(final String name, final String username, final String password) {
		super(name, new Login(username, password));
	}
	
	public void update(final Mentor mentorUpdate) {
		super.update(mentorUpdate);
		this.institution = mentorUpdate.getInstitution();
	}	
	
	public Long getInstitutionId() {
		return institution.getId();
	}
	
	public String getInstitutionCode() {
		return institution.getCode();
	}

	@Override
	public ProfileType getProfile() {
		return ProfileType.MENTOR;
	}
}
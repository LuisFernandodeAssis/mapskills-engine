/*
 * @(#)Mentor.java 1.0 01/11/2016
 *
 * Copyright (c) 2016, Fatec Jessen Vidal. All rights reserved.
 * Fatec Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.domain.user.mentor;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import br.gov.sp.fatec.mapskills.domain.institution.Institution;
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
@Setter
@Entity
@Table(name = "MAPSKILLS.MENTOR")
@PrimaryKeyJoinColumn(name = "USE_ID")
public class Mentor extends User {

	private static final long serialVersionUID = 1L;
	
	@ManyToOne
	@JoinColumn(name = "INS_ID")
	private Institution institution;

	@SuppressWarnings("unused")
	private Mentor() {
		this(null, null, null, null);
	}
	
	public Mentor(final String name, final String username, final String password, final Institution institution) {
		super(name, new Login(username, password), ProfileType.MENTOR);
		this.institution = institution;
	}
	
	public void update(final Mentor mentorUpdate) {
		super.update(mentorUpdate);
		setInstitution(mentorUpdate.getInstitution());
	}	
	
	public Long getInstitutionId() {
		return institution.getId();
	}
	
	public String getInstitutionCode() {
		return institution.getCode();
	}

}

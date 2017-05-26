/*
 * @(#)User.java 1.0 01/11/2016
 *
 * Copyright (c) 2016, Fatec Jessen Vidal. All rights reserved. Fatec Jessen Vidal
 * proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.domain.user;

import java.io.Serializable;
import java.security.Principal;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
/**
 * A classe abstrata <code>User</code> � uma entidade que 
 * representa usuario generico que pode acessar a aplicacao.
 * 
 * @author Marcelo
 *
 */
@Getter
@Setter
@Entity
@Table(name = "USER")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class User implements Principal, Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "use_id")
	private long id;
	
	@Column(name = "use_name")
	private String name;

	@Embedded
	private Login login;
	
	@Enumerated(value = EnumType.ORDINAL)
	@Column(name = "pro_id")
	private ProfileType profile;
		
	public User() {
		// CONSTRUCTOR DEFAULT
	}
	
	public User(final long id, final String name, final Login login, final ProfileType profile) {
		this(name, login, profile);
		this.id = id;
	}
	
	public User(final String name, final Login login, final ProfileType profile) {
		this.name = name;
		this.login = login;
		this.profile = profile;
	}
	
	public ProfileType getProfile() {
		return profile;
	}
		
	public String getUsername() {
		return login.getUsername();
	}
	
	public String getPassword() {
		return login.getPassword();
	}
	
	public void setUsername(final String username) {
		login.setUsername(username);
	}
	
	public void setPassword(final String hash) {
		login.setPassword(hash);
	}
	
}

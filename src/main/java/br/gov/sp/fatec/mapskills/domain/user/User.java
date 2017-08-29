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
import javax.persistence.DiscriminatorColumn;
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

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
/**
 * A classe abstrata <code>User</code> é uma entidade que 
 * representa usuario generico que pode acessar a aplicacao.
 * 
 * @author Marcelo
 *
 */
@Getter
@Setter
@Entity
@Table(name = "MAPSKILLS.USER")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "PRO_ID")
public abstract class User implements Principal, Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "USE_ID")
	@Setter(AccessLevel.NONE)
	private Long id;
	
	@Column(name = "USE_NAME")
	private String name;

	@Embedded
	private Login login;
	
	@Enumerated(value = EnumType.ORDINAL)
	@Column(name = "PRO_ID")
	private ProfileType profile;
	
	protected User() {
		this(null, null, null);
	}
		
	public User(final String name, final Login login, final ProfileType profile) {
		this.name = name;
		this.login = login;
		this.profile = profile;
	}
	
	public void update(final User userUpdate) {
		this.name = userUpdate.getName();
		updateLogin(userUpdate.getLogin());
		
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
	
	public void updateLogin(final Login newLogin) {
		login.update(newLogin);
	}
	
	public void setUsername(final String username) {
		login.setUsername(username);
	}
	
	public void setPassword(final String hash) {
		login.setPassword(hash);
	}
	
}

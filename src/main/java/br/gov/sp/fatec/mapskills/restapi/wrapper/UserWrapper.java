/*
 * @(#)UserWrapper.java 1.0 10/11/2016
 *
 * Copyright (c) 2016, Fatec Jessen Vidal. All rights reserved.
 * Fatec Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.restapi.wrapper;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import br.gov.sp.fatec.mapskills.domain.user.ProfileType;
import br.gov.sp.fatec.mapskills.domain.user.User;
import br.gov.sp.fatec.mapskills.restapi.serializer.UserSerializer;
/**
 * 
 * A classe {@link UserWrapper} encapsula um perfil
 * de usuario da aplicacao para seja serializado.
 *
 * @author Marcelo
 * @version 1.0 10/11/2016
 */
@JsonSerialize(using = UserSerializer.class)
public class UserWrapper {
	
	private final User user;
	
	public UserWrapper(final User user) {
		this.user = user;
	}
	
	public ProfileType getProfile() {
		return user.getProfile();
	}
	
	public User getUser() {
		return user;
	}
}
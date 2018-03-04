/*
 * @(#)UserApplicationServices.java 1.0 01/11/2016
 *
 * Copyright (c) 2016, Fatec Jessen Vidal. All rights reserved.
 * Fatec Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */

package br.gov.sp.fatec.mapskills.application;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.ObjectUtils;

import br.gov.sp.fatec.mapskills.domain.user.Login;
import br.gov.sp.fatec.mapskills.domain.user.User;
import br.gov.sp.fatec.mapskills.domain.user.UserNotFoundException;
import br.gov.sp.fatec.mapskills.domain.user.UserRepository;
import lombok.AllArgsConstructor;

/**
 * A classe {@link UserApplicationServices} e responsavel por conter
 * as regras de negocio que diz respeito aos perfils de usuarios. 
 *
 * @author Marcelo
 * @version 1.0 01/11/2016
 */
@ApplicationServices
@AllArgsConstructor
public class UserApplicationServices {
	
	private final UserRepository repository;
	private final PasswordEncoder encoder;

	public User findUserByUsername(final String username) {
		final User user = repository.findByUsername(username);
		if (ObjectUtils.isEmpty(user)) {
			throw new UserNotFoundException(username);
		}
		return user;
	}
	
	/**
	 * Valida o usuario, verificando se ele nao eh nulo ou possua as credenciais invalidas
	 * 
	 * @param user Usuario a ser validado
	 * @param password Senha a ser validade
	 */
	public void authenticate(final User user, final String password) {
		if (ObjectUtils.isEmpty(user) || !encoder.matches(password, user.getPassword())) {
			throw new BadCredentialsException("username/password invalid");
		}
	}
	
	@PreAuthorize("isFullyAuthenticated()")
	public void updatePassword(final String username, final String newPassword) {
		final User user = repository.findByUsername(username);
		user.updateLogin(new Login(user.getUsername(), encoder.encode(newPassword)));
		repository.save(user);
	}
}
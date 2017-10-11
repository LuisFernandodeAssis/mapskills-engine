/*
 * @(#)UserService.java 1.0 01/11/2016
 *
 * Copyright (c) 2016, Fatec Jessen Vidal. All rights reserved.
 * Fatec Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.application;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.ObjectUtils;

import br.gov.sp.fatec.mapskills.domain.user.Administrator;
import br.gov.sp.fatec.mapskills.domain.user.User;
import br.gov.sp.fatec.mapskills.domain.user.UserNotFoundException;
import br.gov.sp.fatec.mapskills.domain.user.UserRepository;
import lombok.AllArgsConstructor;

/**
 * 
 * A classe {@link UserApplicationServices} e responsavel por conter
 * as regras de negocio que diz respeito aos perfils de usuarios. 
 *
 * @author Marcelo
 * @version 1.0 01/11/2016
 */
@ApplicationServices
@AllArgsConstructor
public class UserApplicationServices {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserApplicationServices.class);
	
	private final UserRepository repository;
	private final PasswordEncoder encoder;
	
	public void save(final Administrator admin) {
		repository.save(admin);
	}

	public User findUserByUsername(final String username) {
		final User user = repository.findByUsername(username);
		if (ObjectUtils.isEmpty(user)) {
			throw new UserNotFoundException(username);
		}
		return user;
	}
	
	public User findByUsername(final String username) {
		return repository.findByUsername(username);
	}
	
	/**
	 * Valida o usuario, verificando se ele nao eh nulo ou possua as credenciais invalidas
	 * 
	 * @param user Usuario a ser validado
	 * @param password Senha a ser validade
	 */
	public void authenticate(final User user, final String password) {
		if (ObjectUtils.isEmpty(user) || !encoder.matches(password, user.getPassword())) {
			LOGGER.warn("username/password invalid");
			throw new BadCredentialsException("username/password invalid");
		}
	}
	
	public void updatePassword(final String username, final String newPassword) {
		final User user = repository.findByUsername(username);
		user.setPassword(encoder.encode(newPassword));
		repository.save(user);
	}	
}
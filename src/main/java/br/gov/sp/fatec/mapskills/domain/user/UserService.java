/*
 * @(#)UserService.java 1.0 01/11/2016
 *
 * Copyright (c) 2016, Fatec Jessen Vidal. All rights reserved.
 * Fatec Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.domain.user;

import java.util.logging.Logger;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.gov.sp.fatec.mapskills.domain.MapSkillsException;
import lombok.AllArgsConstructor;

/**
 * 
 * A classe {@link UserService} e responsavel por conter
 * as regras de negocio que diz respeito aos perfils de usuarios. 
 *
 * @author Marcelo
 * @version 1.0 01/11/2016
 */
@Service
@AllArgsConstructor
public class UserService {
	
	private static final Logger LOGGER = Logger.getLogger(UserService.class.getName());
	
	private final UserRepository repository;
	private final PasswordEncoder encoder;
	
	public void save(final Administrator admin) {
		repository.save(admin);
	}

	public User findUserByUsername(final String username) throws MapSkillsException {
		final User user = repository.findByUsername(username);
		if (user == null) {
			throw new UserNotFoundException(username);
		}
		return user;
	}
	
	public User findByUsername(final String username) {
		return repository.findByUsername(username);
	}
	
	/**
	 * valida o usuario, verificando se ele nao eh nulo ou possua as credenciais invalidas
	 * @param user Usuario a ser validado
	 * @param password Senha a ser validade
	 */
	public void authenticate(final User user, final String password) {
		if(user == null || !encoder.matches(password, user.getPassword())) {
			LOGGER.warning("username/password invalid");
			throw new BadCredentialsException("username/password invalid");
		}
	}
	
	public void updatePassword(final String username, final String newPassword) {
		final User user = repository.findByUsername(username);
		user.setPassword(encoder.encode(newPassword));
		repository.save(user);
	}
	
}
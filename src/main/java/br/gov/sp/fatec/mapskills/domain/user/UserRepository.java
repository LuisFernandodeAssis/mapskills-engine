/*
 * @(#)UserRepository.java 1.0 01/11/2016
 *
 * Copyright (c) 2016, Fatec Jessen Vidal. All rights reserved.
 * Fatec Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.domain.user;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
/**
 * 
 * A classe {@link UserRepository} e responsavel por
 * que seja possivel realizar as buscas dos usuarios
 * da aplicacao, sem perfil especifico.
 *
 * @author Marcelo
 * @version 1.0 01/11/2016
 */
public interface UserRepository extends CrudRepository<User, Long> {

	User findByLogin(final Login login);
	
	/**
	 * Metodo que recupera um usuario da aplicao.
	 * 
	 * @param username
	 * 			E-mail que o usuario foi cadastrado.
	 * @return
	 * 			Usuario se encontrado, ou nulo caso nao encontrado.
	 */
	@Query("SELECT user FROM User user WHERE user.login.username = ?1")
	User findByUsername(final String username);
	
	@Query("SELECT user FROM #{#entityName} user WHERE user.login.username = ?#{principal.login.username}")
	User getLoggedUser();
}
/*
 * @(#)AuthenticationController.java 1.0 03/01/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.restapi;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.gov.sp.fatec.mapskills.domain.user.User;
import br.gov.sp.fatec.mapskills.domain.user.UserService;
import br.gov.sp.fatec.mapskills.restapi.wrapper.UserWrapper;
import lombok.AllArgsConstructor;

/**
 * A classe <code>AuthenticationController</code> eh responsavel por conter a
 * rota (uri) de detalhes do usuario logado na aplicacao.
 * 
 * @author Marcelo
 *
 */
@RestController
@AllArgsConstructor
public class AuthenticationController {
	
	private final UserService userService;
	
	/**
	 * Metodo que retorna os detalhes do usu�rio logado na aplicacao.
	 * @param username
	 * @return <code>ResponseEntity -UserWrapper-</code>
	 */
	@RequestMapping(value = "/user/details", method = RequestMethod.POST)
	public ResponseEntity<UserWrapper> login(@RequestParam("username") String username) {
		
		final User user = userService.findByUsername(username);
		final UserWrapper userWrapper = new UserWrapper(user);
		return new ResponseEntity<>(userWrapper, HttpStatus.OK);
	}
	/**
	 * Metodo que realiza a alteracao de senha do usuario
	 * @param username
	 * @param newPassword
	 * @return
	 */
	@RequestMapping(value = "/user/change/password", method = RequestMethod.POST)
	public ResponseEntity<HttpStatus> changePassword(@RequestParam("username") String username,
			@RequestParam("newPassword") String newPassword) {
		
		userService.updatePassword(username, newPassword);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}

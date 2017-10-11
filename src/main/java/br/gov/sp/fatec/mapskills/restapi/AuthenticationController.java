/*
 * @(#)AuthenticationController.java 1.0 03/01/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.restapi;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.gov.sp.fatec.mapskills.application.UserApplicationServices;
import br.gov.sp.fatec.mapskills.domain.user.User;
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
	
	private final UserApplicationServices userService;
	
	/**
	 * 
	 * Metodo que retorna os detalhes do usuario logado na aplicacao.
	 */
	@PostMapping(value = "/user/details")
	public UserWrapper login(@RequestParam("username") final String username) {
		
		final User user = userService.findByUsername(username);
		return new UserWrapper(user);
	}
	
	/**
	 * 
	 * End-point que expoe servico para realizacao da troca de senha de usuario da aplicacao.
	 */
	@PostMapping(value = "/user/change/password")
	public void changePassword(@RequestParam("username") final String username,
			@RequestParam("newPassword") final String newPassword) {
		
		userService.updatePassword(username, newPassword);
	}
}
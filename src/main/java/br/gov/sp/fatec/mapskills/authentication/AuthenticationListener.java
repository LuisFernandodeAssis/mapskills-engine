/*
 * @(#)AuthenticationListener.java 1.0 27/01/2017
 *
 * Copyright (c) 2017, Fatec Jessen Vidal. All rights reserved. Fatec Jessen Vidal
 * proprietary/confidential. Use is subject to license terms.
 */

package br.gov.sp.fatec.mapskills.authentication;

import java.io.IOException;

import javax.servlet.ServletException;

/**
 * A interface <code>AuthenticationListener</code> define o método a ser
 * executado após uma autenticação com sucesso na aplicação. Cada implementação
 * dessa interface terá um propósito específico, como registrar o usuário no
 * cache, registrar o login no banco para efeitos de auditoria, etc.
 *
 * @author Victor Amano Izawa
 * @author Roberto Perillo
 * @version 1.0 05/02/2016
 */
@FunctionalInterface
public interface AuthenticationListener {
	
	/**
     * Executa uma ação após a autenticação com sucesso na aplicação.
     * 
     * @param event
     *            O evento de autenticação.
     * @throws IOException
     *             Caso ocorra algum problema de I/O, como ao enviar um dado ao
     *             usuário por meio do <code>OutputStream</code> do objeto
     *             <code>HttpServletResponse</code>, presente no evento.
     * @throws ServletException
     *             Caso ocorra algum problema ao lidar com os objetos de
     *             requisição do usuário.
     */
    void onAuthenticationSuccess(final AuthenticationEvent event);

}
/*
 * @(#)CorsFilter.java 1.0 08/01/2017
 *
 * Copyright (c) 2016, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */

package br.gov.sp.fatec.mapskills.config;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * O filtro <code>SCMCorsFilter</code> tem a função de adicionar os seguintes
 * headers na resposta ser enviada para quem efetuou a requisição. <br/>
 * <ul>
 * <li>{@link org.springframework.http.HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN}
 * </li>
 * <li>{@link org.springframework.http.HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS}
 * </li>
 * <li>{@link org.springframework.http.HttpHeaders.ACCESS_CONTROL_MAX_AGE}</li>
 * <li>{@link org.springframework.http.HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS}
 * </li>
 * </ul>
 * <p>
 * Esse filtro deve ser utilizado para considerar quando a aplicação precisa
 * tratar requisições que usam o protocolo Cross-origin resource sharing (CORS).
 * Onde permite que seus recursos sejam acessados por uma página web de um
 * domínio diferente.
 * </p>
 * De acordo com a documentação do
 * Spring(https://spring.io/guides/tutorials/spring-security-and-angular-js/)
 * para que seja possível tratar as requisições "pre-flight" OPTIONS devem ser
 * aplicados alguns headers na resposta a ser enviada para quem efetuou a
 * requisição.
 * <p>
 * Esse filtro deve ser aplicado antes dos filtros de segurança do Spring
 * Security.
 * </p>
 *
 * @author Roberto Perillo
 * @author Victor Amano Izawa
 * @version 1.0 10/12/2015
 */
public class CorsFilter extends OncePerRequestFilter {
	
	private final String allowedOrigins;
	
	public CorsFilter(final String allowedOrigins) {
		this.allowedOrigins = allowedOrigins;
	}

	@Override
	protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response,
			final FilterChain chain) throws ServletException, IOException {
		
		final String[] origins = allowedOrigins.split(",");
		final String clientOrigin = request.getHeader("Origin");
        
        Arrays.asList(origins).stream().filter(origin -> origin.equalsIgnoreCase(clientOrigin))
        .findFirst().ifPresent(origin -> response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, origin));
		
		response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "GET, POST, PUT, OPTIONS, DELETE");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_MAX_AGE, "3600");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, "Origin, X-Requested-With, Content-Type, Accept");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Content-Disposition");
        
        if (!HttpMethod.OPTIONS.name().equals(request.getMethod())) {
            chain.doFilter(request, response);
        }
	}
}
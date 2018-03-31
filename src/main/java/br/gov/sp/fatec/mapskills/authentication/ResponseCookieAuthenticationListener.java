/*
 * @(#)ResponseHeaderAuthenticationListener.java 1.0 27/01/2017
 *
 * Copyright (c) 2017, Fatec Jessen Vidal. All rights reserved.
 * Fatec Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */

package br.gov.sp.fatec.mapskills.authentication;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.stereotype.Component;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

/**
 * A classe {@link ResponseCookieAuthenticationListener} e reponsavel por
 * gerar o token JWT, e gravar no header da resposta o token que dara ao
 * cliente autorizacao aos servicos fornecidos pela aplicacao.
 *
 * @author Marcelo
 * @version 1.0 27/01/2017
 */
@Component
public class ResponseCookieAuthenticationListener implements AuthenticationListener {
	
	private Logger logger = LoggerFactory.getLogger(ResponseCookieAuthenticationListener.class);
	private static final long FIVE_HOURS_IN_MILLISECONDS = 60000L * 300L;
    private final JWSSigner signer;
    
    @Autowired
    public ResponseCookieAuthenticationListener(@Value("${jwt.secret}") final String secret) throws JOSEException {
        this.signer = new MACSigner(secret);
    }

	@Override
	public void onAuthenticationSuccess(final AuthenticationEvent event) {
		final long now = System.currentTimeMillis();
		final JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
				.subject(event.getUsername())
				.claim("username", event.getUserDomain().getUsername())
				.claim("profile", event.getUserDomain().getProfile())
				.issueTime(new Date(now))
				.issuer("https://mapskills.fatec.sp.gov.br")
				.expirationTime(new Date(now + FIVE_HOURS_IN_MILLISECONDS))
				.notBeforeTime(new Date(now))
				.build();

        final SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);

        try {
            signedJWT.sign(signer);
        } catch (final JOSEException exception) {
        	logger.error("O JWT não pode ser assinado.", exception);
            throw new AuthenticationServiceException("The given JWT could not be signed.");
        }

        final HttpServletResponse response = event.getResponse();
        final String bearer = String.format("Bearer %s", signedJWT.serialize());
        final Cookie cookie = new Cookie("Authorization", urlEncode(bearer));
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
	}
	
	private String urlEncode(final String param) {
		try {
			return URLEncoder.encode(param, "UTF-8");
		} catch (final UnsupportedEncodingException exception) {
			throw new IllegalArgumentException(exception);
		}
	}
}
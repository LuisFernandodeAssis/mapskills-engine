/*
 * @(#)Base64Parser.java 1.0 03/11/2016
 *
 * Copyright (c) 2016, Fatec Jessen Vidal. All rights reserved.
 * Fatec Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.infra;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Base64;

import org.springframework.stereotype.Component;
/**
 * 
 * A classe {@link Base64Parser} tem a unica resposabilidade de
 * converter um objeto String em base 64 em objeto InputStream.
 *
 * @author Marcelo
 * @version 1.0 03/11/2016
 */
@Component
public class Base64Parser {
	
	/**
	 * Metodo <code>toInputStream</code> converte uma
	 * String de base 64 para um objeto do tipo InputStream.
	 * 
	 * @param charSequence
	 * @return
	 */
	public InputStream toInputStream(final String charSequence) {
		final byte[] decoded = Base64.getDecoder().decode(charSequence);
		return new ByteArrayInputStream(decoded);
	}
	
	/**
	 * Metodo que converte uma String base64 em um array de bytes
	 * @param charSequence
	 * @return
	 */
	public byte[] toByteArray(final String charSequence) {
		return Base64.getDecoder().decode(charSequence);
	}
}
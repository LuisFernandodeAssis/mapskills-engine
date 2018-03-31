/*
 * @(#)InputStreamWrapper.java 1.0 03/11/2016
 *
 * Copyright (c) 2016, Fatec Jessen Vidal. All rights reserved.
 * Fatec Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */

package br.gov.sp.fatec.mapskills.restapi.wrapper;

import java.io.InputStream;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import br.gov.sp.fatec.mapskills.infra.Base64Parser;
import br.gov.sp.fatec.mapskills.restapi.serializer.FileBase64Deserializer;
import br.gov.sp.fatec.mapskills.utils.ApplicationContextHolder;

/**
 * A classe {@link FileBase64Wrapper} representa
 * um objeto de imagem em formato <i>BASE 64</i>.
 *
 * @author Marcelo
 * @version 1.0 03/11/2016
 */
@JsonDeserialize(using = FileBase64Deserializer.class)
public class FileBase64Wrapper {
	
	private final Base64Parser parser = ApplicationContextHolder.getBean("base64Parser", Base64Parser.class);
	private final InputStream inputStream;
	
	public FileBase64Wrapper(final String charSequence) {
		this.inputStream = parser.toInputStream(charSequence);
	}
	
	public InputStream getInputStream() {
		return inputStream;
	}
}
/*
 * @(#)FileContextWrapper.java 1.0 1 19/12/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */

package br.gov.sp.fatec.mapskills.restapi.wrapper;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import br.gov.sp.fatec.mapskills.restapi.serializer.FileContextSerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * A classe {@link FileContextWrapper}
 *
 * @author Marcelo
 * @version 1.0 19/12/2017
 */
@Getter
@AllArgsConstructor
@JsonSerialize(using = FileContextSerializer.class)
public class FileContextWrapper {
	
	private final String base64;
	private final String filename;
	
	public boolean containsBase64() {
		return base64 != null;
	}
}
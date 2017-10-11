/* @(#)InputStreamDeserializer.java 1.0 03/11/2016
 *
 * Copyright (c) 2016, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.restapi.serializer;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;

import br.gov.sp.fatec.mapskills.restapi.wrapper.FileBase64Wrapper;

/**
 * 
 * A classe {@link FileBase64Deserializer} eh responsavel por
 * deserializar a <i>String</i> que representa o arquivo no
 * formato <b>Base64</b>.
 *
 * @author Marcelo
 * @version 1.0 03/11/2016
 */
@Component
public class FileBase64Deserializer extends AbstractJsonDeserializer<FileBase64Wrapper> {

	@Override
	protected FileBase64Wrapper deserialize(JsonNode node) {
		return new FileBase64Wrapper(getFieldTextValue(node, SerializationKey.BASE_64));
	}
}
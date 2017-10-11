/*
 * @(#)ReportFilterDeserializer.java 1.0 18/03/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.restapi.serializer;

import com.fasterxml.jackson.databind.JsonNode;

import br.gov.sp.fatec.mapskills.dashboard.ReportFilter;

/**
 * 
 * A classe {@link ReportFilterDeserializer} e responsavel
 * por deserializar o objeto filtro, para utilizacao das condicionais
 * da query de relatorio.
 *
 * @author Marcelo
 * @version 1.0 18/03/2017
 */
public class ReportFilterDeserializer extends AbstractJsonDeserializer<ReportFilter> {

	@Override
	protected ReportFilter deserialize(JsonNode node) {
		return new ReportFilter(getFieldTextValue(node, SerializationKey.INSTITUTION_LEVEL),
				getFieldTextValue(node, SerializationKey.INSTITUTION_CODE),
				getFieldTextValue(node, SerializationKey.COURSE_CODE),
				getFieldTextValue(node, SerializationKey.START_DATE),
				getFieldTextValue(node, SerializationKey.END_DATE));
	}
}
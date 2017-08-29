/*
 * @(#)ReportViewSerializer.java 1.0 19/03/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.restapi.serializer;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import br.gov.sp.fatec.mapskills.domain.skill.Skill;
import br.gov.sp.fatec.mapskills.infra.report.ReportDefaultData;
import br.gov.sp.fatec.mapskills.restapi.wrapper.ReportViewWrapper;
/**
 * 
 * A classe {@link ReportViewSerializer} e responsavel
 * por serializar a visualizacao de relatorio da aplicacao.
 * RA, NOME e COMPETENCIAS dinamicamente.
 *
 * @author Marcelo
 * @version 1.0 19/03/2017
 */
public class ReportViewSerializer extends JsonSerializer<ReportViewWrapper> {

	@Override
	public void serialize(final ReportViewWrapper report, final JsonGenerator generator, final SerializerProvider arg2)
			throws IOException {
		
		generator.writeStartObject();
		this.generateHeaderLabel(report.getSkills(), generator);
		this.generateDataInfo(report.getDatas(), generator);
		generator.writeEndObject();
	}
	
	private void generateHeaderLabel(final List<Skill> skills, final JsonGenerator generator) throws IOException {
		generator.writeArrayFieldStart("head");
		generator.writeString("RA");
		generator.writeString("NOME");
		for(final Skill skill : skills) {
			generator.writeString(skill.getType());
		}
		generator.writeEndArray();
	}
	
	private void generateDataInfo(final List<ReportDefaultData> datas, final JsonGenerator generator) throws IOException {
		generator.writeArrayFieldStart("datas");
		for(final ReportDefaultData data : datas) {
			generator.writeStartArray();
			generator.writeString(data.getStudentRA());
			generator.writeString(data.getStudentName());
			for(final Object score : data.getScores()) {
				generator.writeString(score.toString());
			}
			generator.writeEndArray();
		}
		generator.writeEndArray();
	}

}

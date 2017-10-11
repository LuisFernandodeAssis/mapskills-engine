/* @(#)StudentResultSerializer.java 1.0 04/01/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.restapi.serializer;

import java.io.IOException;
import java.math.BigDecimal;

import com.fasterxml.jackson.core.JsonGenerator;

import br.gov.sp.fatec.mapskills.domain.skill.Skill;
import br.gov.sp.fatec.mapskills.restapi.wrapper.StudentResultWrapper;
/**
 * 
 * A classe {@link StudentResultSerializer} e responsavel
 * por serializar o resultado de um aluno para que seja
 * exibida no grafico de radar. Devolve o objeto preparado
 * para que o componente de grafico do JS receba.
 *
 * @author Marcelo
 * @version 1.0 04/01/2017
 */
public class StudentResultSerializer extends AbstractJsonSerializer<StudentResultWrapper> {

	@Override
	public void serialize(final StudentResultWrapper result, final JsonGenerator generator)	throws IOException {
		generator.writeStartObject();
		this.generateLabels(result, generator);
		this.geneateValues(result, generator);
		this.generateSkills(result, generator);
		generator.writeEndObject();
	}
	
	/**
	 * Responsavel por serializar os label's do grafico de radar.
	 */
	private void generateLabels(final StudentResultWrapper result, final JsonGenerator generator) throws IOException {
		generator.writeArrayFieldStart("labels");
		for(final String skill : result.getSkills()) {
			generator.writeString(skill);
		}
		generator.writeEndArray();
	}
	
	/**
	 * Responsavel por serializar os valores do grafico de radar.
	 */
	private void geneateValues(final StudentResultWrapper result, final JsonGenerator generator) throws IOException {
		generator.writeArrayFieldStart("datasets");
		for(final BigDecimal value : result.getValues()) {
			generator.writeNumber(value);
		}
		generator.writeEndArray();
	}
	
	/**
	 * Responsavel por serializar as competencias avaliadas pelo aluno.
	 */
	private void generateSkills(final StudentResultWrapper result, final JsonGenerator generator) throws IOException {
		generator.writeArrayFieldStart("skills");
		for(final Skill skill : result.getSkillsDeatils()) {
			generator.writeStartObject();
			generator.writeStringField("name", skill.getName());
			generator.writeStringField("description", skill.getDescription());
			generator.writeEndObject();
		}
		generator.writeEndArray();
	}
}
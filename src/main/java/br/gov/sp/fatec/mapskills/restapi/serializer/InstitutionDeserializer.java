/* @(#)InstitutionDeserializer.java 1.0 08/01/2017
 *
 * Copyright (c) 2016, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */

package br.gov.sp.fatec.mapskills.restapi.serializer;

import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;

import br.gov.sp.fatec.mapskills.domain.institution.Institution;
import br.gov.sp.fatec.mapskills.domain.institution.InstitutionLevel;
import br.gov.sp.fatec.mapskills.domain.institution.Mentor;
import br.gov.sp.fatec.mapskills.restapi.wrapper.InstitutionWrapper;

/**
 * A classe {@link InstitutionDeserializer} e responsavel por deserializar
 * um instituticao para que seja cadastrada ou atualizada.
 *
 * @author Marcelo
 * @version 1.0 08/01/2017
 */
@Component
public class InstitutionDeserializer extends AbstractJsonDeserializer<InstitutionWrapper> {
	
	@Override
	protected InstitutionWrapper deserialize(final JsonNode node) {
		final List<Mentor> mentors = new LinkedList<>();
        
        if(has(node, SerializationKey.MENTOR)) {
        	mentors.add(this.mentorDeserialize(get(node, SerializationKey.MENTOR)));
        } else {
        	mentors.addAll(this.mentorListDeserialize(get(node, SerializationKey.MENTORS)));
        }
        
		final Institution institution = new Institution(
				getFieldTextValue(node, SerializationKey.CODE),
				getFieldLongValue(node, SerializationKey.CNPJ),
				getFieldTextValue(node, SerializationKey.COMPANY),
				InstitutionLevel.valueOf(getFieldTextValue(node, SerializationKey.LEVEL)),
				getFieldTextValue(node, SerializationKey.CITY),
				mentors);
		
		return new InstitutionWrapper(institution);
	}

	private Mentor mentorDeserialize(final JsonNode node) {
		return new Mentor(getFieldTextValue(node, SerializationKey.NAME),
				getFieldTextValue(node, SerializationKey.USERNAME),
				getFieldPassValue(node));
	}
	
	private List<Mentor> mentorListDeserialize(final JsonNode node) {
		final List<Mentor> mentors = new LinkedList<>();
		node.forEach(currentNode -> mentors.add(mentorDeserialize(currentNode)));
		return mentors;
	}
}
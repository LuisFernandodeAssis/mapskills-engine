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
import br.gov.sp.fatec.mapskills.domain.user.mentor.Mentor;
import br.gov.sp.fatec.mapskills.restapi.wrapper.InstitutionDetailsWrapper;
import lombok.AllArgsConstructor;
/**
 * 
 * A classe {@link InstitutionDeserializer} e responsavel
 * por deserializar um instituticao para que seja
 * cadastrada ou atualizada.
 *
 * @author Marcelo
 * @version 1.0 08/01/2017
 */
@Component
@AllArgsConstructor
public class InstitutionDeserializer extends DefaultJsonDeserializer<InstitutionDetailsWrapper> {
	
	private static final String MENTOR = "mentor";
	
	@Override
	protected InstitutionDetailsWrapper deserialize(final JsonNode node) {
		final List<Mentor> mentors = new LinkedList<>();
        
        if(node.has(MENTOR)) {
        	mentors.add(this.mentorDeserialize(node.get(MENTOR)));
        } else {
        	mentors.addAll(this.mentorListDeserialize(node.get("mentors")));
        }
        
		final Institution institution = new Institution(
				jsonUtil.getFieldTextValue(node, "code"),
				jsonUtil.getFieldTextValue(node, "cnpj"),
				jsonUtil.getFieldTextValue(node, "company"),
				InstitutionLevel.valueOf(jsonUtil.getFieldTextValue(node, "level")),
				jsonUtil.getFieldTextValue(node, "city"),
				mentors, null);
		
		institution.setId(jsonUtil.getFieldLongValue(node, "id"));
		
		return new InstitutionDetailsWrapper(institution);
	}

	private Mentor mentorDeserialize(final JsonNode node) {
		final Mentor mentor = new Mentor(jsonUtil.getFieldTextValue(node, "name"),
				jsonUtil.getFieldTextValue(node, "username"),
				jsonUtil.getFieldPassValue(node),
				null);
		
        return mentor;
	}
	
	private List<Mentor> mentorListDeserialize(final JsonNode node) {
		final int sizeArray = node.size();
		final List<Mentor> mentors = new LinkedList<>();
		for(int i = 0; i < sizeArray; i++ ) {
			final JsonNode nodeCurrent = node.get(i);
			mentors.add(mentorDeserialize(nodeCurrent));
		}
		return mentors;
	}	

}

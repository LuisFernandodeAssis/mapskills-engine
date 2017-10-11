/*
 * @(#)InstitutionClientWrapper.java 1.0 17/04/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.Fatec-Jessen Vidal 
 * proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.restapi.wrapper;

import java.util.Collection;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import br.gov.sp.fatec.mapskills.domain.institution.Institution;
import br.gov.sp.fatec.mapskills.domain.institution.Mentor;
import br.gov.sp.fatec.mapskills.restapi.serializer.InstitutionSerializerTest;
import lombok.Getter;
/**
 * 
 * A classe {@link InstitutionWrapperTest} ...
 *
 * @author Marcelo
 * @version 1.0 17/04/2017
 */
@Getter
@JsonSerialize(using = InstitutionSerializerTest.class)
public class InstitutionWrapperTest {
	
	private Institution institution;
	private final Mentor mentor;
	
	public InstitutionWrapperTest(final Institution institution) {
		this.institution = institution;
		this.mentor = getMentorFromCollection(institution.getMentors());
	}
	
	private Mentor getMentorFromCollection(final Collection<Mentor> mentors) {
		for(final Mentor mentor : mentors) {
			return mentor;
		}
		return null;
	}
	
}

/*
 * @(#)InstitutionListWrapper.java 1.0 29/12/2016
 *
 * Copyright (c) 2016, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.restapi.wrapper;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import br.gov.sp.fatec.mapskills.domain.institution.Institution;
import br.gov.sp.fatec.mapskills.restapi.serializer.InstitutionListSerializer;
/**
 * 
 * A classe {@link InstitutionListWrapper} encapsula uma colecao
 * de instituticoes para que seja serializada.
 *
 * @author Marcelo
 * @version 1.0 29/12/2016
 */
@JsonSerialize(using = InstitutionListSerializer.class)
public class InstitutionListWrapper {
	
	private final List<Institution> institutions = new LinkedList<>();
	
	public InstitutionListWrapper(final List<Institution> institutions) {
		this.institutions.addAll(institutions);
	}
	
	public List<Institution> getInstitutions() {
		return Collections.unmodifiableList(institutions);
	}
}
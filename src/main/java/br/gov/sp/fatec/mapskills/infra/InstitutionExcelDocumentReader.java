/* @(#)InstitutionExcelDocumentReader.java 1.0 03/11/2016
 *
 * Copyright (c) 2016, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */

package br.gov.sp.fatec.mapskills.infra;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import br.gov.sp.fatec.mapskills.domain.institution.Institution;
import br.gov.sp.fatec.mapskills.domain.institution.InstitutionLevel;
import br.gov.sp.fatec.mapskills.domain.institution.Mentor;

/**
 * A classe {@link InstitutionExcelDocumentReader} eh responsavel
 * por manipular arquivos excel para objetos {@link Institution}.
 *
 * @author Marcelo
 * @version 1.0 03/11/2016
 */
@Component
public class InstitutionExcelDocumentReader extends ExcelDocumentReader<Institution> {
	
	private static final int ATTRIBUTES_SIZE = 7;
	
	@Autowired
	public InstitutionExcelDocumentReader(final PasswordEncoder encoder) {
		super(encoder);
	}
	
	@Override
	protected Institution buildEntity(final List<String> attributes) {		
		final Mentor mentor = buildMentor(attributes);
		return new Institution(attributes.get(0),
				getCnpj(attributes.get(1)),
				attributes.get(2),
				InstitutionLevel.getLevel(attributes.get(3)),
				attributes.get(4),
				Arrays.asList(mentor));
	}

	@Override
	protected boolean isValidAttributes(final List<String> attributes) {
		return attributes.size() == ATTRIBUTES_SIZE;
	}
	
	private Mentor buildMentor(final List<String> attributes) {
		return new Mentor(attributes.get(5), attributes.get(6), encodePassword("mentor@mudar"));
	}
	
	private Long getCnpj(final String cnpj) {
		return Long.parseLong(cnpj.replaceAll("[^0-9]", ""));
	}
}
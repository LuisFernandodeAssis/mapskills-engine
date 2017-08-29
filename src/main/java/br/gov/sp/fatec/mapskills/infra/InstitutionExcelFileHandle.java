/* @(#)InstitutionExcelFileHandle.java 1.0 03/11/2016
 *
 * Copyright (c) 2016, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.infra;

import java.io.InputStream;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;

import br.gov.sp.fatec.mapskills.domain.MapSkillsException;
import br.gov.sp.fatec.mapskills.domain.institution.Institution;
import br.gov.sp.fatec.mapskills.domain.institution.InstitutionLevel;
import br.gov.sp.fatec.mapskills.domain.institution.InstitutionRepository;
import br.gov.sp.fatec.mapskills.domain.user.mentor.Mentor;
import lombok.AllArgsConstructor;

/**
 * 
 * A classe {@link InstitutionExcelFileHandle} eh responsavel
 * por manipular arquivos excel para objetos {@link Institution}.
 *
 * @author Marcelo
 * @version 1.0 03/11/2016
 */
@Component
@AllArgsConstructor
public class InstitutionExcelFileHandle extends ExcelFileHandle<Institution> {
	
	private static final int DATA_NUMBER = 7;
	
	private final InstitutionRepository repository;
	
	@Override
	public List<Institution> toObjectList(final InputStream inputStream) throws MapSkillsException {
		return Collections.unmodifiableList(super.objectListFactory(inputStream));
	}

	@Override
	protected Institution buildObject(final List<String> attArgs) throws MapSkillsException {		
		
		final Institution institutionExcel = new Institution(
				attArgs.get(0),
				attArgs.get(1),
				attArgs.get(2),
				InstitutionLevel.build(attArgs.get(3).toUpperCase()),
				attArgs.get(4),
				null, null);
		
		final Institution institution = repository.findByCnpj(attArgs.get(1));
		if(institution != null) {
			institutionExcel.setId(institution.getId());
			institutionExcel.setGameThemeId(institution.getGameThemeId());
		}
		institutionExcel.addMentor(this.buildMentor(attArgs));

		return institutionExcel;
	}

	@Override
	protected boolean verifyListForObject(final List<String> argsToObj) {
		return argsToObj.size() == DATA_NUMBER;
	}
	
	private Mentor buildMentor(final List<String> attArgs) {
		final Mentor mentorExists = repository.findMentorByUsername(attArgs.get(6));
		final Mentor mentorExcel = new Mentor(attArgs.get(5), attArgs.get(6), DEFAULT_ENCRYPTED_PASS, repository.findByCode(attArgs.get(0)));
		if(mentorExists != null) {
			mentorExists.update(mentorExcel);
			return mentorExists;
		}
		return mentorExcel;
	}

}

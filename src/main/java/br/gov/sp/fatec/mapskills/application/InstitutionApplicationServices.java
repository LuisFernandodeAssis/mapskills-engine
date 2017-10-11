/*
 * @(#)InstitutionApplicationServices.java 1.0 1 02/09/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */

package br.gov.sp.fatec.mapskills.application;

import java.io.InputStream;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

import br.gov.sp.fatec.mapskills.domain.institution.Institution;
import br.gov.sp.fatec.mapskills.domain.institution.InstitutionDomainServices;
import lombok.AllArgsConstructor;

/**
 * A classe {@link InstitutionApplicationServices} contem as funcionalidades
 * referentes as instituicoes da aplicacao.
 *
 * @author Marcelo
 * @version 1.0 02/09/2017
 */
@ApplicationServices
@AllArgsConstructor
public class InstitutionApplicationServices {
	
	private final InstitutionDomainServices domainService;
	
	@Transactional
	@PreAuthorize("isFullyAuthenticated()")
	public void updateGameThemeInstitution(final String institutionCode, final Long gameThemeId) {
		domainService.updateGameTheme(institutionCode, gameThemeId);
	}
	
	@Transactional
	@PreAuthorize("isFullyAuthenticated()")
	public List<Institution> saveInstituionFromExcel(final InputStream inputStream) {
		return domainService.saveInstituionFromExcel(inputStream);
	}
	
	@Transactional
	@PreAuthorize("isFullyAuthenticated()")
	public Institution saveInstitution(final Institution newInstitution) {
		return domainService.saveInstitution(newInstitution);
	}
	
	@Transactional
	@PreAuthorize("isFullyAuthenticated()")
	public Institution updateInstitution(final Long id, final Institution institution) {
		return domainService.updateInstitution(id, institution);
	}
	
	@PreAuthorize("isFullyAuthenticated()")
	public List<Institution> getAllInstitutions() {
		return domainService.getAllInstitutions();
	}
	
	@PreAuthorize("isFullyAuthenticated()")
	public Institution getInstitutionById(final Long id) {
		return domainService.getInstitutionById(id);
	}
	
}
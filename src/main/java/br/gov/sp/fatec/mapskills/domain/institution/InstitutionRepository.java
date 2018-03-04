/*
 * @(#)InstitutionRepository.java 1.0 01/11/2016
 *
 * Copyright (c) 2016, Fatec Jessen Vidal. All rights reserved.
 * Fatec Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */

package br.gov.sp.fatec.mapskills.domain.institution;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

/**
 * A classe {@link InstitutionRepository} e responsavel por realizar as
 * transacionalidades referentes as instituticoes.
 *
 * @author Marcelo
 * @version 1.0 01/11/2016
 */
public interface InstitutionRepository extends CrudRepository<Institution, Long> {
	
	Institution findByCode(final String code);
	
	@Override
	List<Institution> findAll();
}
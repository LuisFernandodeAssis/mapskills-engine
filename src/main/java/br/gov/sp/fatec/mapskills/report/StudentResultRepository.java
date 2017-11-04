/*
 * @(#)StudentResultRepository.java 1.0 18/03/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.report;

import org.springframework.data.repository.CrudRepository;

import br.gov.sp.fatec.mapskills.report.entity.StudentResult;
/**
 * 
 * A classe {@link StudentResultRepository} trata das consultas a base de dados
 * para geracao dos relatorios e graficos de alunos.
 *
 * @author Marcelo
 * @version 1.0 18/03/2017
 */
public interface StudentResultRepository extends CrudRepository<StudentResult, Long> {
}
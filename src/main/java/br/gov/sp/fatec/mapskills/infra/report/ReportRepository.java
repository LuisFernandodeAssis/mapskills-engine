/*
 * @(#)ReportRepository.java 1.0 18/03/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.infra.report;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.Repository;

import br.gov.sp.fatec.mapskills.dashboard.CsvReport;
/**
 * 
 * A classe {@link ReportRepository} trata das consultas a base de dados
 * para geracao dos relatorios e graficos.
 *
 * @author Marcelo
 * @version 1.0 18/03/2017
 */
public interface ReportRepository extends Repository<CsvReport, Long>, JpaSpecificationExecutor<CsvReport> {
}
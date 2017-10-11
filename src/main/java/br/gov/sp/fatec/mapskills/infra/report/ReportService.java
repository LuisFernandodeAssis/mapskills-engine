/*
 * @(#)ReportService.java 1.0 05/03/2017
 *
 * Copyright (c) 2017, Fatec Jessen Vidal. All rights reserved.
 * Fatec Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.infra.report;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import br.gov.sp.fatec.mapskills.dashboard.CsvReport;
import br.gov.sp.fatec.mapskills.dashboard.ReportFilter;
import br.gov.sp.fatec.mapskills.dashboard.StudentResultIndicator;
import br.gov.sp.fatec.mapskills.domain.skill.Skill;
import br.gov.sp.fatec.mapskills.domain.skill.SkillRepository;
import br.gov.sp.fatec.mapskills.restapi.wrapper.ReportViewWrapper;
import lombok.AllArgsConstructor;
/**
 * 
 * A classe {@link ReportService} contem as regras de negocio
 * para geracao de relatorios da aplicacao sendo as competencias
 * geradas dinamicamente de acordo com a quantidade cadastrada.
 *
 * @author Marcelo
 * @version 1.0 05/03/2017
 */
@Service
@AllArgsConstructor
public class ReportService {
	
	private static final String SEMICOLON = ";";
	
	private final SkillRepository skillRepository;
	private final ReportRepository reportRepository;
	
	/**
	 * 
	 */
	public byte[] getCsvReport(final ReportFilter filter) {
		final StringBuilder stringBuilder = new StringBuilder();
		generateHeader(stringBuilder);

		final List<CsvReport> csvReport = getReportDatas(new ReportSpecification(filter).get());
		
		csvReport.stream().forEachOrdered(report -> {
			final StringBuilder csvLine = generateDataInfo(report);
			
			for(final StudentResultIndicator studentIndicator : report.getStudentsIndicator()) {
				generateResultGame(studentIndicator, csvLine);
			}
			stringBuilder.append(csvLine.toString());
			stringBuilder.append("\n");
		});
		
		return stringBuilder.toString().getBytes();
	}
	
	private List<CsvReport> getReportDatas(final Specification<CsvReport> specification) {
		return reportRepository.findAll(specification);
	}
	
	public ReportViewWrapper getReport(final ReportFilter filter) {
		final List<CsvReport> reportData = getReportDatas(new ReportSpecification(filter).get());
		final List<Skill> skills = skillRepository.findAllOrderByName();
		return new ReportViewWrapper(reportData, skills);
	}
	
	/**
	 * 
	 * Metodo responsavel por escrever todo o cabecalho do relatorio.
	 * gerando as competencias dinamicamente.
	 */
	private void generateHeader(final StringBuilder stringBuilder) {
		final StringBuilder defaultHeader = new StringBuilder("RA;ALUNO;CURSO;CODIGO INSTITUICAO;INSTITUICAO;ANO/SEMESTRE;");
		final List<Skill> skills = skillRepository.findAllOrderByName();
		for(final Skill skill : skills) {
			defaultHeader.append(String.format("%s;", skill.getName().toUpperCase()));
		}
		stringBuilder.append(defaultHeader).append("\n");
	}
	
	/**
	 * 
	 * Metodo responsavel por escrever todas informacoes
	 * basicas do aluno que aparecera no relatorio.
	 */
	private StringBuilder generateDataInfo(final CsvReport data) {
		final StringBuilder dataInfo = new StringBuilder();
		dataInfo.append(data.getStudentRA()).append(SEMICOLON)
			.append(data.getStudentName()).append(SEMICOLON)
			.append(data.getCourseName()).append(SEMICOLON)
			.append(data.getInstitutionCode()).append(SEMICOLON)
			.append(data.getInstitutionCompany()).append(SEMICOLON)
			.append(data.getYearSemester()).append(SEMICOLON);
		return dataInfo;
	}
	
	/**
	 * 
	 * Metodo responsavel por escrever todos os resultados das competencias
	 * de um aluno gerada pela aplicacao.
	 */
	private void generateResultGame(final StudentResultIndicator studentIndicator, final StringBuilder dataInfo) {
		dataInfo.append(ObjectUtils.isEmpty(studentIndicator.getTotal()) ? "N/A" : studentIndicator.getTotal()).append(SEMICOLON);
	}

}
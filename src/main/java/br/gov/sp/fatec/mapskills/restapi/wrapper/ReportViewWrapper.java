/*
 * @(#)ReportViewWrapper.java 1.0 19/03/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.restapi.wrapper;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import br.gov.sp.fatec.mapskills.dashboard.CsvReport;
import br.gov.sp.fatec.mapskills.domain.skill.Skill;
import br.gov.sp.fatec.mapskills.restapi.serializer.ReportViewSerializer;
/**
 * 
 * A classe {@link ReportViewWrapper} encapsula todas informacoes
 * de relatorios dos alunos para serializacao.
 *
 * @author Marcelo
 * @version 1.0 19/03/2017
 */
@JsonSerialize(using = ReportViewSerializer.class)
public class ReportViewWrapper {
	
	private final List<CsvReport> report = new LinkedList<>();
	private final List<Skill> skills = new LinkedList<>();
	
	public ReportViewWrapper(final List<CsvReport> report, final List<Skill> skills) {
		this.report.addAll(report);
		this.skills.addAll(skills);
	}
	
	public List<CsvReport> getData() {
		return Collections.unmodifiableList(report);
	}
	
	public List<Skill> getSkills() {
		return Collections.unmodifiableList(skills);
	}
}
/*
 * @(#)ReportViewWrapper.java 1.0 19/03/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.restapi.wrapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import br.gov.sp.fatec.mapskills.domain.skill.Skill;
import br.gov.sp.fatec.mapskills.infra.report.ReportDefaultData;
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
	
	private final List<Skill> skills = new ArrayList<>();
	private final List<ReportDefaultData> datas = new ArrayList<>();
	
	public ReportViewWrapper(final List<Skill> skills, final List<ReportDefaultData> datas) {
		this.skills.clear();
		this.skills.addAll(skills);
		this.datas.clear();
		this.datas.addAll(datas);
	}
	
	
	public List<Skill> getSkills() {
		return Collections.unmodifiableList(this.skills);
	}
	
	public List<ReportDefaultData> getDatas() {
		return Collections.unmodifiableList(this.datas);
	}

}

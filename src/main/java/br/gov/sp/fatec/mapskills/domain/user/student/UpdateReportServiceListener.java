/*
 * @(#)UpdateReportServiceListener.java 1.0 1 28/10/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */

package br.gov.sp.fatec.mapskills.domain.user.student;

import org.springframework.web.client.RestTemplate;

import br.gov.sp.fatec.mapskills.domain.event.DomainEvent;
import br.gov.sp.fatec.mapskills.domain.event.DomainEventListener;
import br.gov.sp.fatec.mapskills.domain.event.EventListener;
import br.gov.sp.fatec.mapskills.restapi.wrapper.StudentResultWrapper;
import lombok.AllArgsConstructor;

/**
 * A classe {@link UpdateReportServiceListener} e responsavel
 * por realizar uma postagem <b>REST</b> para atualizar o servico
 * de relatorios de alunos.
 *
 * @author Marcelo
 * @version 1.0 28/10/2017
 */
@EventListener
@AllArgsConstructor
public class UpdateReportServiceListener implements DomainEventListener {
	
	private static final String URL = "http://localhost:8082/report";
	private final RestTemplate rest;

	@Override
	public void notify(final DomainEvent sourceEvent) {
		final StudentFinishedGameEvent event = (StudentFinishedGameEvent) sourceEvent;
		rest.postForEntity(URL, new StudentResultWrapper(event.getSource()), String.class);
	}
}
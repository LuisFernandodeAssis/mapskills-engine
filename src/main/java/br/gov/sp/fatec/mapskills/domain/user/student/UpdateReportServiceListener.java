/*
 * @(#)UpdateReportServiceListener.java 1.0 1 28/10/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */

package br.gov.sp.fatec.mapskills.domain.user.student;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import br.gov.sp.fatec.mapskills.domain.event.DomainEvent;
import br.gov.sp.fatec.mapskills.domain.event.DomainEventListener;
import br.gov.sp.fatec.mapskills.domain.event.EventListener;
import br.gov.sp.fatec.mapskills.infra.ThreadPool;
import br.gov.sp.fatec.mapskills.studentresult.StudentResultRepository;

/**
 * A classe {@link UpdateReportServiceListener} e responsavel
 * por realizar uma postagem <b>REST</b> para atualizar o servico
 * de relatorios de alunos.
 *
 * @author Marcelo
 * @version 1.0 28/10/2017
 */
@EventListener
public class UpdateReportServiceListener implements DomainEventListener {
	
	private final String reportServerUrl;
	private final StudentResultRepository studentResultRepository;
	private final RestTemplate rest;
	private final ThreadPool threadPool;
	
	public UpdateReportServiceListener(@Value("${rest.report.url}") final String urlServer,
			final RestTemplate rest, final StudentResultRepository studentResultRepository,
			final ThreadPool threadPool) {
		this.reportServerUrl = urlServer;
		this.studentResultRepository = studentResultRepository;
		this.rest = rest;
		this.threadPool = threadPool;
	}

	@Override
	public void notify(final DomainEvent sourceEvent) {
		final StudentFinishedGameEvent event = (StudentFinishedGameEvent) sourceEvent;
		threadPool.execute(new StudentReportDataRunnable(threadPool, reportServerUrl,
				rest, studentResultRepository, event.getSource()));
	}
}
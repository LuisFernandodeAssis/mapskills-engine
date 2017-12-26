/*
 * @(#)DeleteSceneImageFileListener.java 1.0 1 17/12/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */

package br.gov.sp.fatec.mapskills.domain.theme;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import br.gov.sp.fatec.mapskills.domain.event.DomainEvent;
import br.gov.sp.fatec.mapskills.domain.event.DomainEventListener;
import br.gov.sp.fatec.mapskills.domain.event.EventListener;
import br.gov.sp.fatec.mapskills.infra.ThreadPool;

/**
 * A classe {@link DeleteSceneImageFileListener} eh responsavel
 * por fazer uma requisicao de <i>delete</i> ao servico de
 * gerencia de arquivo, para que a imagem seja removida.
 *
 * @author Marcelo
 * @version 1.0 17/12/2017
 */
@EventListener
public class DeleteSceneImageFileListener implements DomainEventListener {
	
	private final ThreadPool threadPool;
	private final RestTemplate rest;
	private final String urlServer;
	
	public DeleteSceneImageFileListener(final ThreadPool threadPool,
			final RestTemplate rest, @Value("${rest.filemanager.update.url}") final String urlServer) {
		this.threadPool = threadPool;
		this.rest = rest;
		this.urlServer = urlServer;
	}

	@Override
	public void notify(final DomainEvent event) {
		final SceneWasDeletedEvent evt = (SceneWasDeletedEvent) event;
		threadPool.execute(() -> {
			final String url = urlServer.replace("{filename}", evt.getSource());
			rest.delete(url);
		});
	}
}
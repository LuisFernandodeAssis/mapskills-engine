/*
 * @(#)UpdateSceneImageFileListener.java 1.0 1 17/12/2017
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
import br.gov.sp.fatec.mapskills.utils.ThreadPool;

/**
 * A classe {@link UpdateSceneImageFileListener} eh responsavel
 * por fazer uma requisicao <i>post</i> para o serviço de gerencia
 * de arquivo para atualizar o arquivo de imagem de uma cena.
 *
 * @author Marcelo
 * @version 1.0 17/12/2017
 */
@EventListener
public class UpdateSceneImageFileListener implements DomainEventListener {
	
	private final ThreadPool threadPool;
	private final RestTemplate rest;
	private final String serverUrl;
	
	public UpdateSceneImageFileListener(final ThreadPool threadPool,
			final RestTemplate rest, @Value("${rest.filemanager.update.url}") final String serverUrl) {
		this.threadPool = threadPool;
		this.rest = rest;
		this.serverUrl = serverUrl;
	}

	@Override
	public void notify(final DomainEvent event) {
		final SceneWasUpdatedEvent evt = (SceneWasUpdatedEvent) event;		
		if (evt.containsBase64()) {
			threadPool.execute(() -> {
				final String url = serverUrl.replace("{filename}", evt.getOldFilename());
				rest.put(url, evt.getFileWrapper());
			});
		}
	}
}
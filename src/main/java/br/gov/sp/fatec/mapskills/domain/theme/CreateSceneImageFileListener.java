/*
 * @(#)CreateSceneImageFileListener.java 1.0 1 17/12/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */

package br.gov.sp.fatec.mapskills.domain.theme;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import br.gov.sp.fatec.mapskills.domain.event.DomainEvent;
import br.gov.sp.fatec.mapskills.domain.event.DomainEventListener;
import br.gov.sp.fatec.mapskills.domain.event.EventListener;
import br.gov.sp.fatec.mapskills.restapi.wrapper.FileContextWrapper;
import br.gov.sp.fatec.mapskills.utils.ThreadPool;

/**
 * A classe {@link CreateSceneImageFileListener} eh responsavel
 * por fazer uma requisicao <i>post</i> para o servico de gerencia
 * de arquivo, para que seja criada uma nova imagem no servidor.
 *
 * @author Marcelo
 * @version 1.0 17/12/2017
 */
@EventListener
public class CreateSceneImageFileListener implements DomainEventListener {
	
	private final ThreadPool threadPool;
	private final RestTemplate rest;
	private final String serverUrl;
	
	public CreateSceneImageFileListener(final ThreadPool threadPool, final RestTemplate rest,
			@Value("${rest.filemanager.post.url}") final String serverUrl) {
		this.threadPool = threadPool;
		this.rest = rest;
		this.serverUrl = serverUrl;
	}

	@Override
	public void notify(final DomainEvent event) {
		final SceneWasCreatedEvent evt = (SceneWasCreatedEvent) event;
		final FileContextWrapper wrapper = new FileContextWrapper(evt.getBase64(), evt.getFilename());
		threadPool.execute(() -> rest.postForObject(serverUrl, wrapper, ResponseEntity.class));
	}
}
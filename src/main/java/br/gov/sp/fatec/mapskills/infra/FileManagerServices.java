/*
 * @(#)FileManagerServices.java 1.0 1 03/09/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */

package br.gov.sp.fatec.mapskills.infra;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import br.gov.sp.fatec.mapskills.restapi.wrapper.SceneWrapper;

/**
 * A classe {@link FileManagerServices} e responsavel
 * por realizar <i>POST</i> do contexto de uma cena com imagem
 * a ser salva no servico de gerenciamento de arquivos.
 *
 * @author Marcelo
 * @version 1.0 03/09/2017
 */
@Component
public class FileManagerServices {
	
	private final String fileManagerServer;
	private final RestTemplate restTemplate;
	
	public FileManagerServices(@Value("${rest.filemanager.post.url}") final String urlServer,
			final RestTemplate restTemplate) {
		this.fileManagerServer = urlServer;
		this.restTemplate = restTemplate;
	}	
	
	/**
	 * Metodo responsavel por realizar um <b>request</b>
	 * do contexto da cena a ser salva no servidor de arquivo.
	 * 
	 * @param sceneWrapper Contexto da cena a ser enviada pela rede.
	 */
	public void saveImage(final SceneWrapper sceneWrapper) {
		restTemplate.postForObject(fileManagerServer, sceneWrapper, SceneWrapper.class);
	}
}
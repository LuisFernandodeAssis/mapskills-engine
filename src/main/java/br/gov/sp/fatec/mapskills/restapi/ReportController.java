/*
 * @(#)ReportController.java 1.0 11/03/2017
 *
 * Copyright (c) 2016, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.restapi;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.gov.sp.fatec.mapskills.dashboard.ReportFilter;
import br.gov.sp.fatec.mapskills.domain.MapSkillsException;
import br.gov.sp.fatec.mapskills.infra.ImageNotFoundException;
import br.gov.sp.fatec.mapskills.infra.report.ReportService;
import br.gov.sp.fatec.mapskills.restapi.wrapper.ReportViewWrapper;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class ReportController {
	
	private static final Logger LOGGER = Logger.getLogger(ReportController.class.getName());

	private final ReportService reportService;
	private final ServletContext servletContext;
	
	/**
	 * End-point para realizar download do relatorio,
	 * chamado nas interfaces de administrador e mentor.
	 * 
	 * @param filter
	 * 		Filtro para busca do relatorio.
	 * @param response
	 * 		Sera escrito o tipo do conteudo.
	 * @return
	 * 		Atraves de stream o relatorio.
	 */
	@RequestMapping(value = "/report/download", method = RequestMethod.POST)
	public HttpEntity<byte[]> getReportDownload(@RequestBody final ReportFilter filter, final HttpServletResponse response) {

		final byte[] report = reportService.getCsvReport(filter);
	    response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
	    response.setCharacterEncoding("UTF-8");
		return new HttpEntity<>(report, getHttpHeaders());
	}
	
	/**
	 * End-point que recupera um relatorio dos alunos.
	 * 
	 * @param filter
	 * 		Filtro para pesquisa requerida.
	 * @return
	 * 		Relatorio em json.
	 */
	@RequestMapping(value = "/report/view", method = RequestMethod.POST)
	public ReportViewWrapper getReportView(@RequestBody final ReportFilter filter) {
		
		return reportService.getReport(filter);

	}
	
	/**
	 * End-point que recupera uma imagem de cena salva no servidor da aplicacao.
	 * 
	 * @param imageName
	 * 			Nome da imagem a ser recuperada com a respectiva extensao. Ex.: scene00.jpg
	 * @param response
	 * 			Atraves de stream e retornada a imagem.
	 * @throws MapSkillsException
	 * 			Excecao lancada caso nao encontre a imagem.
	 */
	@RequestMapping(value = "/image/{imageName:.+}", method = RequestMethod.GET)
	@ResponseBody
	public void getImageAsResource(@PathVariable("imageName") final String imageName,
			final HttpServletResponse response) throws MapSkillsException {
		
	    try {
	    	final InputStream inputStream = servletContext.getResourceAsStream("/images/"+imageName);
	    	response.setContentType(MediaType.IMAGE_JPEG_VALUE);
			IOUtils.copy(inputStream, response.getOutputStream());
		} catch (final IOException | NullPointerException exception) {
			LOGGER.log(Level.SEVERE, imageName + " não encontrada", exception);
			throw new ImageNotFoundException(imageName + " não encontrada", exception);
		}
	}
	
	private HttpHeaders getHttpHeaders() {
		final HttpHeaders httpHeaders = new HttpHeaders();
	    httpHeaders.add("Content-Disposition", "attachment; filename=report.csv" );
	    return httpHeaders;
	}

}
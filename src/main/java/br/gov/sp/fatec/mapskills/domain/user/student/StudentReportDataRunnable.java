/*
 * @(#)StudentReportDataRunnable.java 1.0 1 10/12/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */

package br.gov.sp.fatec.mapskills.domain.user.student;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import br.gov.sp.fatec.mapskills.restapi.RestException;
import br.gov.sp.fatec.mapskills.restapi.wrapper.StudentResultWrapper;
import br.gov.sp.fatec.mapskills.studentresult.StudentResult;
import br.gov.sp.fatec.mapskills.studentresult.StudentResultRepository;
import br.gov.sp.fatec.mapskills.utils.ThreadPool;
import lombok.AllArgsConstructor;

/**
 * A classe {@link StudentReportDataRunnable}
 *
 * @author Marcelo
 * @version 1.0 10/12/2017
 */
@AllArgsConstructor
public class StudentReportDataRunnable implements Runnable {
	
	private final ThreadPool threadPool;
	
	/** URL do servidor de relatórios */
	private final String reportServerUrl;
	private final RestTemplate rest;
	private final StudentResultRepository studentResultRepository;
	private final Long studentId;	

	@Override
	public void run() {
		try {
			threadPool.awaitTermination(2);
			final StudentResult result = studentResultRepository.findOne(studentId);
			rest.postForObject(reportServerUrl, new StudentResultWrapper(result), ResponseEntity.class);
		} catch (final InterruptedException | RestClientException exception) {
			throw new RestException("Problemas ao enviar dados do resultado do aluno de ID = "
					+ studentId + " ao servidor de relatorios.", exception);
		}
	}
}
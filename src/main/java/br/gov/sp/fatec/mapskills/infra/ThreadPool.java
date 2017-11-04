/*
 * @(#)ThreadPool.java 1.0 1 07/10/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */

package br.gov.sp.fatec.mapskills.infra;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * A classe <code>ThreadPool</code> representa o pool de threads da aplicacao.
 *
 * @author Roberto Perillo
 * @version 1.0 26/02/2016
 */
public class ThreadPool {
	
	private final ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

	public void execute(final Runnable runnable) {
		executor.execute(runnable);
	}
}
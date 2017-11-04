/*
 * @(#)DomainEventListener.java 1.0 1 28/10/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */

package br.gov.sp.fatec.mapskills.domain.event;

/**
 * A classe {@link DomainEventListener} define o metodo de notificacao
 * de eventos de dominio. Pode haver uma ou mais implementacoes dessa interface
 * para tratar um determinado evento de dominio.
 *
 * @author Roberto Perillo
 * @author Marcelo
 * @version 1.0 28/10/2017
 */
@FunctionalInterface
public interface DomainEventListener {
	
	/**
     * Notifica a implementacao interessada no evento de dominio sendo
     * notificado.
     *
     * @param event
     *            O evento de dominio a ser tratado.
     */
    void notify(final DomainEvent event);
}
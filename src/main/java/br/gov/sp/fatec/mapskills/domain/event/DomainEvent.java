/*
 * @(#)DomainEvent.java 1.0 1 28/10/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */

package br.gov.sp.fatec.mapskills.domain.event;

import java.util.EventObject;

/**
 * A classe {@link DomainEvent} e a classe base para todos os eventos de
 * dominio na aplicacao.
 *
 * @author Roberto Perillo
 * @author Marcelo Inacio
 * @version 1.0 28/10/2017
 */
public abstract class DomainEvent extends EventObject {

	private static final long serialVersionUID = 1L;
	
	public DomainEvent(final Object source) {
		super(source);
	}
	
	/**
     * Retorna o tipo do evento de dominio
     */
    public abstract DomainEventType getType();
}
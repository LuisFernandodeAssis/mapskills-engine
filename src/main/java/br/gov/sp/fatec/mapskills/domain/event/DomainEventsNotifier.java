/*
 * @(#)DomainEventsNotifier.java 1.0 1 28/10/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */

package br.gov.sp.fatec.mapskills.domain.event;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

/**
 * A classe {@link DomainEventsNotifier} contem os tipo de eventos de
 * dominio e seus respectivos <i>ouvintes</i>, e e responsavel por notifica-los
 * quando um evento de dominio acontece.
 *
 * @author Roberto Perillo
 * @author Marcelo
 * @version 1.0 28/10/2017
 */
@Component
public class DomainEventsNotifier {
	
	/**
     * O mapa que contem os tipos de eventos de dominio e seus respectivos
     * ouvintes
     */
    private final Map<DomainEventType, List<DomainEventListener>> listeners;
    
    public DomainEventsNotifier(final Map<DomainEventType, List<DomainEventListener>> listeners) {
        super();
        this.listeners = new EnumMap<>(DomainEventType.class);
        this.listeners.putAll(listeners);
    }
    
    /**
     * Notifica os ouvintes de um tipo de evento de dominio.
     *
     * @param event
     *            O evento a ser tratado pelos ouvintes.
     */
    public void notifyListeners(final DomainEvent event) {
        final List<DomainEventListener> domainEventListeners = listeners.get(event.getType());
        if (domainEventListeners != null) {
            for (final DomainEventListener listener : domainEventListeners) {
                listener.notify(event);
            }
        }
    }
}
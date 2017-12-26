/*
 * @(#)SceneWasDeletedEvent.java 1.0 1 17/12/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */

package br.gov.sp.fatec.mapskills.domain.theme;

import static br.gov.sp.fatec.mapskills.domain.event.DomainEventType.SCENE_WAS_DELETED_EVENT;

import br.gov.sp.fatec.mapskills.domain.event.DomainEvent;
import br.gov.sp.fatec.mapskills.domain.event.DomainEventType;

/**
 * A classe {@link SceneWasDeletedEvent}
 *
 * @author Marcelo
 * @version 1.0 17/12/2017
 */
public class SceneWasDeletedEvent extends DomainEvent {

	private static final long serialVersionUID = 1L;

	public SceneWasDeletedEvent(final String source) {
		super(source);
	}

	@Override
	public DomainEventType getType() {
		return SCENE_WAS_DELETED_EVENT;
	}
	
	@Override
	public String getSource() {
		return (String) super.getSource();
	}
}
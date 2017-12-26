/*
 * @(#)SceneWasCreatedEvent.java 1.0 1 17/12/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */

package br.gov.sp.fatec.mapskills.domain.theme;

import static br.gov.sp.fatec.mapskills.domain.event.DomainEventType.SCENE_WAS_CREATED_EVENT;

import br.gov.sp.fatec.mapskills.domain.event.DomainEvent;
import br.gov.sp.fatec.mapskills.domain.event.DomainEventType;
import br.gov.sp.fatec.mapskills.restapi.wrapper.SceneWrapper;

/**
 * A classe {@link SceneWasCreatedEvent}
 *
 * @author Marcelo
 * @version 1.0 17/12/2017
 */
public class SceneWasCreatedEvent extends DomainEvent {

	private static final long serialVersionUID = 1L;

	public SceneWasCreatedEvent(final SceneWrapper source) {
		super(source);
	}

	@Override
	public DomainEventType getType() {
		return SCENE_WAS_CREATED_EVENT;
	}
	
	@Override
	public SceneWrapper getSource() {
		return (SceneWrapper) super.getSource();
	}
	
	public String getBase64() {
		return getSource().getBase64();
	}
	
	public String getFilename() {
		return getSource().getFileName();
	}
}
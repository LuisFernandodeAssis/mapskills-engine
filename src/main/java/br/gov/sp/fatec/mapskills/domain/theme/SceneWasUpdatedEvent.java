/*
 * @(#)SceneWasUpdatedEvent.java 1.0 1 17/12/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */

package br.gov.sp.fatec.mapskills.domain.theme;

import static br.gov.sp.fatec.mapskills.domain.event.DomainEventType.SCENE_WAS_UPDATED_EVENT;

import br.gov.sp.fatec.mapskills.domain.event.DomainEvent;
import br.gov.sp.fatec.mapskills.domain.event.DomainEventType;
import br.gov.sp.fatec.mapskills.restapi.wrapper.SceneWrapper;

/**
 * A classe {@link SceneWasUpdatedEvent}
 *
 * @author Marcelo
 * @version 1.0 17/12/2017
 */
public class SceneWasUpdatedEvent extends DomainEvent {

	private static final long serialVersionUID = 1L;

	public SceneWasUpdatedEvent(final SceneUpdateContext source) {
		super(source);
	}

	@Override
	public DomainEventType getType() {
		return SCENE_WAS_UPDATED_EVENT;
	}
	
	@Override
	public SceneUpdateContext getSource() {
		return (SceneUpdateContext) super.getSource();
	}

	public boolean containsBase64() {
		return getSource().containsBase64();
	}
	
	public String getOldFilename() {
		return getSource().getOldFilename();
	}

	public SceneWrapper getSceneWrapper() {
		return getSource().getSceneWrapper();
	}
}
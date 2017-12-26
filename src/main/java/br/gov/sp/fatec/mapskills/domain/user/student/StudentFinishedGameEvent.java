/*
 * @(#)StudentFinishedGameEvent.java 1.0 1 28/10/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */

package br.gov.sp.fatec.mapskills.domain.user.student;

import static br.gov.sp.fatec.mapskills.domain.event.DomainEventType.STUDENT_FINISHED_GAME_EVENT;

import br.gov.sp.fatec.mapskills.domain.event.DomainEvent;
import br.gov.sp.fatec.mapskills.domain.event.DomainEventType;

/**
 * A classe {@link StudentFinishedGameEvent} sinaliza
 * um evento para atualizar o servico de relatorio com
 * os indicadores do aluno que terminou de responder o jogo.
 *
 * @author Marcelo
 * @version 1.0 28/10/2017
 */
public class StudentFinishedGameEvent extends DomainEvent {

	private static final long serialVersionUID = 1L;

	public StudentFinishedGameEvent(final Long source) {
		super(source);
	}

	@Override
	public DomainEventType getType() {
		return STUDENT_FINISHED_GAME_EVENT;
	}
	
	@Override
	public Long getSource() {
		return (Long) super.getSource();
	}
}
/*
 * @(#)DomainEventsConfig.java 1.0 1 28/10/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */

package br.gov.sp.fatec.mapskills.config;

import static br.gov.sp.fatec.mapskills.domain.event.DomainEventType.STUDENT_FINISHED_GAME_EVENT;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.gov.sp.fatec.mapskills.domain.event.DomainEventListener;
import br.gov.sp.fatec.mapskills.domain.event.DomainEventType;

/**
 * A classe {@link DomainEventsConfig} define os beans
 * referentes a configuracao de eventos de dominio na aplicacao.
 *
 * @author Marcelo
 * @version 1.0 28/10/2017
 */
@Configuration
public class DomainEventsConfig {
	
	@Bean
	public Map<DomainEventType, List<DomainEventListener>> listeners(
			@Qualifier("updateReportServiceListener") final DomainEventListener updateReportServiceListener) {
		
		final Map<DomainEventType, List<DomainEventListener>> map = new EnumMap<>(DomainEventType.class);
		map.put(STUDENT_FINISHED_GAME_EVENT, Arrays.asList(updateReportServiceListener));
		return map;
	}
}
/*
 * @(#)SerializersConfig.java 1.0 10/11/2016
 *
 * Copyright (c) 2016, Fatec Jessen Vidal. All rights reserved.
 * Fatec Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.config;

import java.util.EnumMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.gov.sp.fatec.mapskills.domain.institution.Mentor;
import br.gov.sp.fatec.mapskills.domain.user.Administrator;
import br.gov.sp.fatec.mapskills.domain.user.ProfileType;
import br.gov.sp.fatec.mapskills.domain.user.User;
import br.gov.sp.fatec.mapskills.domain.user.student.Student;
import br.gov.sp.fatec.mapskills.restapi.serializer.UserSerilizerStrategy;
/**
 * 
 * A classe {@link SerializersConfig} possui uma configuracao de estrategia de
 * serializacao de perfil, onde cada perfil possui seu serializador.
 *
 * @author Marcelo
 * @version 1.0 10/11/2016
 */
@Configuration
public class SerializersConfig {
	/**
	 * Instancia no cluster de objetos do spring um mapa de perfil/serializador.
	 */
	@Bean
	public Map<ProfileType, UserSerilizerStrategy<? extends User>> mapSerializerStrategy(
			@Autowired @Qualifier("defaultUserSerializer") final UserSerilizerStrategy<Administrator> defaultSerializer,
			@Autowired @Qualifier("studentSerializer") final UserSerilizerStrategy<Student> studentSerializer,
			@Autowired @Qualifier("mentorSerializer") final UserSerilizerStrategy<Mentor> mentorSerializer) {
		
		final Map<ProfileType, UserSerilizerStrategy<? extends User>> map = new EnumMap<>(ProfileType.class);
		map.put(ProfileType.ADMINISTRATOR, defaultSerializer);
		map.put(ProfileType.MENTOR, mentorSerializer);
		map.put(ProfileType.STUDENT, studentSerializer);
		
		return map;
	}

}

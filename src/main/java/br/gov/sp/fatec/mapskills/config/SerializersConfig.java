/*
 * @(#)SerializersConfig.java 1.0 10/11/2016
 *
 * Copyright (c) 2016, Fatec Jessen Vidal. All rights reserved.
 * Fatec Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */

package br.gov.sp.fatec.mapskills.config;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.gov.sp.fatec.mapskills.domain.institution.Course;
import br.gov.sp.fatec.mapskills.domain.institution.Institution;
import br.gov.sp.fatec.mapskills.domain.institution.Mentor;
import br.gov.sp.fatec.mapskills.domain.skill.Skill;
import br.gov.sp.fatec.mapskills.domain.theme.GameTheme;
import br.gov.sp.fatec.mapskills.domain.theme.Scene;
import br.gov.sp.fatec.mapskills.domain.user.Administrator;
import br.gov.sp.fatec.mapskills.domain.user.ProfileType;
import br.gov.sp.fatec.mapskills.domain.user.student.Student;
import br.gov.sp.fatec.mapskills.restapi.serializer.AbstractSerializer;

/**
 * A classe {@link SerializersConfig} possui uma configuracao de estrategia de
 * serializacao de perfil, onde cada perfil possui seu serializador.
 *
 * @author Marcelo
 * @version 1.0 10/11/2016
 */
@Configuration
public class SerializersConfig {
	
	/**
	 * Define um serializador para cada perfil de usuario da aplicacao.
	 */
	@Bean("userSerializerMap")
	@SuppressWarnings("rawtypes")
	public Map<ProfileType, AbstractSerializer> userSerializerMap(
			@Qualifier("defaultUserSerializer") final AbstractSerializer<Administrator> defaultSerializer,
			@Qualifier("studentSerializer") final AbstractSerializer<Student> studentSerializer,
			@Qualifier("mentorSerializer") final AbstractSerializer<Mentor> mentorSerializer) {
		
		final Map<ProfileType, AbstractSerializer> map = new EnumMap<>(ProfileType.class);
		map.put(ProfileType.ADMINISTRATOR, defaultSerializer);
		map.put(ProfileType.MENTOR, mentorSerializer);
		map.put(ProfileType.STUDENT, studentSerializer);
		return map;
	}
	
	@Bean
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map<Class, AbstractSerializer> domainSerializerMap(
			@Qualifier("courseSerializer") final AbstractSerializer courseSerializer,
			@Qualifier("gameThemeSerializer") final AbstractSerializer gameThemeSerializer,
			@Qualifier("institutionSerializer") final AbstractSerializer institutionSerializer,
			@Qualifier("sceneSerializer") final AbstractSerializer sceneSerializer,
			@Qualifier("skillSerializer") final AbstractSerializer skillSerializer,
			@Qualifier("userSerializer") final AbstractSerializer userSerializer) {
		
		final Map map = new HashMap<>(8);
		map.put(Administrator.class, userSerializer);
		map.put(Course.class, courseSerializer);
		map.put(GameTheme.class, gameThemeSerializer);		
		map.put(Institution.class, institutionSerializer);
		map.put(Mentor.class, userSerializer);
		map.put(Scene.class, sceneSerializer);
		map.put(Skill.class, skillSerializer);
		map.put(Student.class, userSerializer);
		return map;		
	}
}
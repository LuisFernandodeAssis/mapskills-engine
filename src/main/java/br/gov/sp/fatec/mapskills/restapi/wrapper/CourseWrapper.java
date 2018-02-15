/*
 * @(#)CourseWrapper.java 1.0 15/01/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */

package br.gov.sp.fatec.mapskills.restapi.wrapper;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import br.gov.sp.fatec.mapskills.domain.institution.Course;
import br.gov.sp.fatec.mapskills.restapi.serializer.CourseDeserializer;

/**
 * A classe {@link CourseWrapper} encapsula um curso que sera
 * deserializado pela classe <code>CourseDeserializer</code>.
 * Para que seja cadastrado ou atualizado.
 *
 * @author Marcelo
 * @version 1.0 15/01/2017
 */
@JsonDeserialize(using = CourseDeserializer.class)
public class CourseWrapper {
	
	private final Course course;
	
	public CourseWrapper(final Course course) {
		this.course = course;
	}
	
	public Course getCourse() {
		return course;
	}
}
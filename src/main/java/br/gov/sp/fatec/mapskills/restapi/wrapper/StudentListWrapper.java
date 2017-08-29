/*
 * @(#)StudentListWrapper.java 1.0 13/01/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.restapi.wrapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import br.gov.sp.fatec.mapskills.domain.institution.Course;
import br.gov.sp.fatec.mapskills.domain.user.student.Student;
import br.gov.sp.fatec.mapskills.restapi.serializer.StudentListSerializer;

/**
 * 
 * A classe {@link StudentListWrapper} encapsula uma colecao de alunos e
 * um mapa <codigo do curso / curso> para que seja serializado o aluno
 * com informacao sobre seu curso.
 *
 * @author Marcelo
 * @version 1.0 13/01/2017
 */
@JsonSerialize(using = StudentListSerializer.class)
public class StudentListWrapper {
	
	private final List<Student> students = new ArrayList<>();
	private final Map<String, Course> coursesMap = new HashMap<>();
	
	public StudentListWrapper(final List<Student> students, final List<Course> courses) {
		this.students.addAll(students);
		for(final Course course : courses) {
			this.coursesMap.put(course.getCode(), course);
		}
	}
	
	public List<Student> getStudents() {
		return Collections.unmodifiableList(students);
	}
	
	public Course getCourse(final String courseCode) {
		return coursesMap.get(courseCode);
	}

}

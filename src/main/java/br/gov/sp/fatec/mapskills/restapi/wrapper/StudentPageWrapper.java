/*
 * @(#)StudentListWrapper.java 1.0 13/01/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.restapi.wrapper;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.springframework.data.domain.Page;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import br.gov.sp.fatec.mapskills.domain.user.student.Student;
import br.gov.sp.fatec.mapskills.restapi.serializer.StudentListSerializer;
import lombok.Getter;

/**
 * 
 * A classe {@link StudentPageWrapper} encapsula uma colecao de alunos e
 * um mapa <codigo do curso / curso> para que seja serializado o aluno
 * com informacao sobre seu curso.
 *
 * @author Marcelo
 * @version 1.0 13/01/2017
 */
@Getter
@JsonSerialize(using = StudentListSerializer.class)
public class StudentPageWrapper {
	
	private final Integer totalPages;
	private final Integer numberCurrentPage;
	private final Boolean isLast; 
	private final List<Student> students = new LinkedList<>();
	
	public StudentPageWrapper(final Page<Student> students) {
		this.students.addAll(students.getContent());
		this.totalPages = students.getTotalPages();
		this.numberCurrentPage = students.getNumber();
		this.isLast = students.isLast();
	}
	
	public List<Student> getStudents() {
		return Collections.unmodifiableList(students);
	}

}
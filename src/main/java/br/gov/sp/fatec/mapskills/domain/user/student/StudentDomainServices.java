/*
 * @(#)StudentDomainServices.java 1.0 1 12/09/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */

package br.gov.sp.fatec.mapskills.domain.user.student;

import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Component;

import br.gov.sp.fatec.mapskills.infra.StudentExcelDocumentReader;
import lombok.AllArgsConstructor;

/**
 * A classe {@link StudentDomainServices}
 *
 * @author Marcelo
 * @version 1.0 12/09/2017
 */
@Component
@AllArgsConstructor
public class StudentDomainServices {
	
	private final StudentRepository repository;
	private final StudentExcelDocumentReader documentReader;
	
	public List<Student> saveStudentsFromExcel(final InputStream inputStream) {
		final List<Student> students = documentReader.readDocument(inputStream);
		final Iterable<Student> studentsSaved = repository.save(students);
		final List<Student> studentsAsList = StreamSupport.stream(studentsSaved.spliterator(), false)
			      .collect(Collectors.toList());
		return studentsAsList;
	}

}
/*
 * @(#)StudentDomainServices.java 1.0 1 12/09/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */

package br.gov.sp.fatec.mapskills.domain.user.student;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

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
		final List<Student> studentsToSave = new LinkedList<>();
		students.stream().forEach(student -> {
			final Student studentFound = repository.findByRaOrUsername(student.getFullRa(), student.getUsername());
			if (ObjectUtils.isEmpty(studentFound)) {
				studentsToSave.add(student);
				return;
			}
			studentFound.update(student);
			studentsToSave.add(studentFound);
		});		
		final Iterable<Student> studentsSaved = repository.save(studentsToSave);
		final List<Student> studentsAsList = StreamSupport.stream(studentsSaved.spliterator(), false)
			      .collect(Collectors.toList());
		return studentsAsList;
	}
	
	public Student saveStudent(final Student student) {
		return repository.save(student);
	}
	
	public Student updateStudent(final Long id, final Student student) {
		final Student studentFound = repository.findOne(id);
		studentFound.update(studentFound);
		return repository.save(studentFound);
	}
}
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

import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import br.gov.sp.fatec.mapskills.domain.event.DomainEvent;
import br.gov.sp.fatec.mapskills.domain.event.DomainEventsNotifier;
import br.gov.sp.fatec.mapskills.domain.studentquestioncontext.StudentQuestionContext;
import br.gov.sp.fatec.mapskills.domain.studentquestioncontext.StudentQuestionContextRepository;
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
	private final DomainEventsNotifier notifier;
	private final StudentQuestionContextRepository answerRepository;
	
	public void saveStudentsFromExcel(final InputStream inputStream) {
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
		repository.save(studentsToSave);
	}
	
	public Student saveStudent(final Student student) {
		return repository.save(student);
	}
	
	public Student updateStudent(final Long id, final Student student) {
		final Student studentFound = repository.findOne(id);
		studentFound.update(studentFound);
		return repository.save(studentFound);
	}

	public void registryAnswerContext(final StudentQuestionContext context, final int remainingQuestions) {
		answerRepository.save(context);
		if(remainingQuestions == 0) {
			studentCompleted(context.getStudentId());
		}
	}
	
	private void studentCompleted(final Long studentId) {
		final Student student = repository.findOne(studentId);
		final DomainEvent event = student.completed();
		repository.save(student);
		notifier.notifyListeners(event);
	}
}
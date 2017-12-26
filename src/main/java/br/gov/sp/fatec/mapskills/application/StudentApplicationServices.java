/*
 * @(#)StudentApplicationServices.java 1.0 1 12/09/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */

package br.gov.sp.fatec.mapskills.application;

import java.io.InputStream;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

import br.gov.sp.fatec.mapskills.domain.studentquestioncontext.StudentQuestionContext;
import br.gov.sp.fatec.mapskills.domain.theme.GameThemeDomainServices;
import br.gov.sp.fatec.mapskills.domain.theme.Scene;
import br.gov.sp.fatec.mapskills.domain.user.student.Student;
import br.gov.sp.fatec.mapskills.domain.user.student.StudentDomainServices;
import lombok.AllArgsConstructor;

/**
 * A classe {@link StudentApplicationServices} contem as funcionalidades
 * relacionados a alunos.
 *
 * @author Marcelo
 * @version 1.0 12/09/2017
 */
@ApplicationServices
@AllArgsConstructor
public class StudentApplicationServices {
	
	private final StudentDomainServices domainServices;
	private final GameThemeDomainServices gameDomainServices;
	
	@Transactional
	@PreAuthorize("isFullyAuthenticated()")
	public void saveStudentsFromExcel(final InputStream inputStream) {
		domainServices.saveStudentsFromExcel(inputStream);
	}
	
	@Transactional
	@PreAuthorize("isFullyAuthenticated()")
	public Student saveStudent(final Student student) {
		return domainServices.saveStudent(student);
	}
	
	@Transactional
	@PreAuthorize("isFullyAuthenticated()")
	public Student updateStudent(final Long id, final Student student) {
		return domainServices.updateStudent(id, student);
	}
	
	@Transactional
	@PreAuthorize("isFullyAuthenticated()")
	public void registryAnswerContext(final StudentQuestionContext context, final int remainingQuestions) {
		domainServices.registryAnswerContext(context, remainingQuestions);
	}
	
	@PreAuthorize("isFullyAuthenticated()")
	public List<Scene> getScenesNotAnswered(final Long studentId) {
		return gameDomainServices.getScenesNotAnswered(studentId);
	}
}
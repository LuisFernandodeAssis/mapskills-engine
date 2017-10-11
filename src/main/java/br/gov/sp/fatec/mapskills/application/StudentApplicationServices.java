/*
 * @(#)StudentApplicationServices.java 1.0 1 12/09/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */

package br.gov.sp.fatec.mapskills.application;

import java.io.InputStream;
import java.util.Collections;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

import br.gov.sp.fatec.mapskills.domain.studentquestioncontext.StudentQuestionContext;
import br.gov.sp.fatec.mapskills.domain.studentquestioncontext.StudentQuestionContextRepository;
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
	private final StudentQuestionContextRepository answerRepository;
	private final GameThemeDomainServices gameDomainServices;
	
	@Transactional
	@PreAuthorize("isFullyAuthenticated()")
	public List<Student> saveStudentsFromExcel(final InputStream inputStream) {
		return domainServices.saveStudentsFromExcel(inputStream);
	}
	
	@Transactional
	@PreAuthorize("isFullyAuthenticated()")
	public void registryAnswerContext(final StudentQuestionContext context) {
		answerRepository.save(context);
	}
	
	@PreAuthorize("isFullyAuthenticated()")
	public List<Scene> getSceneNotAnswered(final Long studentId) {
		final List<StudentQuestionContext> context = answerRepository.findByStudentId(studentId);
		final List<Scene> scenes = gameDomainServices.getScenesNotAnswered(studentId, context);
		if (scenes.isEmpty()) {
			//marca que o aluno terminou
		}
		return scenes;
	}
}
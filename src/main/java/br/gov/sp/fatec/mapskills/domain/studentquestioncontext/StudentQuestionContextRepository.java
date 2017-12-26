/*
 * @(#)StudentQuestionContextRepository.java 1.0 19/03/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.domain.studentquestioncontext;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
/**
 * 
 * A classe {@link StudentQuestionContextRepository}
 *
 * @author Marcelo
 * @version 1.0 19/03/2017
 */
public interface StudentQuestionContextRepository extends CrudRepository<StudentQuestionContext, Long> {

	Optional<StudentQuestionContext> findFirstByStudentIdOrderBySceneIndexDesc(final Long id);	
}
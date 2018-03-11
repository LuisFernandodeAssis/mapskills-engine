/* @(#)AnswerWrapper.java 1.0 04/01/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */

package br.gov.sp.fatec.mapskills.restapi.wrapper;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import br.gov.sp.fatec.mapskills.domain.studentquestioncontext.StudentQuestionContext;
import br.gov.sp.fatec.mapskills.restapi.serializer.StudentQuestionContextDeserializer;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * A classe {@link StudentQuestionContextWrapper} contem o contexto
 * de resposta formada pelo aluno ao selecionar
 * uma alternativa durante o jogo.
 *
 * @author Marcelo
 * @version 1.0 04/01/2017
 */
@Getter
@AllArgsConstructor
@JsonDeserialize(using = StudentQuestionContextDeserializer.class)
public class StudentQuestionContextWrapper {
	
	private final StudentQuestionContext context;
	private final int remainingQuestions;
}
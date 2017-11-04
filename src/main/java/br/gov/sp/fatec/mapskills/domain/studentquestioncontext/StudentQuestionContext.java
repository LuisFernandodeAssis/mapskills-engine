/* @(#)StudentQuestionContext.java 1.0 04/01/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.domain.studentquestioncontext;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
/**
 * 
 * A classe {@link StudentQuestionContext} representa todas as informacoes de
 * escolha de uma alternativa de uma questao respondida durante o jogo.
 *
 * @author Marcelo
 * @version 1.0 04/01/2017
 */
@Getter
@Entity
@Table(name = "MAPSKILLS.STUDENT_QUESTION_CONTEXT")
public class StudentQuestionContext {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "DATE", nullable = false)
	private final LocalDateTime date;
	
	@Column(name = "ID_SKILL")
	private final Long skillId;
	
	@Column(name = "SKILL_VALUE")
	private final Integer skillValue;

	@Column(name = "ID_STUDENT", nullable = false)
	private final Long studentId;
	
	@Column(name = "ID_SCENE", nullable = false)
	private final Long sceneId;
	
	@Column(name = "SCENE_INDEX", nullable = false)
	private final Integer sceneIndex;
	
	@SuppressWarnings("unused")
	private StudentQuestionContext() {
		this(null, null, null, null, null);
	}
	
	public StudentQuestionContext(final Long sceneId, final Integer sceneIndex,
			final Long studentId, final Long skillId, final Integer skillValue) {
		this.sceneId = sceneId;
		this.sceneIndex = sceneIndex;
		this.studentId = studentId;
		this.skillId = skillId;
		this.skillValue = skillValue;
		this.date = LocalDateTime.now();
	}
}
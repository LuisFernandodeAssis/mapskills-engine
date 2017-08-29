/* @(#)AnswerEvent.java 1.0 04/01/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.domain.answerevent;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Getter;
/**
 * 
 * A classe {@link AnswerEvent} representa todas as informacoes de
 * escolha de uma alternativa de uma questao respondida durante o jogo.
 *
 * @author Marcelo
 * @version 1.0 04/01/2017
 */
@Getter
@Entity
@Table(name = "MAPSKILLS.STUDENT_QUESTION_EVENT")
public class AnswerEvent implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "sqe_id")
	private Long id;
	
	@Column(name = "sqe_skill_value")
	private Integer skillValue;
	
	@Column(name = "ski_id")
	private Long skillId;

	@Column(name = "use_id", nullable = false)
	private Long studentId;
	
	@Column(name = "scn_id", nullable = false)
	private Long sceneId;
	
	@Column(name = "scn_index", nullable = false)
	private Integer sceneIndex;
			
	@Column(name = "sqe_date", nullable = false)
	private Timestamp date;
	
	@SuppressWarnings("unused")
	private AnswerEvent() {
		this(null, null, null, null, null);
	}
	
	@Builder
	public AnswerEvent(final Integer sceneIndex, final Long sceneId, final Long studentId,
			final Long skillId, final Integer skillValue) {
		this.sceneIndex = sceneIndex;
		this.sceneId = sceneId;
		this.studentId = studentId;
		this.skillId = skillId;
		this.skillValue = skillValue;
		this.date = new Timestamp(System.currentTimeMillis());
	}

}
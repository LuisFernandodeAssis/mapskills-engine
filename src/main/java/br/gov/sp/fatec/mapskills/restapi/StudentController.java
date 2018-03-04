/*
 * @(#)MapSkillsController.java 1.0 30/09/2016
 *
 * Copyright (c) 2016, Fatec-Jessen Vidal. All rights reserved.Fatec-Jessen Vidal 
 * proprietary/confidential. Use is subject to license terms.
 */

package br.gov.sp.fatec.mapskills.restapi;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.gov.sp.fatec.mapskills.application.StudentApplicationServices;
import br.gov.sp.fatec.mapskills.domain.theme.Scene;
import br.gov.sp.fatec.mapskills.domain.user.student.Student;
import br.gov.sp.fatec.mapskills.restapi.wrapper.FileBase64Wrapper;
import br.gov.sp.fatec.mapskills.restapi.wrapper.ListWrapper;
import br.gov.sp.fatec.mapskills.restapi.wrapper.SingleWrapper;
import br.gov.sp.fatec.mapskills.restapi.wrapper.StudentQuestionContextWrapper;
import br.gov.sp.fatec.mapskills.restapi.wrapper.StudentWrapper;
import lombok.AllArgsConstructor;

/**
 * A classe <code>MapSkillsController</code> eh responsavel por conter as rotas
 * de controle da aplicacao.
 * 
 * @author Marcelo Inacio
 *
 */
@RestController
@AllArgsConstructor
public class StudentController {
	
	private final StudentApplicationServices applicationServices;
	
	/**
	 * Endpoint que realiza o cadastro de uma lista de alunos por meio de um aquivio
	 * excel (.xlsx) feito pelo perfil <code>MENTOR</code>
	 */
	@PostMapping("/students")
	public void importStudents(@RequestBody final FileBase64Wrapper inputStreamWrapper) {
		applicationServices.saveStudentsFromExcel(inputStreamWrapper.getInputStream());
	}
	
	/**
	 * Endpoint que realiza o cadastro de um aluno realizado pelo perfil <b>MENTOR</b>.
	 * @param studentWrapper
	 * @return
	 */
	@PostMapping("/student")
	public SingleWrapper<Student> saveStudent(@RequestBody final StudentWrapper studentWrapper) {
		final Student student = applicationServices.saveStudent(studentWrapper.getStudent());
		return new SingleWrapper<>(student);
	}
	
	@PutMapping("/student/{studentId}")
	public SingleWrapper<Student> updateStudent(@PathVariable("studentId")final Long id,
			@RequestBody final StudentWrapper wrapper) {
		final Student student = applicationServices.updateStudent(id, wrapper.getStudent());
		return new SingleWrapper<>(student);
	}
	
	/**
	 * Endpoint que realiza a persistencia de um contexto de resposta feita pelo aluno
	 * durante a realização do jogo, a cada click de uma alternativa dispara este post.
	 * 
	 * @param contextWrapper Contexto com as informacoes da resposta e aluno.
	 */
	@PostMapping("/student/game/answer")
	public void saveAnswer(@RequestBody final StudentQuestionContextWrapper contextWrapper) {
		applicationServices.registryAnswerContext(contextWrapper.getContext(), contextWrapper.getRemainingQuestions());
	}
	
	/**
	 * Endpoint que recupera todas as cenas ainda nao respondidas pelo aluno.
	 */
	@GetMapping("/student/{id}/scene")
	public ListWrapper<Scene> getScene(@PathVariable("id") final Long studentId) {
		final List<Scene> scenes = applicationServices.getScenesNotAnswered(studentId);
		return new ListWrapper<>(scenes);
	}
}
/*
 * @(#)MapSkillsController.java 1.0 30/09/2016
 *
 * Copyright (c) 2016, Fatec-Jessen Vidal. All rights reserved.Fatec-Jessen Vidal 
 * proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.restapi;

import java.util.List;

import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.gov.sp.fatec.mapskills.application.StudentApplicationServices;
import br.gov.sp.fatec.mapskills.domain.institution.Course;
import br.gov.sp.fatec.mapskills.domain.institution.Institution;
import br.gov.sp.fatec.mapskills.domain.institution.InstitutionService;
import br.gov.sp.fatec.mapskills.domain.theme.Scene;
import br.gov.sp.fatec.mapskills.domain.theme.SceneService;
import br.gov.sp.fatec.mapskills.domain.user.student.Student;
import br.gov.sp.fatec.mapskills.restapi.wrapper.StudentQuestionContextWrapper;
import br.gov.sp.fatec.mapskills.restapi.wrapper.FileBase64Wrapper;
import br.gov.sp.fatec.mapskills.restapi.wrapper.SceneListWrapper;
import br.gov.sp.fatec.mapskills.restapi.wrapper.StudentDetailsWrapper;
import br.gov.sp.fatec.mapskills.restapi.wrapper.StudentPageWrapper;
import br.gov.sp.fatec.mapskills.restapi.wrapper.StudentResultWrapper;
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
	
	private final StudentApplicationServices applicationService;
	
	/**
	 * Endpoint que realiza o cadastro de uma lista de alunos por meio de um aquivio
	 * excel (.xlsx) feito pelo perfil <code>MENTOR</code>
	 */
	@PostMapping(value = "/student/upload")
	public StudentPageWrapper importStudents(@RequestBody final FileBase64Wrapper inputStreamWrapper) {
		final List<Student> studentsSaved = applicationService.saveStudentsFromExcel(inputStreamWrapper.getInputStream());
		return new StudentPageWrapper(new PageImpl<Student>(studentsSaved));
	}	
	
	/**
	 * Endpoint que realiza a persistencia de um contexto de resposta feita pelo aluno
	 * durante a realização do jogo, a cada click de uma alternativa dispara este post.
	 * 
	 * @param contextWrapper Contexto com as informacoes da resposta e aluno.
	 */
	@PostMapping(value = "/student/game/answer")
	public void saveAnswer(@RequestBody final StudentQuestionContextWrapper contextWrapper) {
		applicationService.registryAnswerContext(contextWrapper.getContext());
	}
	
	/** 
	 * Endpoint que expoe servico para determinar a conclusao do jogo feito pelo aluno
	 * e retornar os resultados obtidos pelo mesmo, a ser exibido na interface de resultados.
	 * 
	 * @param studentID ID do aluno a ser recuperado.
	 * @return StudentResultWrapper Resultado a ser serializado. 
	 */
	@GetMapping(value = "/student/{id}/result")
	public StudentResultWrapper getResult(@PathVariable("id") final Long studentId) {
		
		applicationService.registryFinishGame(studentId);
		
		final Student student = institutionService.findStudentById(studentID);
		student.completed();
		institutionService.saveStudent(student);
		final List<Object[]> context = sceneService.getResultByStudentId(studentID);
		return new StudentResultWrapper(context);
	}
	
	/**
	 * Endpoint que recupera todas as cenas ainda nao respondidas pelo aluno.
	 */
	@GetMapping(value = "/student/{id}/scene")
	public SceneListWrapper getScene(@PathVariable("id") final Long studentId) {
		final List<Scene> scenes = applicationService.getSceneNotAnswered(studentId);
		return new SceneListWrapper(scenes);
		/*final SceneListWrapper scenesListWrapper = new SceneListWrapper(sceneService.findAllNotAnsweredByStudent(studentID));
		return new ResponseEntity<>(scenesListWrapper, HttpStatus.OK);*/
	}
	
	/**
	 * Endpoint que recupera os detalhes de um aluno a partir do seu RA.
	 */
	@RequestMapping(value = "/student/{ra}/details", method = RequestMethod.GET)
	public ResponseEntity<StudentDetailsWrapper> findStudentDetails(@PathVariable("ra") final String ra) {
		final Student student = institutionService.findStudentByRa(ra);
		final Institution institution = institutionService.findInstitutionByCode(student.getInstitutionCode());
		final Course course = institution.getCourseByCode(student.getCourseCode());
		final StudentDetailsWrapper studentDetailsWrapper = new StudentDetailsWrapper(student, course, institution);
		
		return new ResponseEntity<>(studentDetailsWrapper, HttpStatus.OK);
	}
}
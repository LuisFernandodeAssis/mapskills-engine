/*
 * @(#)MapSkillsController.java 1.0 30/09/2016
 *
 * Copyright (c) 2016, Fatec-Jessen Vidal. All rights reserved.Fatec-Jessen Vidal 
 * proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.restapi;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.gov.sp.fatec.mapskills.domain.MapSkillsException;
import br.gov.sp.fatec.mapskills.domain.institution.Course;
import br.gov.sp.fatec.mapskills.domain.institution.Institution;
import br.gov.sp.fatec.mapskills.domain.institution.InstitutionService;
import br.gov.sp.fatec.mapskills.domain.scene.SceneService;
import br.gov.sp.fatec.mapskills.domain.user.student.Student;
import br.gov.sp.fatec.mapskills.restapi.wrapper.AnswerWrapper;
import br.gov.sp.fatec.mapskills.restapi.wrapper.SceneListWrapper;
import br.gov.sp.fatec.mapskills.restapi.wrapper.StudentDetailsWrapper;
import br.gov.sp.fatec.mapskills.restapi.wrapper.StudentResultWrapper;
/**
 * A classe <code>MapSkillsController</code> eh responsavel por conter as rotas
 * de controle da aplicacao.
 * 
 * @author Marcelo Inácio
 *
 */
@RestController
@RequestMapping(StudentController.BASE_PATH)
public class StudentController {
	
	public static final String BASE_PATH = "/student";
	
	@Autowired
	private SceneService sceneService;
	
	@Autowired
	private InstitutionService institutionService;
	
	/**
	 * realiza a persistencia de um contexto de resposta feita pelo aluno
	 * durante a realização do jogo, ou seja cada click de uma alternativa
	 * dispara este post.
	 * @param answerWrapper contexto de id's da alternativa respondida
	 * @return 
	 * 		HTTP 200 ao concluir o post com sucesso.
	 */
	@RequestMapping(value = "/game/answer", method = RequestMethod.POST)
	public ResponseEntity<HttpStatus> saveAnswer(@RequestBody final AnswerWrapper answerWrapper) {
		sceneService.saveAnswer(answerWrapper.getAnswerContext());
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	/**
	 * recupera os resultados do aluno ao finalizar o jogo.
	 * @param studentID
	 * @return
	 * 		StudentResultWrapper
	 * @throws MapSkillsException 
	 */
	@RequestMapping(value = "/game/result/{studentID}", method = RequestMethod.GET)
	public ResponseEntity<StudentResultWrapper> getResult(@PathVariable("studentID") final long studentID) throws MapSkillsException {
		final Student student = institutionService.findStudentById(studentID);
		student.completed();
		institutionService.saveStudent(student);
		final List<Object[]> context = sceneService.getResultByStudentId(studentID);
		final StudentResultWrapper result = new StudentResultWrapper(context);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	/**
	 * recupera todas as cenas ainda nao jogadas pelo aluno.
	 * @param studentID
	 * @return
	 * 		SceneListWrapper
	 */
	@RequestMapping(value = "/game/{studentID}", method = RequestMethod.GET)
	public ResponseEntity<SceneListWrapper> findAllByEnableAndNotAnaswerByStudent(@PathVariable("studentID") final long studentID) {
		final SceneListWrapper scenesListWrapper = new SceneListWrapper(sceneService.findAllNotAnsweredByStudent(studentID));
		return new ResponseEntity<>(scenesListWrapper, HttpStatus.OK);
	}
	/**
	 * recupera os detalhes de um aluno a partir do seu RA.
	 * @param studentRA
	 * @return
	 * 		StudentDetailsWrapper
	 */
	@RequestMapping(value = "/details/{studentRA}", method = RequestMethod.GET)
	public ResponseEntity<StudentDetailsWrapper> findStudentDetails(@PathVariable("studentRA") final String studentRA) {
		final Student student = institutionService.findStudentByRa(studentRA);
		final Institution institution = institutionService.findInstitutionByCode(student.getInstitutionCode());
		final Course course = institution.getCourseByCode(student.getCourseCode());
		final StudentDetailsWrapper studentDetailsWrapper = new StudentDetailsWrapper(student, course, institution);
		
		return new ResponseEntity<>(studentDetailsWrapper, HttpStatus.OK);
	}


}

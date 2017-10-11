/*
 * @(#)MentorController.java 1.0 03/01/2017
 *
 * Copyright (c) 2016, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.restapi;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.gov.sp.fatec.mapskills.application.InstitutionApplicationServices;
import br.gov.sp.fatec.mapskills.domain.MapSkillsException;
import br.gov.sp.fatec.mapskills.domain.institution.Course;
import br.gov.sp.fatec.mapskills.domain.institution.Institution;
import br.gov.sp.fatec.mapskills.domain.institution.InstitutionService;
import br.gov.sp.fatec.mapskills.domain.theme.GameThemeService;
import br.gov.sp.fatec.mapskills.domain.user.student.Student;
import br.gov.sp.fatec.mapskills.restapi.wrapper.CourseListWrapper;
import br.gov.sp.fatec.mapskills.restapi.wrapper.CourseWrapper;
import br.gov.sp.fatec.mapskills.restapi.wrapper.GameThemeListWrapper;
import br.gov.sp.fatec.mapskills.restapi.wrapper.FileBase64Wrapper;
import br.gov.sp.fatec.mapskills.restapi.wrapper.InstitutionWrapper;
import br.gov.sp.fatec.mapskills.restapi.wrapper.InstitutionListWrapper;
import br.gov.sp.fatec.mapskills.restapi.wrapper.StudentPageWrapper;
import br.gov.sp.fatec.mapskills.restapi.wrapper.StudentWrapper;
import br.gov.sp.fatec.mapskills.restapi.wrapper.StudentsProgressByInstitutionWrapper;
import br.gov.sp.fatec.mapskills.restapi.wrapper.UserWrapper;
import lombok.AllArgsConstructor;

/**
 * 
 * A classe {@link InstitutionController} e responsavel por conter todos
 * end points (uri's) de acesso do perfil mentor da aplicacao.
 *
 * @author Marcelo
 * @version 1.0 03/01/2017
 */
@RestController
@AllArgsConstructor
public class InstitutionController {
		
	private final InstitutionApplicationServices applicationServices;

	private final InstitutionService institutionService;
	private final GameThemeService themeService;
	
	/**
	 * End-point que realiza a persistencia de lista de instituicoes
	 * por meio de um arquivo excel (.xlsx).
	 * 
	 * @param inputStreamWrapper wrapper que encapsula o arquivo.
	 * @return lista de instituicoes cadastradas.
	 */
	@PostMapping(value = "/institution/upload")
	public InstitutionListWrapper importInstitutions(@RequestBody final FileBase64Wrapper inputStreamWrapper) {		
		final List<Institution> institutionsSaved = applicationServices.saveInstituionFromExcel(inputStreamWrapper.getInputStream());		
		return new InstitutionListWrapper(institutionsSaved);
	}
	
	/**
	 * End-point que realiza a persistencia de uma unica instituicao.
	 * 
	 * @param inputStreamWrapper instituicao encapsulada a ser cadastrada.
	 * @return a instituicao cadastrada.
	 */
	@PostMapping(value = "/institution")
	public InstitutionWrapper saveInstitution(@RequestBody final InstitutionWrapper institutionWrapper) {
		final Institution institution = applicationServices.saveInstitution(institutionWrapper.getInstitution());
		return new InstitutionWrapper(institution);
	}
	
	/**
	 * End-point que atualiza uma instituicao.
	 * 
	 * @param institutionId ID da instituicao a ser atualizada.
	 * @param institutionWrapper dados para atualizacao.
	 * @return a instituicao atualizada.
	 */
	@PutMapping(value = "/institution/{id}")
	public InstitutionWrapper updateInstitution(@PathVariable("id") final Long institutionId,
			@RequestBody final InstitutionWrapper institutionWrapper) {
		final Institution institutionUpdated = applicationServices.updateInstitution(institutionId, institutionWrapper.getInstitution());
		return new InstitutionWrapper(institutionUpdated);
	}
	
	/**
	 * End-point que recupera todas instituicoes cadastradas na aplicacao.
	 * 
	 * @return Lista de todas intuticoes.
	 */
	@GetMapping(value = "/institutions")
	public InstitutionListWrapper getAllInstitution() {
		return new InstitutionListWrapper(applicationServices.getAllInstitutions());
	}
	
	/**
	 * End-point que recupera os detalhes de uma instituicao.
	 * 
	 * @param institutionId id da intituicao a ser recuperada.
	 * @return instituticao encontrada.
	 */
	@GetMapping(value = "/institution/{id}")
	public InstitutionWrapper getInstitution(@PathVariable("id") final Long institutionId) {
		final Institution institution = applicationServices.getInstitutionById(institutionId);
		return new InstitutionWrapper(institution);
	}	
	
	
	
	
	
	
	
	/**
	 * Metodo que realiza o cadastro de um aluno realizado pelo perfil <code>MENTOR</code>
	 * @param studentWrapper
	 * @return
	 * @throws MapSkillsException 
	 */
	@RequestMapping(value = "/student", method = RequestMethod.POST)
	public ResponseEntity<UserWrapper> saveStudent(@RequestBody final StudentWrapper studentWrapper) {
		final Student studentSaved = institutionService.saveStudent(studentWrapper.getStudent());
		final UserWrapper saved = new UserWrapper(studentSaved);
		return new ResponseEntity<>(saved, HttpStatus.CREATED);
	}
	
	/**
	 * End Point que atualiza um aluno a partir de seu id.
	 * @param studentWrapper
	 * @param studentId
	 * @return
	 * @throws MapSkillsException eh lancado caso haja algum problema com objeto ao
	 * realizar persistencia.
	 */
	@RequestMapping(value = "/student/{studentId}", method = RequestMethod.PUT)
	public ResponseEntity<StudentWrapper> updateStudent(@RequestBody final StudentWrapper studentWrapper,
			@PathVariable("studentId") final long studentId) {
		
		final Student updated = institutionService.updateStudent(studentId, studentWrapper.getStudent());
		final StudentWrapper updatedWrapper = new StudentWrapper(updated); 
		return new ResponseEntity<>(updatedWrapper, HttpStatus.OK);
	}
	
	/**
	 * Realiza persistencia de um novo curso em um instituição
	 * @param courseWrapper
	 * @return
	 */
	@RequestMapping(value = "/course", method = RequestMethod.POST)
	public ResponseEntity<CourseWrapper> saveCourse(@RequestBody final CourseWrapper courseWrapper) {
		final Course course = institutionService.saveCourse(courseWrapper.getCourse());
		final CourseWrapper saved = new CourseWrapper(course);
		return new ResponseEntity<>(saved, HttpStatus.CREATED);
	}
	
	/**
	 * End-point que retorna todos alunos de um determinada instituicao, atraves do seu codigo.
	 * realizado pelo perfil <code>MENTOR</code>
	 * @param institutionCode
	 * @return
	 */
	@GetMapping(value = "/{institutionCode}/students")
	public StudentPageWrapper getAllStudentsByInstitution(
			@PathVariable("institutionCode") final String institutionCode,
			final Pageable pageable) {
		
		final Page<Student> students = institutionService.findAllStudentsByInstitution(institutionCode, pageable);
		return new StudentPageWrapper(students);
	}
	/**
	 * End-point que retorna todos os cursos de uma instituição
	 * @param institutionCode
	 * @return
	 */
	@RequestMapping(value = "/{institutionCode}/courses", method = RequestMethod.GET)
	public ResponseEntity<CourseListWrapper> getAllCoursesByInstitution(
			@PathVariable("institutionCode") final String institutionCode) {
		
		final Institution institution = institutionService.findInstitutionByCode(institutionCode);
		final CourseListWrapper coursesWrapper = new CourseListWrapper(institution.getCourses());
		return new ResponseEntity<>(coursesWrapper, HttpStatus.OK);
	}
	/**
	 * retorna todos temas ativados, para que o mentor escolha um, e os alunos
	 * joguem no semestre.
	 * @return
	 */
	@RequestMapping(value = "/themes", method = RequestMethod.GET)
	public ResponseEntity<GameThemeListWrapper> getAllThemes() {
		final GameThemeListWrapper gameThemes = new GameThemeListWrapper(themeService.findAllThemesActivated()); 
		return new ResponseEntity<>(gameThemes, HttpStatus.OK);
	}
	/**
	 * recupera o id do tema atual da instituição pelo codigo da instituição.
	 * @param institutionCode
	 * @return
	 */
	@RequestMapping(value = "/{institutionCode}/theme/current", method = RequestMethod.GET)
	public ResponseEntity<Long> getThemeCurrent(@PathVariable("institutionCode") final String institutionCode) {
		final Long themeIdCurrent = institutionService.findThemeCurrent(institutionCode);
		return new ResponseEntity<>(themeIdCurrent, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{institutionCode}/theme/{themeId}", method = RequestMethod.PUT)
	public ResponseEntity<HttpStatus> updateThemeCurrent(
			@PathVariable("institutionCode") final String institutionCode,
			@PathVariable("themeId") final long themeId) {
		
		institutionServices.updateGameThemeInstitution(institutionCode, themeId);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	/**
	 * metodo que recupera os detalhes do perfil de um mentor
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/mentor/details/{userId}", method = RequestMethod.GET)
	public ResponseEntity<HttpStatus> getMentorDetails(@PathVariable("userId") final long userId) {
		//TODO Fazer método que recupere os detalhes do mentor
		return new ResponseEntity<>(HttpStatus.OK);
	}
	/**
	 * 
	 * @param institutionCode
	 * @return
	 */
	@RequestMapping(value = "/{institutionCode}/progress", method = RequestMethod.GET)
	public ResponseEntity<StudentsProgressByInstitutionWrapper> getStudentsProgress(
			@PathVariable("institutionCode") final String institutionCode) {
		
		final List<Object[]> resultSet = institutionService.getStudentsProgressByInstitution(institutionCode);
		final StudentsProgressByInstitutionWrapper progress = new StudentsProgressByInstitutionWrapper(resultSet);
		return new ResponseEntity<>(progress, HttpStatus.OK);
	}

}

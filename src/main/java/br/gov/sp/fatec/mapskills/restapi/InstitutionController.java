/*
 * @(#)MentorController.java 1.0 03/01/2017
 *
 * Copyright (c) 2016, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.restapi;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.gov.sp.fatec.mapskills.application.MapSkillsException;
import br.gov.sp.fatec.mapskills.domain.institution.Course;
import br.gov.sp.fatec.mapskills.domain.institution.Institution;
import br.gov.sp.fatec.mapskills.domain.institution.InstitutionService;
import br.gov.sp.fatec.mapskills.domain.theme.GameThemeService;
import br.gov.sp.fatec.mapskills.domain.user.student.Student;
import br.gov.sp.fatec.mapskills.domain.user.student.StudentExcelIO;
import br.gov.sp.fatec.mapskills.restapi.wrapper.CourseListWrapper;
import br.gov.sp.fatec.mapskills.restapi.wrapper.CourseWrapper;
import br.gov.sp.fatec.mapskills.restapi.wrapper.GameThemeListWrapper;
import br.gov.sp.fatec.mapskills.restapi.wrapper.InputStreamWrapper;
import br.gov.sp.fatec.mapskills.restapi.wrapper.StudentListWrapper;
import br.gov.sp.fatec.mapskills.restapi.wrapper.StudentWrapper;
import br.gov.sp.fatec.mapskills.restapi.wrapper.UserWrapper;
import br.gov.sp.fatec.mapskills.restapi.wrapper.report.StudentsProgressByInstitutionWrapper;
import br.gov.sp.fatec.mapskills.utils.BeanRetriever;

/**
 * 
 * A classe {@link InstitutionController} e responsavel por conter todos
 * end points (uri's) de acesso do perfil mentor da aplicacao.
 *
 * @author Marcelo
 * @version 1.0 03/01/2017
 */
@RestController
@RequestMapping(InstitutionController.BASE_PATH)
public class InstitutionController {
	
	public static final String BASE_PATH = "/institution";
	
	@Autowired
	private InstitutionService institutionService;
	
	@Autowired
	private GameThemeService themeService;
	
	/**
	 * Metodo que realiza o cadastro de uma lista de alunos por meio de um aquivio
	 * excel (.xlsx) feito pelo perfil <code>MENTOR</code>
	 * @param inputStreamWrapper
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/upload/students", method = RequestMethod.POST)
	public ResponseEntity<HttpStatus> importStudents(@RequestBody final InputStreamWrapper inputStreamWrapper) throws MapSkillsException {
		final StudentExcelIO studentPoi = BeanRetriever.getBean("studentExcelIO", StudentExcelIO.class);
		final List<Student> students = studentPoi.toObjectList(inputStreamWrapper.getInputStream());
		institutionService.saveStudents(students);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	/**
	 * Metodo que realiza o cadastro de um aluno realizado pelo perfil <code>MENTOR</code>
	 * @param studentWrapper
	 * @return
	 * @throws MapSkillsException 
	 */
	@RequestMapping(value = "/student", method = RequestMethod.POST)
	public ResponseEntity<UserWrapper> saveStudent(@RequestBody final StudentWrapper studentWrapper) throws MapSkillsException {
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
			@PathVariable("studentId") final long studentId) throws MapSkillsException {
		
		final Student updated = institutionService.updateStudent(studentId, studentWrapper.getStudent());
		final StudentWrapper updatedWrapper = new StudentWrapper(updated); 
		return new ResponseEntity<>(updatedWrapper, HttpStatus.OK);
	}
	
	/**
	 * Realiza persistencia de um novo curso em um institui��o
	 * @param courseWrapper
	 * @return
	 */
	@RequestMapping(value = "/course", method = RequestMethod.POST)
	public ResponseEntity<CourseWrapper> saveCourse(@RequestBody final CourseWrapper courseWrapper) {
		final Course courseSaved = institutionService.saveCourse(courseWrapper.getCourse());
		final CourseWrapper saved = new CourseWrapper(courseSaved);
		return new ResponseEntity<>(saved, HttpStatus.CREATED);
	}
	
	/**
	 * Metodo que retorna todos alunos de um determinada instituicao, atraves do seu codigo.
	 * realizado pelo perfil <code>MENTOR</code>
	 * @param institutionCode
	 * @return
	 */
	@RequestMapping(value = "/{institutionCode}/students", method = RequestMethod.GET)
	public ResponseEntity<StudentListWrapper> getAllStudentsByInstitution(
			@PathVariable("institutionCode") final String institutionCode) {
		
		final Collection<Course> courses = new ArrayList<>(); 
		final Collection<Student> students = new ArrayList<>();
		courses.addAll(institutionService.findAllCoursesByInstitutionCode(institutionCode));
		students.addAll(institutionService.findAllStudentsByInstitution(institutionCode));
		final StudentListWrapper studentsWrapper = new StudentListWrapper(students, courses);
		return new ResponseEntity<>(studentsWrapper, HttpStatus.OK);
	}
	/**
	 * retorna todos os cursos de uma institui��o
	 * @param institutionCode
	 * @return
	 */
	@RequestMapping(value = "/{institutionCode}/courses", method = RequestMethod.GET)
	public ResponseEntity<CourseListWrapper> getAllCoursesByInstitution(
			@PathVariable("institutionCode") final String institutionCode) {
		
		final Collection<Course> allCourses = new ArrayList<>();
		allCourses.addAll(institutionService.findAllCoursesByInstitutionCode(institutionCode));
		final CourseListWrapper coursesWrapper = new CourseListWrapper(allCourses);
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
	 * recupera o id do tema atual da institui��o pelo codigo da institui��o.
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
		
		final Institution institution = institutionService.findInstitutionByCode(institutionCode);
		institution.setGameThemeId(themeId);
		institutionService.saveInstitution(institution);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	/**
	 * metodo que recupera os detalhes do perfil de um mentor
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/mentor/details/{userId}", method = RequestMethod.GET)
	public ResponseEntity<HttpStatus> getMentorDetails(@PathVariable("userId") final long userId) {
		//TODO Fazer m�todo que recupere os detalhes do mentor
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

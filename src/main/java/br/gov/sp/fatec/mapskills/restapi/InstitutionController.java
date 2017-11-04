/*
 * @(#)InstitutionController.java 1.0 03/01/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.restapi;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.gov.sp.fatec.mapskills.application.InstitutionApplicationServices;
import br.gov.sp.fatec.mapskills.domain.institution.Course;
import br.gov.sp.fatec.mapskills.domain.institution.Institution;
import br.gov.sp.fatec.mapskills.domain.user.student.Student;
import br.gov.sp.fatec.mapskills.restapi.wrapper.CourseListWrapper;
import br.gov.sp.fatec.mapskills.restapi.wrapper.CourseWrapper;
import br.gov.sp.fatec.mapskills.restapi.wrapper.FileBase64Wrapper;
import br.gov.sp.fatec.mapskills.restapi.wrapper.InstitutionListWrapper;
import br.gov.sp.fatec.mapskills.restapi.wrapper.InstitutionWrapper;
import br.gov.sp.fatec.mapskills.restapi.wrapper.StudentPageWrapper;
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
	
	/**
	 * End-point que realiza a persistencia de lista de instituicoes
	 * por meio de um arquivo excel (.xlsx).
	 * 
	 * @param inputStreamWrapper wrapper que encapsula o arquivo.
	 * @return lista de instituicoes cadastradas.
	 */
	@PostMapping("/institution/upload")
	public InstitutionListWrapper importInstitutions(@RequestBody final FileBase64Wrapper inputStreamWrapper) {		
		final List<Institution> institutionsSaved = applicationServices.saveInstituionFromExcel(inputStreamWrapper.getInputStream());		
		return new InstitutionListWrapper(institutionsSaved);
	}
	
	/**
	 * End-point que realiza o cadastro de uma instituicao.
	 * 
	 * @param inputStreamWrapper instituicao encapsulada a ser cadastrada.
	 * @return a instituicao cadastrada.
	 */
	@PostMapping("/institution")
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
	@PutMapping("/institution/{id}")
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
	@GetMapping("/institutions")
	public InstitutionListWrapper getAllInstitution() {
		return new InstitutionListWrapper(applicationServices.getAllInstitutions());
	}
	
	/**
	 * End-point que recupera os detalhes de uma instituicao.
	 * 
	 * @param institutionId id da intituicao a ser recuperada.
	 * @return instituticao encontrada.
	 */
	@GetMapping("/institution/{id}")
	public InstitutionWrapper getInstitution(@PathVariable("id") final Long institutionId) {
		final Institution institution = applicationServices.getInstitutionById(institutionId);
		return new InstitutionWrapper(institution);
	}	
	
	/**
	 * Endpoint que realiza o cadastro de um curso para uma instituticao.
	 */
	@PostMapping("/institution/course")
	public CourseListWrapper saveCourse(@RequestBody final CourseWrapper courseWrapper) {
		final List<Course> courses = applicationServices.saveCourse(courseWrapper.getCourse());
		return new CourseListWrapper(courses);
	}
	
	/**
	 * End-point que retorna todos alunos de um determinada instituicao, atraves do seu codigo.
	 * Realizado pelo perfil <b>MENTOR</b>.
	 */
	@GetMapping("/institution/{code}/students")
	public StudentPageWrapper getAllStudentsByInstitution(@PathVariable("code") final String institutionCode,
			final Pageable pageable) {
		final Page<Student> students = applicationServices.getStudentsByInstitutionCode(institutionCode, pageable);
		return new StudentPageWrapper(students);
	}
	
	/**
	 * End-point que retorna todos os cursos de uma instituicao.
	 */
	@GetMapping("/institution/{code}/courses")
	public CourseListWrapper getCoursesByInstitutionCode(
			@PathVariable("code") final String institutionCode) {		
		final List<Course> courses = applicationServices.getCoursesByInstitutionCode(institutionCode);
		return new CourseListWrapper(courses);
	}	
	
	/**
	 * Endpoint que realiza a atualizacao do tema do jogo para instituicao.
	 */
	@PutMapping("/institution/{code}/theme/{themeId}")
	public void updateThemeCurrent(@PathVariable("code") final String institutionCode,
			@PathVariable("themeId") final Long themeId) {
		applicationServices.updateGameThemeInstitution(institutionCode, themeId);
	}
}
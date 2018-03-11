/*
 * @(#)InstitutionDomainService.java 1.0 1 02/09/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */

package br.gov.sp.fatec.mapskills.domain.institution;

import java.io.InputStream;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import br.gov.sp.fatec.mapskills.domain.ObjectNotFoundException;
import br.gov.sp.fatec.mapskills.domain.theme.GameTheme;
import br.gov.sp.fatec.mapskills.domain.theme.GameThemeRepository;
import br.gov.sp.fatec.mapskills.domain.user.UserRepository;
import br.gov.sp.fatec.mapskills.domain.user.student.Student;
import br.gov.sp.fatec.mapskills.domain.user.student.StudentRepository;
import br.gov.sp.fatec.mapskills.domain.user.student.StudentSpecification;
import br.gov.sp.fatec.mapskills.infra.InstitutionExcelDocumentReader;
import lombok.AllArgsConstructor;

/**
 * A classe {@link InstitutionDomainServices}
 *
 * @author Marcelo
 * @version 1.0 02/09/2017
 */
@Component
@AllArgsConstructor
public class InstitutionDomainServices {
	
	private final InstitutionRepository institutionRepository;
	private final GameThemeRepository themeRepository;
	private final InstitutionExcelDocumentReader documentReader;
	private final UserRepository userRepository;
	private final StudentRepository studentRepository;
	
	public void updateGameTheme(final String institutionCode, final Long themeId) {
		final Institution institution = institutionRepository.findByCode(institutionCode);
		final GameTheme theme = themeRepository.findOne(themeId);
		institution.updateGameTheme(theme);
		institutionRepository.save(institution);
	}
	
	public List<Institution> saveInstituionFromExcel(final InputStream inputStream) {
		final List<Institution> institutionsFromExcel = documentReader.readDocument(inputStream);
		institutionRepository.save(institutionsFromExcel);
		return institutionsFromExcel;
	}
	
	public Institution saveInstitution(final Institution newInstitution) {
		return institutionRepository.save(newInstitution);
	}
	
	public Institution updateInstitution(final Long id, final Institution institutionUpdate) {
		final Institution institution = institutionRepository.findOne(id);
		institution.update(institutionUpdate);
		institutionRepository.save(institution);
		return institution;
	}
	
	public List<Institution> getAllInstitutions() {
		return institutionRepository.findAll();
	}
	
	public Institution getInstitutionById(final Long id) {
		final Institution institution = institutionRepository.findOne(id);
		if(ObjectUtils.isEmpty(institution)) {
			throw new ObjectNotFoundException("instituição de ID = " + id + " não encontrado");
		}
		return institution;
	}
	
	public void saveCourse(final Course newCourse) {
		final Mentor mentor = (Mentor) userRepository.getLoggedUser();
		final Institution institution = getInstitutionById(mentor.getInstitutionId());
		institution.addCourse(newCourse);
		institutionRepository.save(institution);
	}
	
	public void updateCourse(final Long courseId, final Course course) {
		final Mentor mentor = (Mentor) userRepository.getLoggedUser();
		final Institution institution = getInstitutionById(mentor.getInstitutionId());
		institution.updateCourse(courseId, course);
		institutionRepository.save(institution);
	}

	public Page<Student> getStudents(final StudentSpecification specification, final Pageable pageable) {
		return studentRepository.findAll(specification, pageable);
	}

	public List<Course> getCoursesByInstitutionCode(final String institutionCode) {
		final Institution institution = institutionRepository.findByCode(institutionCode);
		return institution.getCourses();
	}
}
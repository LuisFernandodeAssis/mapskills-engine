/*
 * @(#)InstitutionService.java 1.0 01/11/2016
 *
 * Copyright (c) 2016, Fatec Jessen Vidal. All rights reserved.
 * Fatec Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.domain.institution;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.gov.sp.fatec.mapskills.domain.MapSkillsException;
import br.gov.sp.fatec.mapskills.domain.user.mentor.Mentor;
import br.gov.sp.fatec.mapskills.domain.user.mentor.MentorRepository;
import br.gov.sp.fatec.mapskills.domain.user.student.Student;
import br.gov.sp.fatec.mapskills.domain.user.student.StudentInvalidException;
import br.gov.sp.fatec.mapskills.domain.user.student.StudentRepository;
import br.gov.sp.fatec.mapskills.infra.InstitutionExcelFileHandle;
import br.gov.sp.fatec.mapskills.infra.StudentExcelFileHandle;
import lombok.AllArgsConstructor;

/**
 * 
 * A classe {@link InstitutionService} contem todos metodos necessarios
 * para realizacao de tudo que esta relacionado ha instituicao.
 *
 * @author Marcelo
 * @version 1.0 01/11/2016
 */
@Service
@AllArgsConstructor
public class InstitutionService {
	
	private static final Logger LOGGER = Logger.getLogger(InstitutionService.class.getName());
		
	private final InstitutionRepository institutionRepository;
	private final StudentRepository studentRepository;
	private final MentorRepository mentorRepository;
	private final StudentExcelFileHandle studentExcelHandle;
	private final InstitutionExcelFileHandle institutionExcelFileHandle;
		
	public List<Institution> saveInstituionFromExcel(final InputStream inputStream) throws MapSkillsException {
		final List<Institution> institutionsFromExcel = institutionExcelFileHandle.toObjectList(inputStream);
		return saveInstitutions(institutionsFromExcel);
	}
	
	public List<Student> saveStudentsFromExcel(final InputStream inputStream) throws MapSkillsException {
		final List<Student> studentsFromExcel = studentExcelHandle.toObjectList(inputStream);
		return saveStudents(studentsFromExcel);
	}

	@Transactional
	public List<Institution> saveInstitutions(final List<Institution> institutions) {
		final List<Institution> institutionsSaved = new ArrayList<>(institutions.size());
		for(final Institution institution : institutions) {
			institutionsSaved.add(this.saveInstitution(institution));
		}
		return institutionsSaved;
	}
	
	public void saveMentor(final Mentor mentor) {
		mentorRepository.save(mentor);
	}

	@Transactional
	public Institution saveInstitution(final Institution institution) {
		return institutionRepository.save(institution);			
	}
	
	@Transactional
	public List<Student> saveStudents(final Collection<Student> students) throws MapSkillsException {
		final List<Student> studentsSaved = new ArrayList<>(students.size());
		for(final Student student : students) {
			final Student existingStudent = studentRepository.findByRaOrUsername(student.getRa(), student.getUsername()); 
			if(existingStudent == null) {
				studentsSaved.add(this.saveStudent(student));
				continue;
			}
			existingStudent.update(student);
			studentsSaved.add(this.saveStudent(existingStudent));
		}
		return Collections.unmodifiableList(studentsSaved);
	}
	
	public Student saveStudent(final Student student) throws MapSkillsException {
		try {
			return studentRepository.save(student);
		} catch (final Exception exception) {
			LOGGER.log(Level.INFO, exception.getMessage(), exception);
			throw new StudentInvalidException(exception);
		}
	}
	
	public Course saveCourse(final Course course) {
		final Institution institution = findInstitutionByCode(course.getInstitutionCode());
		institution.addCourse(course);
		saveInstitution(institution);
		return course;
	}
	
	public Student updateStudent(final long id, final Student student) throws MapSkillsException {
		final Student studentBase = studentRepository.findOne(id);
		studentBase.update(student);
		return saveStudent(studentBase);
	}

	public Institution findInstitutionById(final long id) {
		return institutionRepository.findById(id);
	}
	
	public Institution findInstitutionByCnpj(final String cnpj) {
		return institutionRepository.findByCnpj(cnpj);
	}
	
	public Mentor findMentorByUsername(final String username) {
		return mentorRepository.findByLoginUsername(username);
	}
	
	public Institution findInstitutionByCode(final String code) {
		return institutionRepository.findByCode(code);
	}
	
	public Student findStudentByRa(final String ra) {
		return studentRepository.findByRaRa(ra);
	}
	
	public Student findStudentById(final long id) {
		return studentRepository.findOne(id);
	}
	
	public Collection<Institution> findAllInstitutions() {
		final List<Institution> institutions = new ArrayList<>();
		for(final Institution institution : institutionRepository.findAll()) {
			institutions.add(institution);
		}
		return institutions;
	}
	
	public Institution findInstitutionDetailsById(final long id) throws MapSkillsException {
		final Institution institution = institutionRepository.findById(id);
		if(institution == null) {
			throw new InstitutionNotFoundException(id);
		}
		return institution;
	}
	
	/**
	 * Metodo que recupera todos alunos de um curso de uma determinada instituicao
	 */
	public Collection<Student> findAllStudentsByCourseAndInstitution(final String courseCode, final String institutionCode) {
		return studentRepository.findAllByCourseAndInstitution(courseCode, institutionCode);
	}
	
	public List<Student> findAllStudentsByInstitution(final String institutionCode) {
		return studentRepository.findAllByRaInstitutionCode(institutionCode);
	}
	
	@Transactional(readOnly = true)
	public long findThemeCurrent(final String institutionCode) {
		return institutionRepository.findGameThemeIdByCode(institutionCode);
	}
	
	@Transactional(readOnly = true)
	public List<Object[]> getStudentsProgressByInstitution(final String institutionCode) {
		final String yearSemester = getYearSemesterCurrent();
		return institutionRepository.findStudentsProgressByInstitution(institutionCode, yearSemester);
	}
	
	@Transactional(readOnly = true)
	public List<Object[]> getGlobalPogress() {
		final String yearSemester = getYearSemesterCurrent();
		return institutionRepository.findGlobalStudentsProgress(yearSemester);
	}
	
	@Transactional(readOnly = true)
	public List<Object[]> getLevelPogress(final String level) {
		final String yearSemester = getYearSemesterCurrent();
		return institutionRepository.findLevelStudentsProgress(level, yearSemester);
	}
	/**
	 * Metodo que recupera o ano e semestre corrente.
	 */
	private String getYearSemesterCurrent() {
		final LocalDate dateCurrent = LocalDate.now();
		final String semester = dateCurrent.getMonthValue() < 7 ? "1" : "2";
		final String year = String.valueOf(dateCurrent.getYear());
		return year.substring(2).concat(semester);
	}

}
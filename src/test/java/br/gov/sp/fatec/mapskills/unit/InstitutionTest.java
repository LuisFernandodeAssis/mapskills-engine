/*
 * @(#)InstitutionTest.java 1.0 29/12/2016
 *
 * Copyright (c) 2016, Fatec Jessen Vidal. All rights reserved.
 * Fatec Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.unit;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import br.gov.sp.fatec.mapskills.domain.MapSkillsException;
import br.gov.sp.fatec.mapskills.domain.institution.Course;
import br.gov.sp.fatec.mapskills.domain.institution.CoursePeriod;
import br.gov.sp.fatec.mapskills.domain.institution.Institution;
import br.gov.sp.fatec.mapskills.domain.institution.InstitutionLevel;
import br.gov.sp.fatec.mapskills.domain.institution.InstitutionService;
import br.gov.sp.fatec.mapskills.domain.user.mentor.Mentor;
import br.gov.sp.fatec.mapskills.domain.user.student.Student;
/**
 * 
 * A classe {@link InstitutionTest} contem teste
 * de dominio da classe <code>Institution</code>.
 *
 * @author Marcelo
 * @version 1.0 29/12/2016
 */
public class InstitutionTest extends MapSkillsTest {
	
	@Autowired
	private InstitutionService institutionService;
		
	@Test
	public void saveInstitution() {
		final Institution fatec = new Institution("146", "123456789000", "Jessen Vidal", InstitutionLevel.SUPERIOR, "São José", null, null);
		fatec.addMentor(new Mentor("Mentor Responsavel Teste", "marquinhos@fatec", "Mudar@123", fatec));
		institutionService.saveInstitution(fatec);
		
		assertEquals("123456789000", institutionService.findInstitutionById(fatec.getId()).getCnpj());
	}
	
	@Test
	public void findInstitutionDetails() throws MapSkillsException {
		final Institution fatec = new Institution("147", "123456789000", "Jessen Vidal", InstitutionLevel.SUPERIOR, "São José", null, null);
		fatec.addMentor(new Mentor("Mentor Responsavel Teste", "marquinhos@fatec", "Mudar@123", fatec));
		institutionService.saveInstitution(fatec);
		
		final List<Course> coursesList = new ArrayList<>(4);
		coursesList.add(new Course("28", "Banco de dados", CoursePeriod.NOTURNO, fatec));
		coursesList.add(new Course("29", "Logistica", CoursePeriod.NOTURNO, fatec));
		coursesList.add(new Course("30", "Estruturas Leves", CoursePeriod.NOTURNO, fatec));
		coursesList.add(new Course("31", "Manutenção de Aeronaves", CoursePeriod.NOTURNO, fatec));
		institutionService.saveInstitution(fatec);
		
		final Institution institutionDetails = institutionService.findInstitutionDetailsById(fatec.getId());
		assertEquals(4, institutionDetails.getCourses().size());
	}
	
	@Test
	public void saveInstitutions() {
		final List<Institution> fatecList = new ArrayList<>(3);

		final Institution fatecOURINHOS = new Institution("147", "123456789001", "Fatec Ourinhos", InstitutionLevel.SUPERIOR, "São José", null, null);
		fatecOURINHOS.addMentor(new Mentor("Mentor Responsavel OURINHOS", "valdez@fatec", "Mudar@123", null));
		
		final Institution fatecPINDA = new Institution("148", "123456789002", "Fatec Pinda", InstitutionLevel.SUPERIOR, "Pindamonhangaba", null, null);
		fatecPINDA.addMentor(new Mentor("Mentor Responsavel PINDA", "paulo@fatec", "Mudar@123", null));
		
		final Institution fatecSP = new Institution("149", "123456789003", "Fatec SP", InstitutionLevel.SUPERIOR, "São Paulo", null, null);
		fatecSP.addMentor(new Mentor("Mentor Responsavel SP", "fagundez@fatec", "Mudar@123", null));
		
		fatecList.add(fatecOURINHOS);
		fatecList.add(fatecPINDA);
		fatecList.add(fatecSP);
		
		institutionService.saveInstitutions(fatecList);
		
		assertEquals(3, institutionService.findAllInstitutions().size());
	}
	
	@Test
	public void saveAndFindAllCoursesByInstitution() {
		final List<Course> coursesList = new ArrayList<>(4);

		final Institution fatecOURINHOS = new Institution("147", "123456789001", "Fatec Ourinhos", InstitutionLevel.SUPERIOR, "Ouro Preto", null, null);
		fatecOURINHOS.addMentor(new Mentor("Mentor Responsavel OURINHOS", "valdez@fatec", "Mudar@123", null));
		institutionService.saveInstitution(fatecOURINHOS);

		coursesList.add(new Course("28", "Banco de dados", CoursePeriod.NOTURNO, fatecOURINHOS));
		coursesList.add(new Course("29", "Logistica", CoursePeriod.NOTURNO, fatecOURINHOS));
		coursesList.add(new Course("30", "Estruturas Leves", CoursePeriod.NOTURNO, fatecOURINHOS));
		coursesList.add(new Course("31", "Manutenção de Aeronaves", CoursePeriod.NOTURNO, fatecOURINHOS));
		
		institutionService.saveInstitution(fatecOURINHOS);
		
		assertEquals(4, institutionService.findInstitutionByCode("147").getCourses().size());
	}
		
	@Test
	public void findAllStudentsByCourseAndInstitution() throws MapSkillsException {
		institutionService.saveStudents(mockStudents());
		final Student studentE = new Student("1460281423050", "Student MockE", "1289003400", "studentE@fatec.sp.gov.br", "mudar@123");
		final Student studentF = new Student("1460281423073", "Student MockF", "1289003400", "studentF@fatec.sp.gov.br", "mudar@123");
		institutionService.saveStudent(studentE);
		institutionService.saveStudent(studentF);
		
		final List<Student> students = new ArrayList<>();
		students.addAll(institutionService.findAllStudentsByCourseAndInstitution("028", "146"));
		
		assertEquals(6, students.size());
	}
	
	@Test
	public void updateInstitution() {

		final Institution fatecOURINHOS = new Institution("200", "123456909001", "Fatec Ourinhos", InstitutionLevel.SUPERIOR, "Ourinhos", null, null);
		fatecOURINHOS.addMentor(new Mentor("Victor Responsavel OURINHOS", "victor@fatec", "Mudar@123", fatecOURINHOS));
		
		final Institution fatecSAMPA = new Institution("156", "123445789001", "Fatec Sampa", InstitutionLevel.SUPERIOR, "São Paulo", null, null);
		fatecSAMPA.addMentor(new Mentor("Regina", "regina@fatec", "Mudar@123", fatecSAMPA));
		final List<Institution> institutions = new ArrayList<>(2);
		institutions.add(fatecSAMPA);
		institutions.add(fatecOURINHOS);
		institutionService.saveInstitutions(institutions);
		
		final Institution institution = institutionService.findInstitutionById(fatecSAMPA.getId());
		institution.setCnpj("71461173000155");
		institution.setCity("Jacarei");
		institution.setCompany("Fatec Jacarei");
		institutionService.saveInstitution(institution);
		
		assertEquals("Fatec Jacarei", institutionService.findInstitutionById(institution.getId()).getCompany());
	}
	
	@Test
	public void saveCourses() throws MapSkillsException {
		final Institution fatecOURINHOS = new Institution("144", "123456909001", "Fatec Ourinhos", InstitutionLevel.SUPERIOR, "Ourinhos", null, null);
		fatecOURINHOS.addMentor(new Mentor("Victor Responsavel OURINHOS", "victor@fatec", "Mudar@123", null));
		institutionService.saveInstitution(fatecOURINHOS);

		fatecOURINHOS.addCourse(new Course("200", "Estruturas Leves", CoursePeriod.NOTURNO, fatecOURINHOS));
		fatecOURINHOS.addCourse(new Course("201", "Manutenção de Aeronaves", CoursePeriod.NOTURNO, fatecOURINHOS));
		fatecOURINHOS.addCourse(new Course("202", "Logistica", CoursePeriod.NOTURNO, fatecOURINHOS));
		institutionService.saveInstitution(fatecOURINHOS);
		
		assertEquals(3, institutionService.findInstitutionByCode("144").getCourses().size());
	}
	
	@Test
	public void saveThemeInstitution() {
		final Institution fatecOURINHOS = new Institution("144", "123456909001", "Fatec Ourinhos", InstitutionLevel.SUPERIOR, "Ourinhos", null, null);
		fatecOURINHOS.addMentor(new Mentor("Victor Responsavel OURINHOS", "victor@fatec", "Mudar@123", null));
		institutionService.saveInstitution(fatecOURINHOS);
		
		final Long themeIdRetrieved = institutionService.findInstitutionById(fatecOURINHOS.getId()).getGameThemeId(); 
		
		assertEquals(new Long(0L), themeIdRetrieved);
		
		fatecOURINHOS.setGameThemeId(1L);
		institutionService.saveInstitution(fatecOURINHOS);
		
		assertEquals(new Long("1"), institutionService.findInstitutionById(fatecOURINHOS.getId()).getGameThemeId());
	}
	
	private List<Student> mockStudents() throws MapSkillsException {
		final List<Student> students = new ArrayList<>(4);
		students.add(new Student("1460281423023", "Student MockA", "1289003400", "studentA@fatec.sp.gov.br", "mudar@123"));
		students.add(new Student("1460281423024", "Student MockB", "1289003500", "studentB@fatec.sp.gov.br", "mudar@123"));
		students.add(new Student("1460281423025", "Student MockC", "1289003600", "studentC@fatec.sp.gov.br", "mudar@123"));
		students.add(new Student("1460281423026", "Student MockD", "1289003700", "studentD@fatec.sp.gov.br", "mudar@123"));
		return students;
	}
	

}

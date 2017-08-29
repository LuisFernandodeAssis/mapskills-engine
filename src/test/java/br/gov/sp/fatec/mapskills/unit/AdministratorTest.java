/*
 * @(#)AdministratorTest.java 1.0 29/12/2016
 *
 * Copyright (c) 2016, Fatec Jessen Vidal. All rights reserved.
 * Fatec Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.unit;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import br.gov.sp.fatec.mapskills.domain.MapSkillsException;
import br.gov.sp.fatec.mapskills.domain.institution.Institution;
import br.gov.sp.fatec.mapskills.domain.institution.InstitutionLevel;
import br.gov.sp.fatec.mapskills.domain.institution.InstitutionService;
import br.gov.sp.fatec.mapskills.domain.skill.Skill;
import br.gov.sp.fatec.mapskills.domain.user.Administrator;
import br.gov.sp.fatec.mapskills.domain.user.User;
import br.gov.sp.fatec.mapskills.domain.user.UserService;
import br.gov.sp.fatec.mapskills.domain.user.mentor.Mentor;
import br.gov.sp.fatec.mapskills.domain.user.student.Student;
import br.gov.sp.fatec.mapskills.infra.report.ReportFilter;
import br.gov.sp.fatec.mapskills.infra.report.ReportService;
/**
 * 
 * A classe {@link AdministratorTest} realiza
 * os testes de unidade do perfil administrador.
 *
 * @author Marcelo
 * @version 1.0 29/12/2016
 */
public class AdministratorTest extends MapSkillsTest {

	@Autowired
	private UserService userService;
	
	@Autowired
	private InstitutionService institutionService;
	
	@Autowired
	private ReportService reportService;
	
	@Test
	public void findUserByUsernamePassword() throws MapSkillsException {
		final String EXPECTED_RA = "Student MockA"; 
		final Institution fatec = new Institution("146", "123456789000", "Jessen Vidal", InstitutionLevel.SUPERIOR, "São José", null, null);
		fatec.addMentor(new Mentor("Mentor Responsavel Teste", "marquinhos@fatec.sp.gov.br", "Mudar@123", fatec));
		institutionService.saveInstitution(fatec);
		
		final Student studentSave = new Student("2000281423023","Student MockA", "1289003400", "studentA@fatec.sp.gov.br", "mudar@123");
		institutionService.saveStudent(studentSave);
		
		final User studentUser = userService.findUserByUsername("studentA@fatec.sp.gov.br");
		final User mentorUser = userService.findUserByUsername("marquinhos@fatec.sp.gov.br");
		
		assertEquals(EXPECTED_RA, studentUser.getName());
		assertEquals("Mentor Responsavel Teste", mentorUser.getName());
	}
	
	@Test
	public void saveAdministrator() {
		final Administrator admin = new Administrator("Administrador", "admin", "admin");
		userService.save(admin);
		assertEquals(admin.getId(), userService.findByUsername("admin").getId());
	}
	
	@Test
	public void getReportInstitution() throws IOException {
		for(final Skill skill : super.buildSkillsMock()) {
			skillService.save(skill);
		}
		final ReportFilter filter = new ReportFilter(null, "146", null, null, null);
		reportService.getCsvReport(filter);
	}

}
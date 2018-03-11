/* @(#)StudentExcelDocumentReader.java 1.0 03/11/2016
 *
 * Copyright (c) 2016, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */

package br.gov.sp.fatec.mapskills.infra;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import br.gov.sp.fatec.mapskills.domain.institution.Course;
import br.gov.sp.fatec.mapskills.domain.institution.Institution;
import br.gov.sp.fatec.mapskills.domain.institution.InstitutionRepository;
import br.gov.sp.fatec.mapskills.domain.user.student.AcademicRegistry;
import br.gov.sp.fatec.mapskills.domain.user.student.Student;

/**
 * A classe {@link StudentExcelDocumentReader} e responsavel por
 * manipular arquivos excel em objetos {@link Student}.
 *
 * @author Marcelo
 * @version 1.0 03/11/2016
 */
@Component
public class StudentExcelDocumentReader extends ExcelDocumentReader<Student> {
		
	private final InstitutionRepository institutionRepository;
	
	@Autowired
	public StudentExcelDocumentReader(final PasswordEncoder encoder,
			final InstitutionRepository institutionRepository) {
		super(encoder);
		this.institutionRepository = institutionRepository;
	}
	
	/**
	 * Constroi um objeto do tipo <code>Student</code> a partir de uma lista
	 * de String devolvida da chamada do metodo <code>objectArgs</code>.
	 */
	@Override
	protected Student buildEntity(final List<String> attributes) {
		final AcademicRegistry registry = new AcademicRegistry(attributes.get(0));
		final Institution institution = institutionRepository.findByCode(registry.getInstitutionCode());
		final Course course = institution.getCourseByCode(registry.getCourseCode());
		
		return new Student(registry.getFullRa(), attributes.get(1),
				attributes.get(2), course, attributes.get(3), encodePassword(registry.getStudentCode()));
	}
	
	/**
	 * Verifica se o numero de string da lista que servira como
	 * parametro para a criacao do aluno eh diferente do necessario.
	 */
	@Override
	protected boolean isValidAttributes(final List<String> attributes) {
		return attributes.size() == 4;
	}		
}
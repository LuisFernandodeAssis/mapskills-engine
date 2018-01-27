/* @(#)StudentExcelDocumentReader.java 1.0 03/11/2016
 *
 * Copyright (c) 2016, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.infra;

import java.util.List;

import org.springframework.stereotype.Component;

import br.gov.sp.fatec.mapskills.domain.institution.Course;
import br.gov.sp.fatec.mapskills.domain.institution.Institution;
import br.gov.sp.fatec.mapskills.domain.institution.InstitutionRepository;
import br.gov.sp.fatec.mapskills.domain.user.student.Student;
import lombok.AllArgsConstructor;

/**
 * 
 * A classe {@link StudentExcelDocumentReader} e responsavel por
 * manipular arquivos excel em objetos {@link Student}.
 *
 * @author Marcelo
 * @version 1.0 03/11/2016
 */
@Component
@AllArgsConstructor
public class StudentExcelDocumentReader extends ExcelDocumentReader<Student> {
		
	private final InstitutionRepository institutionRepository;
	
	/**
	 * Metodo responsavel por construir um objeto do tipo <code>Student</code> a partir
	 * de uma lista de String devolvida da chamada do metodo <code>objectArgs</code>.
	 */
	@Override
	protected Student buildEntity(final List<String> attributes) {
		final Student student = new Student(attributes.get(0), attributes.get(1),
				attributes.get(2), attributes.get(3), DEFAULT_ENCRYPTED_PASS);
		final Institution institution = institutionRepository.findByCode(student.getInstitutionCode());
		final Course course = institution.getCourseByCode(student.getCourseCode());
		student.setCourse(course);
		return student;
	}
	
	/**
	 * verifica se o numero de string da lista que servira como
	 * parametro para a criacao do aluno eh diferente do necessario.
	 */
	@Override
	protected boolean isValidAttributes(final List<String> attributes) {
		return attributes.size() == 4;
	}		
}
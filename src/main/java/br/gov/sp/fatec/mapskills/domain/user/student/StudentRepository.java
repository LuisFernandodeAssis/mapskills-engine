/* @(#)StudentRepository.java 1.0 03/11/2016
 *
 * Copyright (c) 2016, Fatec-Jessen Vidal. All rights reserved.Fatec-Jessen Vidal 
 * proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.domain.user.student;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface StudentRepository extends CrudRepository<Student, Long> {
	
	@Query("SELECT student FROM Student student WHERE student.ra.courseCode = ?1 AND student.ra.institutionCode = ?2")
	public List<Student> findAllByCourseAndInstitution(final String courseCode, final String institutionCode);
	
	@Query("SELECT student FROM Student student WHERE student.ra.ra = ?1 OR student.login.username = ?2")
	public Student findByRaOrUsername(final String ra, final String username);
	
	public Student findByRaRa(final String ra);
	/**
	 * Recupera todos alunos em detalhes de uma instituicao
	 * @param institutionCode
	 * 			Codigo da instituicao de tres digitos.
	 * @return
	 * 			Lista de alunos que fazem parte da instituicao
	 * 			que possui o codigo.
	 */
	public List<Student> findAllByRaInstitutionCode(final String institutionCode);

}

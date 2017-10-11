/* @(#)StudentRepository.java 1.0 03/11/2016
 *
 * Copyright (c) 2016, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.domain.user.student;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
/**
 * 
 * A classe {@link StudentRepository}
 *
 * @author Marcelo
 * @version 1.0 03/11/2016
 */
public interface StudentRepository extends PagingAndSortingRepository<Student, Long> {
	
	@Query("SELECT student FROM Student student WHERE student.ra.courseCode = ?1 AND student.ra.institutionCode = ?2")
	List<Student> findAllByCourseAndInstitution(final String courseCode, final String institutionCode);
	
	@Query("SELECT student FROM Student student WHERE student.ra.ra = ?1 OR student.login.username = ?2")
	Student findByRaOrUsername(final String ra, final String username);
	
	Student findByRaRa(final String ra);
	
	/**
	 * Recupera todos alunos de uma instituicao
	 * 
	 * @param institutionCode
	 * 			Codigo da instituicao de tres digitos.
	 * @return
	 * 			Lista de alunos que fazem parte da instituicao
	 * 			que possui o codigo.
	 */
	Page<Student> findAllByRaInstitutionCode(final String institutionCode, final Pageable pageable);

}
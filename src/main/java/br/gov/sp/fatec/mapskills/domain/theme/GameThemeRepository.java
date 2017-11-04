/*
 * @(#)GameThemeRepository.java 1.0 04/11/2016
 *
 * Copyright (c) 2016, Fatec Jessen Vidal. All rights reserved. Fatec Jessen Vidal
 * proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.domain.theme;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
/**
 * 
 * A interface {@link GameThemeRepository}
 *
 * @author Marcelo
 * @version 1.0 04/11/2016
 */
public interface GameThemeRepository extends CrudRepository<GameTheme, Long> {
	
	List<GameTheme> findAllByActive(final boolean active);
	
	GameTheme findByName(final String name);
	
	List<GameTheme> findAll();
	
	@Query("SELECT theme FROM GameTheme theme "
			+ "INNER JOIN Institution i ON i.gameTheme.id = theme.id "
			+ "INNER JOIN Student s ON s.ra.institutionCode = i.code "
			+ "WHERE s.id = ?1")
	GameTheme findByStudentId(final Long studentId);
}
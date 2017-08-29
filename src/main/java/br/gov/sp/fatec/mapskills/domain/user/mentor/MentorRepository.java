/*
 * @(#)MentorRepository.java 1.0 12/02/2017
 *
 * Copyright (c) 2017, Fatec Jessen Vidal. All rights reserved.
 * Fatec Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.domain.user.mentor;

import org.springframework.data.repository.CrudRepository;
/**
 * 
 * A classe {@link MentorRepository}
 *
 * @author Marcelo
 * @version 1.0 12/02/2017
 */
public interface MentorRepository extends CrudRepository<Mentor, Long> {
	
	Mentor findByLoginUsername(final String username);
	
}
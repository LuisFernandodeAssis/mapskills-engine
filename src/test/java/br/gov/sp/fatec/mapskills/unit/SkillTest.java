/*
 * @(#)SkillTest.java 1.0 29/12/2016
 *
 * Copyright (c) 2016, Fatec Jessen Vidal. All rights reserved.
 * Fatec Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import br.gov.sp.fatec.mapskills.domain.skill.Skill;
import br.gov.sp.fatec.mapskills.domain.skill.SkillService;
/**
 * 
 * A classe {@link SkillTest} contem os teste
 * de unidade para a classe <code>Skill</code>.
 *
 * @author Marcelo
 * @version 1.0 29/12/2016
 */

public class SkillTest extends MapSkillsTest {
	
	@Autowired
	private SkillService service;
		
	@Test
	public void save() {
		final Skill skill = new Skill("Liderança", "Breve descrição da habilidade");
		service.save(skill);
		assertEquals("Liderança", service.findById(skill.getId()).getType());
	}
	
	@Test
	public void testClean() {
		final Collection<Skill> skillList = service.findAll();
		assertTrue(skillList.isEmpty());
	}

	@Test
	public void update() {
		final Skill skillSave = new Skill("Liderança", "Breve descrição da habilidade");
		service.save(skillSave);
		
		final Skill skillUpdate = new Skill("força", "Breve descrição da habilidade");
		skillUpdate.setId(skillSave.getId());
		service.update(skillUpdate);
		
		assertEquals("força", service.findById(skillSave.getId()).getType());
		
	}

}

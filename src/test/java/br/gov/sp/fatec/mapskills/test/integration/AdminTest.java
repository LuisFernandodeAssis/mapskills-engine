/*
 * @(#)AdminTest.java 1.0 13/01/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.test.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;

import br.gov.sp.fatec.mapskills.authentication.DefaultGrantedAuthority;
import br.gov.sp.fatec.mapskills.authentication.PreAuthenticatedAuthentication;
import br.gov.sp.fatec.mapskills.authentication.jwt.JwtAuthenticationManager;
import br.gov.sp.fatec.mapskills.domain.institution.Course;
import br.gov.sp.fatec.mapskills.domain.institution.CoursePeriod;
import br.gov.sp.fatec.mapskills.domain.institution.Institution;
import br.gov.sp.fatec.mapskills.domain.institution.InstitutionService;
import br.gov.sp.fatec.mapskills.domain.skill.Skill;
import br.gov.sp.fatec.mapskills.domain.skill.SkillService;
import br.gov.sp.fatec.mapskills.domain.theme.GameTheme;
import br.gov.sp.fatec.mapskills.domain.theme.GameThemeService;
import br.gov.sp.fatec.mapskills.domain.user.Administrator;
import br.gov.sp.fatec.mapskills.domain.user.ProfileType;
import br.gov.sp.fatec.mapskills.restapi.wrapper.GameThemeListWrapper;
import br.gov.sp.fatec.mapskills.restapi.wrapper.InstitutionDetailsWrapper;
import br.gov.sp.fatec.mapskills.test.config.AbstractApplicationTest;
import br.gov.sp.fatec.mapskills.utils.JsonUtil;
/**
 * 
 * A classe {@link AdminTest} contem os testes de integracao
 * do perfil de administrador.
 *
 * @author Marcelo
 * @version 1.0 13/01/2017
 */
public class AdminTest extends AbstractApplicationTest {
	
	private static final String BASE_PATH = "/admin";
	
	@Mock
	protected JwtAuthenticationManager jwtAuthenticationManager;
	
	@Mock
	private JsonUtil jsonUtil;
	
	@Autowired
	private InstitutionService institutionService;
		
	@Autowired
	private SkillService skillService;
	
	@Autowired
	private GameThemeService themeService;
	
	@Before
	public void setuUp() {
		super.setUpContext();
		MockitoAnnotations.initMocks(this);
    	filter.setAuthenticationManager(jwtAuthenticationManager); 
	}
	
	@After
	public void cleanTables() {
		super.cleanTables(skillService);
		super.cleanTables(themeService);
		super.cleanTables(institutionService);
	}
	
	@Test
	public void getAllInstitutionsForbidden() throws Exception {
		mockAdminAuthentication();
		
		super.mockMvcPerformGet(BASE_PATH.concat("/institutions"))
			.andExpect(status().isForbidden());
	}
	
	@Test
	public void postSkill() throws Exception {
		mockAdminAuthentication();
		
		final Skill skill = Skill.builder().type("lideran�a").description("avalia...").build();
		final String bodyInput = objectMapper.writeValueAsString(skill);

		super.mockMvcPerformPost(BASE_PATH.concat("/skill"), bodyInput)
			.andExpect(status().isCreated());
		
		assertEquals(1, skillService.findAll().size());
	}
	
	@Test
	@WithMockUser(username="admin", authorities={"ADMINISTRATOR"})
	public void AuthenticationIsAdmin() {
		
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		final List<DefaultGrantedAuthority> authorities = new ArrayList<>(); 
		for(final GrantedAuthority authority : authentication.getAuthorities()) {
			authorities.add(new DefaultGrantedAuthority(ProfileType.valueOf(authority.getAuthority())));
		}
		assertTrue(authorities.get(0).isAdmin());
	}
	
	@Test
	public void uploadInstitutionFromExcel() throws Exception {
		mockAdminAuthentication();

		final String body = this.parseFileToJson("institution.xlsx");
		
		super.mockMvcPerformPost(BASE_PATH.concat("/upload/institutions"), body)
			.andExpect(status().isCreated());
		
		assertEquals(7, institutionService.findAllInstitutions().size());
	}
	
	@Test
	public void saveInstitution() throws Exception {
		super.cleanTables(institutionService);
		mockAdminAuthentication();
		
		final String bodyInput = objectMapper.writeValueAsString(getInstitutionClient());
				
		super.mockMvcPerformPost(BASE_PATH.concat("/institution"), bodyInput)
			.andExpect(status().isCreated());
		
		assertNotNull(institutionService.findInstitutionByCode("146"));
		assertEquals(1, institutionService.findInstitutionByCode("146").getMentors().size());
	}
	
	@Test
	public void findAllInstitution() throws Exception {
		mockAdminAuthentication();
		
		institutionService.saveInstitution(getOneInstitution());
		
		final String jsonResponse = super.mockMvcPerformWithMockHeaderGet(BASE_PATH.concat("/institutions"))
										.andReturn().getResponse().getContentAsString();
		
		final Object[] allInstitutionAsArray = objectMapper.readValue(jsonResponse, Object[].class);
		final Collection<Object> allInstitutions = new ArrayList<>(1);
		allInstitutions.addAll(Arrays.asList(allInstitutionAsArray));
		
		assertEquals(1, allInstitutions.size());
	}
	
	@Test
	public void findInstitutionDetailsByCode() throws Exception {
		mockAdminAuthentication();
		
		final Institution fatec = institutionService.saveInstitution(getOneInstitution());
		institutionService.saveCourse(Course.builder().code("100").name("manuten��o de aeronaves")
				.period(CoursePeriod.NOTURNO).institutionCode(fatec.getCode()).build());
		
		final String jsonResponse = super.mockMvcPerformWithMockHeaderGet(BASE_PATH.concat("/institution/" + fatec.getId()))
				.andReturn().getResponse().getContentAsString();
		
		final InstitutionDetailsWrapper institutionReturn = objectMapper.readValue(jsonResponse, InstitutionDetailsWrapper.class);
		
		assertEquals(fatec.getId(), institutionReturn.getInstitution().getId());
		assertEquals(1, institutionReturn.getInstitution().getMentors().size());
	}
	
	@Test
	public void saveGameTheme() throws Exception {
		mockAdminAuthentication();
		
		final GameTheme theme = GameTheme.builder().name("pizzaria").build();
		final String bodyInput = objectMapper.writeValueAsString(theme);
		
		super.mockMvcPerformPost(BASE_PATH.concat("/game/theme"), bodyInput)
			.andExpect(status().isCreated());
	}
	
	@Test
	public void updateTheme() throws Exception {
		mockAdminAuthentication();
		
		final List<GameTheme> themes = new ArrayList<>();
		themeService.save(getThemesMock());
		themes.addAll(themeService.findAllThemes());
		
		themes.get(0).enable();
		final String bodyInput = objectMapper.writeValueAsString(new GameThemeListWrapper(themes));
		
		super.mockMvcPerformWithMockHeaderPut(BASE_PATH.concat("/game/themes"), bodyInput)
			.andExpect(status().isOk());
		
		assertTrue(themeService.findById(themes.get(0).getId()).isActive());
	}
	
	@Test
	public void getReportByInstitution() throws Exception {
		mockAdminAuthentication();
		for(final Skill skill : super.getSkillsMock()) {
			skillService.save(skill);
		}
		
		super.mockMvcPerformWithMockHeaderGet(BASE_PATH.concat("/report/146"));
	}
	
	@Test
	public void updateInstitutionFromExcel() throws Exception {
		mockAdminAuthentication();
		
		final String bodyOriginal = this.parseFileToJson("institution.xlsx");
		super.mockMvcPerformPost(BASE_PATH.concat("/upload/institutions"), bodyOriginal)
			.andExpect(status().isCreated());
				
		final String bodyUpdate = this.parseFileToJson("institutionUpdate.xlsx");
		super.mockMvcPerformPost(BASE_PATH.concat("/upload/institutions"), bodyUpdate)
			.andExpect(status().isCreated());
		
		final Institution institution = institutionService.findInstitutionByCode("148");
		assertEquals("Nome Atualizado", institution.getMentors().iterator().next().getName());
		
	}
	
	private void mockAdminAuthentication() {
		when(jwtAuthenticationManager.authenticate(Mockito.any(Authentication.class)))
			.thenReturn(getAdminMock());
	}
	
	private Authentication getAdminMock() {
		return new PreAuthenticatedAuthentication(new Administrator("admin", "admin@cps.sp.gov.br", "admin"));
	}
	
	private Collection<GameTheme> getThemesMock() {
		final Collection<GameTheme> themeCollection = new ArrayList<>();
		themeCollection.add(GameTheme.builder().name("pizzaria").build());
		themeCollection.add(GameTheme.builder().name("gravadora").build());
		themeCollection.add(GameTheme.builder().name("museu").build());
		return themeCollection;
	}

}

/*
 * @(#)AdminController.java 1.0 03/01/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.restapi;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.gov.sp.fatec.mapskills.domain.MapSkillsException;
import br.gov.sp.fatec.mapskills.domain.institution.Institution;
import br.gov.sp.fatec.mapskills.domain.institution.InstitutionService;
import br.gov.sp.fatec.mapskills.domain.scene.Scene;
import br.gov.sp.fatec.mapskills.domain.scene.SceneService;
import br.gov.sp.fatec.mapskills.domain.skill.SkillService;
import br.gov.sp.fatec.mapskills.domain.theme.GameThemeService;
import br.gov.sp.fatec.mapskills.infra.SaveImageService;
import br.gov.sp.fatec.mapskills.restapi.wrapper.GameThemeListWrapper;
import br.gov.sp.fatec.mapskills.restapi.wrapper.GameThemeWrapper;
import br.gov.sp.fatec.mapskills.restapi.wrapper.InputStreamWrapper;
import br.gov.sp.fatec.mapskills.restapi.wrapper.InstitutionDetailsWrapper;
import br.gov.sp.fatec.mapskills.restapi.wrapper.InstitutionListWrapper;
import br.gov.sp.fatec.mapskills.restapi.wrapper.SceneListWrapper;
import br.gov.sp.fatec.mapskills.restapi.wrapper.SceneWrapper;
import br.gov.sp.fatec.mapskills.restapi.wrapper.SkillListWrapper;
import br.gov.sp.fatec.mapskills.restapi.wrapper.SkillWrapper;
import br.gov.sp.fatec.mapskills.restapi.wrapper.StudentsProgressGlobalWrapper;
import br.gov.sp.fatec.mapskills.restapi.wrapper.StudentsProgressLevelWrapper;
import lombok.AllArgsConstructor;
/**
 * 
 * A classe {@link AdministratorController} eh responsavel por conter todos
 * end-points de servico do perfil administrador da aplicacao.
 *
 * @author Marcelo
 * @version 1.0 03/01/2017
 */
@RestController
@RequestMapping(AdministratorController.BASE_PATH)
@AllArgsConstructor
public class AdministratorController {
	
	protected static final String BASE_PATH = "/admin";
	
	private final SkillService skillService;
	private final GameThemeService themeService;
	private final SceneService sceneService;
	private final InstitutionService institutionService;
	private final SaveImageService imageService;
	
	/**
	 * Metodo que realiza a persistencia de lista de instituicoes por meio de um arquivo
	 * excel .(xlsx) feito pelo perfil <code>ADMINISTRATOR</code>
	 * @param inputStreamWrapper
	 * @return lista de instituicoes cadastradas
	 * @throws MapSkillsException 
	 */
	@RequestMapping(value = "/upload/institutions", method = RequestMethod.POST)
	public ResponseEntity<InstitutionListWrapper> importInstitutions(@RequestBody final InputStreamWrapper inputStreamWrapper) throws MapSkillsException {		
		final List<Institution> institutionsSaved = institutionService.saveInstituionFromExcel(inputStreamWrapper.getInputStream());		
		final InstitutionListWrapper wrapper = new InstitutionListWrapper(institutionsSaved);
		return new ResponseEntity<>(wrapper, HttpStatus.CREATED);
	}
	
	/**
	 * Metodo que realiza a persistencia de uma unica instituicao
	 * realizado pelo perfil <code>ADMINISTRATOR</code>
	 * @param inputStreamWrapper
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/institution", method = RequestMethod.POST)
	public ResponseEntity<InstitutionDetailsWrapper> saveInstitution(@RequestBody final InstitutionDetailsWrapper institutionWrapper) {
		final Institution institution = institutionService.saveInstitution(institutionWrapper.getInstitution());
		final InstitutionDetailsWrapper institutionCreated = new InstitutionDetailsWrapper(institution);
		return new ResponseEntity<>(institutionCreated, HttpStatus.CREATED);
	}
	
	/**
	 * Metodo que recuepra todas instituicoes cadastradas na aplicacao
	 * realizado pelo perfil <code>ADMINISTRATOR</code>
	 * @return
	 */
	@RequestMapping(value = "/institutions", method = RequestMethod.GET)
	public ResponseEntity<InstitutionListWrapper> getAllInstitution() {
		final InstitutionListWrapper institutions = new InstitutionListWrapper(institutionService.findAllInstitutions());
		return new ResponseEntity<>(institutions, HttpStatus.OK);
	}
	
	/**
	 * Metodo que recupera um instituicao em detalhes
	 * @param institutionId
	 * @return
	 * @throws MapSkillsException 
	 */
	@RequestMapping(value = "/institution/{institutionId}", method = RequestMethod.GET)
	public ResponseEntity<InstitutionDetailsWrapper> getInstitutionDetail(@PathVariable("institutionId") final long institutionId) throws MapSkillsException {
		final Institution institution = institutionService.findInstitutionDetailsById(institutionId);
		final InstitutionDetailsWrapper institutionDetail = new InstitutionDetailsWrapper(institution);
		return new ResponseEntity<>(institutionDetail, HttpStatus.OK);
	}
	
	/**
	 * Metodo que realiza a persistencia de um tema na aplicacao feito
	 * pelo perfil <code>ADMINISTRATOR</code>
	 * @param themeWrapper
	 * @return
	 */
	@RequestMapping(value = "/game/theme", method = RequestMethod.POST)
	public ResponseEntity<GameThemeWrapper> saveTheme(@RequestBody final GameThemeWrapper themeWrapper) {
		final GameThemeWrapper wrapper = new GameThemeWrapper(themeService.save(themeWrapper.getGameTheme()));
		return new ResponseEntity<>(wrapper, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/game/themes", method = RequestMethod.PUT)
	public ResponseEntity<GameThemeListWrapper> updateThemes(@RequestBody final GameThemeListWrapper themeWrapper) {
		final GameThemeListWrapper wrapper = new GameThemeListWrapper(themeService.save(themeWrapper.getGameThemes()));
		return new ResponseEntity<>(wrapper, HttpStatus.OK);
	}
	
	/***
	 * Metodo que retorna todos temas cadastrados na aplicacao
	 * @return
	 */
	@RequestMapping(value = "/game/themes", method = RequestMethod.GET)
	public ResponseEntity<GameThemeListWrapper> getAllThemes() {
		final GameThemeListWrapper gameThemes = new GameThemeListWrapper(themeService.findAllThemes()); 
		return new ResponseEntity<>(gameThemes, HttpStatus.OK);
	}
	
	/**
	 * Metodo que salva uma cena de um tema do jogo na aplicacao
	 * @param sceneWrapper
	 * @return
	 * @throws MapSkillsException 
	 */
	@RequestMapping(value = "/game/scene", method = RequestMethod.POST)
	public ResponseEntity<HttpStatus> saveScene(@RequestBody final SceneWrapper sceneWrapper) throws MapSkillsException {
		imageService.save(sceneWrapper.getBase64(), sceneWrapper.getFileName());
		sceneService.save(sceneWrapper.getScene());
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	/**
	 * Realiza atualizacao de uma lista de cenas, (i.e. a ordem de exibicao)
	 * @param sceneListWrapper
	 * @return
	 */
	@RequestMapping(value = "/game/scenes", method = RequestMethod.PUT)
	public ResponseEntity<HttpStatus> updateIndexScenes(@RequestBody final SceneListWrapper sceneListWrapper) {
		sceneService.updateIndex(sceneListWrapper.getScenes());
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	/**
	 * Metodo que retorna todas as cenas de um tema da aplicacao
	 * @param themeId
	 * @return
	 */
	@RequestMapping(value = "/game/theme/{themeId}", method = RequestMethod.GET)
	public ResponseEntity<SceneListWrapper> getAllScenesByThemeId(@PathVariable("themeId") final long themeId) {
		final SceneListWrapper scenesListWrapper = new SceneListWrapper(themeService.findAllScenesByThemeId(themeId));
		return new ResponseEntity<>(scenesListWrapper, HttpStatus.OK);
	}
	
	/**
	 * Salva uma nova competência na aplicacao
	 * @param skillWrapper
	 * @return
	 */
	@RequestMapping(value = "/skill", method = RequestMethod.POST)
	public ResponseEntity<HttpStatus> saveSkill(@RequestBody final SkillWrapper skillWrapper) {
		skillService.save(skillWrapper.getSkill());
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	/**
	 * Retorna uma array serializado com todas competencias cadastradas na aplicacao.
	 * @return
	 */
	@RequestMapping(value = "/skills", method = RequestMethod.GET)
	public ResponseEntity<SkillListWrapper> getAllSkills() {
		final SkillListWrapper gameThemes = new SkillListWrapper(skillService.findAll()); 
		return new ResponseEntity<>(gameThemes, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/scene/question/{sceneId}", method = RequestMethod.DELETE)
	public ResponseEntity<HttpStatus> deleteQuestion(@PathVariable("sceneId") final long sceneId) {
		final Scene scene = sceneService.findById(sceneId);
		scene.deleteQuestion();
		sceneService.save(scene);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@RequestMapping(value = "/scene/{sceneId}", method = RequestMethod.DELETE)
	public ResponseEntity<HttpStatus> deleteScene(@PathVariable("sceneId") final long sceneId) {
		sceneService.delete(sceneId);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@RequestMapping(value = "/dashboard/global", method = RequestMethod.GET)
	public ResponseEntity<StudentsProgressGlobalWrapper> getGlobalStudentsProgress() {
		final List<Object[]> resultSet = institutionService.getGlobalPogress();
		final StudentsProgressGlobalWrapper progress = new StudentsProgressGlobalWrapper(resultSet);
		return new ResponseEntity<>(progress, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/dashboard/{level}", method = RequestMethod.GET)
	public ResponseEntity<StudentsProgressLevelWrapper> getLevelStudentsProgress(
			@PathVariable("level") final String level) {
		
		final List<Object[]> resultSet = institutionService.getLevelPogress(level);
		final StudentsProgressLevelWrapper progress = new StudentsProgressLevelWrapper(resultSet);
		return new ResponseEntity<>(progress, HttpStatus.OK);
	}

}

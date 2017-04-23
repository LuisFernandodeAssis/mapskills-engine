/*
 * @(#)SetupApplicationToInitializeGame.java 1.0 20/01/2017
 *
 * Copyright (c) 2016, Fatec Jessen Vidal. All rights reserved. Fatec Jessen Vidal
 * proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.config;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.gov.sp.fatec.mapskills.application.MapSkillsException;
import br.gov.sp.fatec.mapskills.domain.institution.Course;
import br.gov.sp.fatec.mapskills.domain.institution.CoursePeriod;
import br.gov.sp.fatec.mapskills.domain.institution.Institution;
import br.gov.sp.fatec.mapskills.domain.institution.InstitutionLevel;
import br.gov.sp.fatec.mapskills.domain.institution.InstitutionService;
import br.gov.sp.fatec.mapskills.domain.scene.Alternative;
import br.gov.sp.fatec.mapskills.domain.scene.Question;
import br.gov.sp.fatec.mapskills.domain.scene.Scene;
import br.gov.sp.fatec.mapskills.domain.scene.SceneService;
import br.gov.sp.fatec.mapskills.domain.skill.Skill;
import br.gov.sp.fatec.mapskills.domain.skill.SkillRepository;
import br.gov.sp.fatec.mapskills.domain.theme.GameTheme;
import br.gov.sp.fatec.mapskills.domain.theme.GameThemeService;
import br.gov.sp.fatec.mapskills.domain.user.Administrator;
import br.gov.sp.fatec.mapskills.domain.user.UserService;
import br.gov.sp.fatec.mapskills.domain.user.mentor.Mentor;
import br.gov.sp.fatec.mapskills.domain.user.student.AcademicRegistry;
import br.gov.sp.fatec.mapskills.domain.user.student.Student;
import br.gov.sp.fatec.mapskills.utils.BeanRetriever;

//@Configuration
public class SetupApplicationToInitializeGame {
	
	private static final Logger LOGGER = Logger.getLogger(SetupApplicationToInitializeGame.class.getName());
	
	private static final String PATH_TXT = "d:/temp/arquivosTexto/";
	private static final String URL_SERVER = "http://127.0.0.1:8585/mapskills/images/";
	private static final long GAME_THEME_ID = 1;
	private static final String INSTITUTION_CODE = "146";
	
	private final Map<Integer, Question> mapQuestion = new HashMap<>(26);
	private final Map<Integer, Collection<Alternative>> mapAlternatives = new HashMap<>(26);
	final List<String> textList = new LinkedList<>();
	
	private SkillRepository skillRepository = BeanRetriever.getBean("skillRepository", SkillRepository.class);
	private SceneService sceneService = BeanRetriever.getBean("sceneService", SceneService.class);
	private GameThemeService themeService = BeanRetriever.getBean("gameThemeService", GameThemeService.class);
	private InstitutionService institutionService = BeanRetriever.getBean("institutionService", InstitutionService.class);
	private UserService userService = BeanRetriever.getBean("userService", UserService.class);
	
	public SetupApplicationToInitializeGame() throws IOException, MapSkillsException {
		this.createAdmin();
		this.createInstitution();
		this.createCourses();
		this.creatStudent();
		this.createGameTheme();
		this.createSkills();
		this.buildTextFromFile();
		this.generateAlternatives();
		this.generateQuestions();
		this.createScenesFromFile();
	}
	
	private void createAdmin() {
		userService.save(new Administrator("AdministradorCPS", "admin@cps.sp.gov.br", "$2a$10$q9jtl3BMIBxnpPZJm1hy5.7xDwKgUlfL93c0CbrzagZZUpyfRncVC"));
		LOGGER.log(Level.INFO, "=== ADMINISTATOR SAVE SUCCESS ===");
	}
	/**
	 * cria uma nova institui��o persistindo-a na base de dados
	 */
	private void createInstitution() {
		final Institution fatec = new Institution(INSTITUTION_CODE, "56381708000194", "Jessen Vidal", InstitutionLevel.SUPERIOR,"S�o Jos�");
		fatec.addMentor(new Mentor("Mentor", INSTITUTION_CODE, "mentor@fatec.sp.gov.br", "$2a$10$wEaMddZtyZp5Kkj/MpObjeCkYNoPFdoNwMKzxLuD7KjCyB63kf6Yy"));
		institutionService.saveInstitution(fatec);
		LOGGER.log(Level.INFO, "=== INSTITUTION SAVE SUCCESS ===");
	}
	/**
	 * adiciona cursos a institui��o
	 */
	private void createCourses() {
		institutionService.saveCourse(Course.builder().code("048").name("Tecnologia em An�lise e Desenvolvimento de Sistemas").period(CoursePeriod.NOTURNO).institutionCode(INSTITUTION_CODE).build());
		institutionService.saveCourse(Course.builder().code("114").name("Tecnologia em Automa��o Manufatura Digital").period(CoursePeriod.MATUTINO).institutionCode(INSTITUTION_CODE).build());
		institutionService.saveCourse(Course.builder().code("028").name("Tecnologia em Banco de Dados").period(CoursePeriod.NOTURNO).institutionCode(INSTITUTION_CODE).build());
		institutionService.saveCourse(Course.builder().code("077").name("Tecnologia em Gest�o da Produ��o Industrial").period(CoursePeriod.NOTURNO).institutionCode(INSTITUTION_CODE).build());
		institutionService.saveCourse(Course.builder().code("064").name("Tecnologia em Gest�o Empresarial").period(CoursePeriod.EaD).institutionCode(INSTITUTION_CODE).build());
		institutionService.saveCourse(Course.builder().code("074").name("Tecnologia em Log�stica").period(CoursePeriod.NOTURNO).institutionCode(INSTITUTION_CODE).build());
		institutionService.saveCourse(Course.builder().code("068").name("Tecnologia em Manuten��o de Aeronaves").period(CoursePeriod.NOTURNO).institutionCode(INSTITUTION_CODE).build());
		institutionService.saveCourse(Course.builder().code("115").name("Tecnologia em Projetos de Estruturas Aeron�uticas").period(CoursePeriod.NOTURNO).institutionCode(INSTITUTION_CODE).build());
		LOGGER.log(Level.INFO, "=== COURSES SAVE SUCCESS ===");
	}
	
	private void creatStudent() throws MapSkillsException {
		institutionService.saveStudent(new Student(new AcademicRegistry(INSTITUTION_CODE+"0481713000", INSTITUTION_CODE, "048"), "Student User", "1289003400", "aluno@fatec.sp.gov.br", "$2a$10$MfkKiDmLJohCjQ45Kb7vnOAeALBR1SV0OTqkkB6IfcMDA87iOrgmG"));
		LOGGER.log(Level.INFO, "=== STUDENT SAVE SUCCESS ===");
	}
	/**
	 * cria um tema e persiste-a na base de dados
	 */
	private void createGameTheme() {
		themeService.save(GameTheme.builder().name("GER�NCIA DE PIZZARIA").build());
		LOGGER.log(Level.INFO, "=== THEMES SAVE SUCCESS ===");
	}
	/**
	 * cria uma carga inicial das cenas persistindo-as na base de dados,
	 * h� uma sequencia de quest�es entre as cenas
	 * (i.e. cena 1 n�o tem quest�o, cena 2 tem quest�o e cena 3 n�o tem quest�o)
	 * @throws IOException caso ocorra um problema de leitura do arquivo
	 * 			texto com os nomes das imagens de cada cena (i.e. scene01.jpg)
	 */
	private void createScenesFromFile() throws IOException {
		final String filePath = PATH_TXT.concat("sequenciaImagensCenasTemaPizzaria.txt");
		
		int idQuestion = 1;
		int imageIndex = 0;
		for(final String line : this.buildReaderFromFile(filePath)) {
			if(imageIndex % 3 == 1) {
				sceneService.save(Scene.builder().text(textList.get(imageIndex++)).urlBackground(URL_SERVER.concat(line))
						.question(mapQuestion.get(idQuestion++)).gameThemeId(GAME_THEME_ID).build());
				continue;
			}
			sceneService.save(Scene.builder().text(textList.get(imageIndex++)).urlBackground(URL_SERVER.concat(line))
					.question(null).gameThemeId(GAME_THEME_ID).build());
		}
		LOGGER.log(Level.INFO, "=== SCENES SAVE SUCCESS ===");
	}
	
	/**
	 * cria competencias persistindo-as na base de dados
	 */
	private void createSkills() {
		skillRepository.save(Skill.builder().type("Vis�o do futuro").description("Capacidade de antecipar barreira e tendencias identificando oportunidade mais criativas e beneficas para a organiza��o ou para a adversidade.").build());
		skillRepository.save(Skill.builder().type("Comunica��o").description("Capacidade de transmitir e expressar ideias, pensamentos, emo��es e informa��es de forma clara e objetiva, de modo a garantir sua compreens�o sem distor��es ou ru�dos.").build());
		skillRepository.save(Skill.builder().type("Gest�o do tempo").description("Capacidade de gerenciar adequandamente a execu��o das tarefas, planejando e organizando recursos, em conformidade com o prazo acordado.").build());
		skillRepository.save(Skill.builder().type("Equilibrio emocional").description("Qualidade no dominio das emo��es e de adequa��o a resposta emocional a estimulos internos e externos.").build());
		skillRepository.save(Skill.builder().type("Trabalho em equipe").description("Busca construir relacionamentos assertivos para com os outros, respeitando as necessidades e contribui��es dos demais, levando os integrantes a alcan�arem positivamente os resultados estabelecidos.").build());
		skillRepository.save(Skill.builder().type("Resili�ncia").description("Capacidade de lidar com problemas, superar obstaculos ou resistir a press�o de situa��es adversas dando condi��o para enfrentar a alcan�ar os objetivos esperados.").build());
		LOGGER.log(Level.INFO, "=== SKILLS SAVE SUCCESS ===");
	}
	/**
	 * popula mapa de quest�es, sendo a lista de alternativas recuperada
	 * do mapa de alternativas populada anteriormente.
	 * h� cenas que n�o possuem quest�o.
	 */
	private void generateQuestions() {
		final Random gerador = new Random();
		for(int i = 1; i < 26; i++) {
			mapQuestion.put(i, Question.builder().alternatives(mapAlternatives.get(i)).skillId(gerador.nextInt(6) + 1).build());
		}
	}
	
	/**
	 * popula a lista de textos, realizando leitura do arquivo contendo
	 * os texto das cenas.
	 * @throws IOException
	 */
	private void buildTextFromFile() throws IOException {
		final String filePath = PATH_TXT.concat("textosTemaPizzaria.txt");
		this.textList.addAll(this.buildReaderFromFile(filePath));
	}
	/**
	 * realizando leitura do arquivo contendo as alternativas de cada quest�o e
	 * popula o mapa de int/List<Alternatives>
	 * gerando o valor do peso da alternativa aleatoriamente entre 0 e 6
	 * @throws IOException caso haja algum problema I/O
	 * 
	 */
	private void generateAlternatives() throws IOException {
		this.mapAlternatives.clear();
		final String filePath = PATH_TXT.concat("alternativasTemaPizzaria.txt");
		
		int idQuestion = 1;

		final List<String> list = new ArrayList<>();
		list.addAll(this.buildReaderFromFile(filePath));
		final int sizeList = list.size();
		for(int i = 0; i < sizeList; i++) {
			final List<Alternative> alternatives = new LinkedList<>();
			for(int j = 0; j < 4; j++) {
				final String[] lineWithValue = list.get(i++).split(";");
				alternatives.add(Alternative.builder()
						.description((lineWithValue[0]))
						.skillValue(Integer.valueOf(lineWithValue[1]))
						.build());
			}
			mapAlternatives.put(idQuestion++, alternatives);
		}
	}
	/**
	 * cria uma lista de string, apartir do caminho de um arquivo txt.
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	private Collection<String> buildReaderFromFile(final String filePath) throws IOException {
		final BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
		final Collection<String> lineList = new ArrayList<>();
		String lineTmp;
		while((lineTmp = reader.readLine()) != null) {
			lineList.add(lineTmp);
		}
		reader.close();
		return Collections.unmodifiableCollection(lineList);
	}
	
}

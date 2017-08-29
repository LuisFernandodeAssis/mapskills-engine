/*
 * @(#)SetupApplicationToInitializeGame.java 1.0 20/01/2017
 *
 * Copyright (c) 2016, Fatec Jessen Vidal. All rights reserved.
 * Fatec Jessen Vidal proprietary/confidential. Use is subject to license terms.
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

import org.springframework.beans.factory.annotation.Value;

import br.gov.sp.fatec.mapskills.domain.MapSkillsException;
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
import br.gov.sp.fatec.mapskills.domain.user.student.Student;
import br.gov.sp.fatec.mapskills.utils.ApplicationContextHolder;
/**
 * 
 * A classe {@link SetupApplicationToInitializeGame} utilizada para
 * automatizar a primeira carga de informacoes na base de dados da
 * aplicacao, estando as informacoes em arquivos de texto.
 *
 * @author Marcelo
 * @version 1.0 20/01/2017
 */
//@Configuration
public class SetupApplicationToInitializeGame {
	
	private static final Logger LOGGER = Logger.getLogger(SetupApplicationToInitializeGame.class.getName());
	
	private static final String PATH_TXT = "d:/temp/arquivosTexto/";
	@Value("${app.setup.images.ip}")
	private static String urServer;
	private static final long GAME_THEME_ID = 1;
	private static final String INSTITUTION_CODE = "146";
	
	private final Map<Integer, Question> mapQuestion = new HashMap<>(26);
	private final Map<Integer, List<Alternative>> mapAlternatives = new HashMap<>(26);
	final List<String> textList = new LinkedList<>();
	
	private SkillRepository skillRepository = ApplicationContextHolder.getBean("skillRepository", SkillRepository.class);
	private SceneService sceneService = ApplicationContextHolder.getBean("sceneService", SceneService.class);
	private GameThemeService themeService = ApplicationContextHolder.getBean("gameThemeService", GameThemeService.class);
	private InstitutionService institutionService = ApplicationContextHolder.getBean("institutionService", InstitutionService.class);
	private UserService userService = ApplicationContextHolder.getBean("userService", UserService.class);
	
	public SetupApplicationToInitializeGame() throws IOException, MapSkillsException {
		this.createAdmin();
		this.createInstitution();
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
	 * cria uma nova instituição persistindo-a na base de dados
	 */
	private void createInstitution() {
		final Institution fatec = new Institution(INSTITUTION_CODE, "56381708000194", "Jessen Vidal", InstitutionLevel.SUPERIOR, "São José", null, null);
		fatec.addMentor(new Mentor("Mentor", "mentor@fatec.sp.gov.br", "$2a$10$wEaMddZtyZp5Kkj/MpObjeCkYNoPFdoNwMKzxLuD7KjCyB63kf6Yy", fatec));
		fatec.setCourses(createCourses(fatec));
		institutionService.saveInstitution(fatec);
		LOGGER.log(Level.INFO, "=== INSTITUTION SAVE SUCCESS ===");
	}
	/**
	 * adiciona cursos a instituição
	 */
	private List<Course> createCourses(final Institution fatec) {
		final List<Course> courses = new LinkedList<>();
		courses.add(new Course("048", "Tecnologia em Análise e Desenvolvimento de Sistemas", CoursePeriod.NOTURNO, fatec));
		courses.add(new Course("114", "Tecnologia em Automação Manufatura Digital", CoursePeriod.MATUTINO, fatec));
		courses.add(new Course("028", "Tecnologia em Banco de Dados", CoursePeriod.NOTURNO, fatec));
		courses.add(new Course("077", "Tecnologia em Gestão da Produção Industrial", CoursePeriod.NOTURNO, fatec));
		courses.add(new Course("064", "Tecnologia em Gestão Empresarial", CoursePeriod.EAD, fatec));
		courses.add(new Course("074", "Tecnologia em Logística", CoursePeriod.NOTURNO, fatec));
		courses.add(new Course("068", "Tecnologia em Manutenção de Aeronaves", CoursePeriod.NOTURNO, fatec));
		courses.add(new Course("115", "Tecnologia em Projetos de Estruturas Aeronáuticas", CoursePeriod.NOTURNO, fatec));
		LOGGER.log(Level.INFO, "=== COURSES SAVE SUCCESS ===");
		return courses;
	}
	
	private void creatStudent() throws MapSkillsException {
		institutionService.saveStudent(new Student(INSTITUTION_CODE+"0481713000", "Student User", "1289003400", "aluno@fatec.sp.gov.br", "$2a$10$MfkKiDmLJohCjQ45Kb7vnOAeALBR1SV0OTqkkB6IfcMDA87iOrgmG"));
		LOGGER.log(Level.INFO, "=== STUDENT SAVE SUCCESS ===");
	}
	/**
	 * cria um tema e persiste-a na base de dados
	 */
	private void createGameTheme() {
		themeService.save(new GameTheme("GERÊNCIA DE PIZZARIA"));
		LOGGER.log(Level.INFO, "=== THEMES SAVE SUCCESS ===");
	}
	/**
	 * cria uma carga inicial das cenas persistindo-as na base de dados,
	 * há uma sequencia de questões entre as cenas
	 * (i.e. cena 1 não tem questão, cena 2 tem questão e cena 3 não tem questão)
	 * @throws IOException caso ocorra um problema de leitura do arquivo
	 * 			texto com os nomes das imagens de cada cena (i.e. scene01.jpg)
	 */
	private void createScenesFromFile() throws IOException {
		final String filePath = PATH_TXT.concat("sequenciaImagensCenasTemaPizzaria.txt");
		
		int idQuestion = 1;
		int imageIndex = 0;
		for(final String line : this.buildReaderFromFile(filePath)) {
			if(imageIndex % 3 == 1) {
				sceneService.save(new Scene(null, null, textList.get(imageIndex++), urServer.concat(line),
						mapQuestion.get(idQuestion++), GAME_THEME_ID));
				continue;
			}
			sceneService.save(new Scene(null, null, textList.get(imageIndex++), urServer.concat(line), null, GAME_THEME_ID));
		}
		LOGGER.log(Level.INFO, "=== SCENES SAVE SUCCESS ===");
	}
	
	/**
	 * cria competencias persistindo-as na base de dados
	 */
	private void createSkills() {
		skillRepository.save(new Skill("Visão do futuro", "Capacidade de antecipar barreira e tendencias identificando oportunidade mais criativas e beneficas para a organização ou para a adversidade."));
		skillRepository.save(new Skill("Comunicação", "Capacidade de transmitir e expressar ideias, pensamentos, emoções e informações de forma clara e objetiva, de modo a garantir sua compreensão sem distorções ou ruídos."));
		skillRepository.save(new Skill("Gestão do tempo", "Capacidade de gerenciar adequandamente a execução das tarefas, planejando e organizando recursos, em conformidade com o prazo acordado."));
		skillRepository.save(new Skill("Equilibrio emocional", "Qualidade no dominio das emoções e de adequação a resposta emocional a estimulos internos e externos."));
		skillRepository.save(new Skill("Trabalho em equipe", "Busca construir relacionamentos assertivos para com os outros, respeitando as necessidades e contribuições dos demais, levando os integrantes a alcançarem positivamente os resultados estabelecidos."));
		skillRepository.save(new Skill("Resiliência", "Capacidade de lidar com problemas, superar obstaculos ou resistir a pressão de situações adversas dando condição para enfrentar a alcançar os objetivos esperados."));
		LOGGER.log(Level.INFO, "=== SKILLS SAVE SUCCESS ===");
	}
	/**
	 * popula mapa de questões, sendo a lista de alternativas recuperada
	 * do mapa de alternativas populada anteriormente.
	 * há cenas que não possuem questão.
	 */
	private void generateQuestions() {
		final Random gerator = new Random();
		for(int index = 1; index < 26; index++) {
			final long currentSkillsId = gerator.nextInt(6) + 1L;
			mapQuestion.put(index, new Question(null, mapAlternatives.get(index), currentSkillsId));
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
	 * realizando leitura do arquivo contendo as alternativas de cada questão e
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
				alternatives.add(new Alternative(null, lineWithValue[0], Integer.valueOf(lineWithValue[1])));
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

### MAP SKILLS - FACULDADE DE TECNOLOGIA PROF º JESSEN VIDAL

##### Projeto de avaliação de competência de alunos que fazem parte das unidades de ensino Centro Paula Souza.

##### CONFIGURAÇÃO

Banco de Dados : [MySql](https://www.mysql.com "MySql")
- Schema : mapskills
- Usuário : mapskills
- Senha : mapskills

----

Gerenciador de dependencia : [Maven](https://maven.apache.org "Maven")
- `mvn clean install -P local`

----

Qualidade Contínua : [SonarQube](https://www.sonarqube.org "SonarQube")
- `mvn clean org.jacoco:jacoco-maven-plugin:prepare-agent install -Dmaven.test.failure.ignore=true`
- `mvn sonar:sonar`

----

Cobertura de Testes : [Cobertura](http://cobertura.github.io/cobertura "Cobertura")
- `mvn cobertura:cobertura -P local`

----

Versionador de Banco de Dados : [Liquibase](http://www.liquibase.org "Liquibase")
- `mvn liquibase:update -P local`

----

Documentação da API : [Swagger](https://swagger.io/ "Swagger")
- URL : host:port/swagger-ui.html

----

Configuração Eclipse : [Eclipse](http://www.eclipse.org/ "Eclipse")
> Run > Run Configurations... > Java Applications > "Selecione sua Main" > Aba Arguments > Campo "VM arguments" coloque o parâmetro : 
-Dspring.datasource.url=jdbc:mysql://127.0.0.1:3306/mapskills

Executar Projeto
- `mvn spring-boot:run`

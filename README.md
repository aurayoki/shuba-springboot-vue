# shuba-springboot-vue CRM for buyer

[![Deployed on Heroku](https://img.shields.io/badge/heroku-deployed-blueviolet.svg?logo=heroku)](https://shuba.herokuapp.com/)
[![versionspringboot](https://img.shields.io/badge/dynamic/xml?color=brightgreen&url=https://raw.githubusercontent.com/770001/shuba-springboot-vue/master/pom.xml&query=%2F%2A%5Blocal-name%28%29%3D%27project%27%5D%2F%2A%5Blocal-name%28%29%3D%27parent%27%5D%2F%2A%5Blocal-name%28%29%3D%27version%27%5D&label=springboot)](https://github.com/spring-projects/spring-boot)
[![versionjava](https://img.shields.io/badge/jdk-8-brightgreen.svg?logo=java)](https://github.com/spring-projects/spring-boot)
[![versionvuejs](https://img.shields.io/badge/dynamic/json?color=brightgreen&url=https://raw.githubusercontent.com/770001/shuba-springboot-vue/master/frontend/package.json&query=$.dependencies.vue&label=vue&logo=vue.js)](https://vuejs.org/)
[![versionnodejs](https://img.shields.io/badge/dynamic/xml?color=brightgreen&url=https://raw.githubusercontent.com/770001/shuba-springboot-vue/master/frontend/pom.xml&query=%2F%2A%5Blocal-name%28%29%3D%27project%27%5D%2F%2A%5Blocal-name%28%29%3D%27build%27%5D%2F%2A%5Blocal-name%28%29%3D%27plugins%27%5D%2F%2A%5Blocal-name%28%29%3D%27plugin%27%5D%2F%2A%5Blocal-name%28%29%3D%27executions%27%5D%2F%2A%5Blocal-name%28%29%3D%27execution%27%5D%2F%2A%5Blocal-name%28%29%3D%27configuration%27%5D%2F%2A%5Blocal-name%28%29%3D%27nodeVersion%27%5D&label=nodejs&logo=node.js)](https://nodejs.org/en/)
[![versionaxios](https://img.shields.io/badge/dynamic/json?color=brightgreen&url=https://raw.githubusercontent.com/770001/shuba-springboot-vue/master/frontend/package.json&query=$.dependencies.axios&label=axios)](https://github.com/axios/axios)

## Доступно на [shuba.herokuapp.com](https://shuba.herokuapp.com)

## Собрано по туториалу [https://github.com/jonashackt/spring-boot-vuejs](https://github.com/jonashackt/spring-boot-vuejs) - обязательно к прочтению!

> Цель проекта - помощь байеру в планировании поездок, закупок, доставки, ведение статистики по заказам, клиентам, 
> расчете доходов и расходов и пр. В дальнейшем возможно подключение Telegram-бота

## Оглавление:
* [Структура](#Структура)
* [Docker](#Docker)
* [Backend](#Backend)
* [Frontend](#Frontend)
* [Собираем проект](#Собираем-проект)
* [Деплой](#Деплой)

### Структура 
   
Общая структура проекта: модуль `frontend`, модуль `backend`. В корне проекта и в модулях - по `pom.xml`

```text
shuba-springboot-vue
│
├─┬ backend     → модуль backend со `Spring Boot`
│ ├── src
│ └── pom.xml   свой pom.xml
│
├─┬ frontend    → модуль frontend с `Vue.js`
│ ├── src
│ └── pom.xml   свой pom.xml
│
└── pom.xml     → parent pom.xml - управляет модулями
```

### Docker

Для подключения базы данных будем юзать `Docker Compose`.

Скачиваем [Docker QuickTerminal](https://github.com/docker/toolbox/releases/)

Создаем `docker-compose.yml`
```yaml
version: '2'
services:
  postgres-buyer:
    image: postgres:11.5
    environment:
      - POSTGRES_USER=your_user
      - POSTGRES_PASSWORD=your_password
      - POSTGRES_DB=your_database_name
    ports:
      - 5432:5432
```

Создаем `down_up.bat`
```
docker-compose down
docker-compose up -d
```

Запускаем `Docker QuickTerminal`, запускаем `down_up.bat`

### Backend

Генерим проект на [Spring Initializr](https://start.spring.io/) (зависимости см. в основном туториале)

Шарим на `github.com` - создаем репозиторий.

Создаем директорию `backend` и переносим туда директорию `src`

Мой `pom.xml` для модуля `backend`:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <artifactId>backend</artifactId>
    <packaging>jar</packaging>

    <parent>
        <groupId>com.khilkevichigor</groupId>
        <artifactId>shuba-springboot-vue</artifactId>
        <version>0.0.1-SNAPSHOT</version>
    </parent>

    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
        <java.version>1.8</java.version>

        <!-- backend dependencies -->
        <rest-assured.version>4.3.3</rest-assured.version>
        <postgresql.version>42.2.18</postgresql.version>
        <groovy.version>3.0.7</groovy.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>

        <!-- In-Memory database used for local development & testing -->
        <dependency>
            <groupId>com.h2database</groupId>
            <artifactId>h2</artifactId>
        </dependency>

        <!-- Switch back from Spring Boot 2.x standard HikariCP to Tomcat JDBC,
        configured later in Heroku (see https://stackoverflow.com/a/49970142/4964553) -->
        <dependency>
            <groupId>org.apache.tomcat</groupId>
            <artifactId>tomcat-jdbc</artifactId>
        </dependency>

        <!-- PostgreSQL used in Staging and Production environment, e.g. on Heroku -->
        <dependency>
            <groupId>org.postgresql</groupId>
            <artifactId>postgresql</artifactId>
            <version>${postgresql.version}</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-security</artifactId>
        </dependency>
        <!-- Testing -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>

        <dependency>
            <groupId>io.rest-assured</groupId>
            <artifactId>rest-assured</artifactId>
            <version>${rest-assured.version}</version>
            <scope>test</scope>
        </dependency>

        <!-- The fixed groovy version is necessary after upgrade to Rest-Assured 4.3. because of conflicts otherwise
         see https://stackoverflow.com/questions/61020200/getting-java-lang-abstractmethoderror-when-i-test-using-rest-assured -->
        <dependency>
            <groupId>org.codehaus.groovy</groupId>
            <artifactId>groovy</artifactId>
            <version>${groovy.version}</version>
        </dependency>

        <!-- Secure backend API -->
        <!--        <dependency>-->
        <!--            <groupId>org.springframework.boot</groupId>-->
        <!--            <artifactId>spring-boot-starter-security</artifactId>-->
        <!--        </dependency>-->

        <!--        <dependency>-->
        <!--            <groupId>org.springframework.security</groupId>-->
        <!--            <artifactId>spring-security-test</artifactId>-->
        <!--            <scope>test</scope>-->
        <!--        </dependency>-->

    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
            <plugin>
                <artifactId>maven-resources-plugin</artifactId>
                <executions>
                    <execution>
                        <id>copy Vue.js frontend content</id>
                        <phase>generate-resources</phase>
                        <goals>
                            <goal>copy-resources</goal>
                        </goals>
                        <configuration>
                            <outputDirectory>src/main/resources/public</outputDirectory>
                            <overwrite>true</overwrite>
                            <resources>
                                <resource>
                                    <directory>${project.parent.basedir}/frontend/target/dist</directory>
                                    <includes>
                                        <include>static/</include>
                                        <include>index.html</include>
                                        <include>favicon.ico</include>
                                    </includes>
                                </resource>
                            </resources>
                        </configuration>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
</project>
```

Для подключения БД как локальной так и `HerokuPostgres` - прописываем datasource в `application.properties`:

```properties
#port for backend
server.port=${PORT:8080}
# datasources for local db and heroku
spring.datasource.url=${SPRING_DATASOURCE_URL:jdbc:postgresql://localhost:5432/buyer}
spring.datasource.username=${SPRING_DATASOURCE_USERNAME:buyer}
spring.datasource.password=${SPRING_DATASOURCE_PASSWORD :buyer}
# for init db by admin
spring.datasource.initialization-mode=always
spring.jpa.hibernate.ddl-auto=update
spring.datasource.platform=postgres
```

В IDE подключаем БД c datasource из `docker-compose.yml`
![](.README_images/add_database.png)

### Frontend

На Windows: 

Ставим [Chocolatey](https://chocolatey.org/)

Выполняем `choco install npm`, затем `npm install -g @vue/cli` (по-моему через PowerShell)

Идем в корень проекта и выполняем `vue create frontend --no-git`

Как установить CLI (что выбрать при установке) - см. основной [туториал](https://github.com/jonashackt/spring-boot-vuejs).

Будет создана директорию `frontend` и сгенерится проект на `VueJs`

Нужно будет настроить проксирование запросов от фронта на бэк с помощью `Axios` (см. основной [туториал](https://github.com/jonashackt/spring-boot-vuejs))

Мой pom.xml для модуля `frontend`:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <artifactId>frontend</artifactId>

    <parent>
        <groupId>com.khilkevichigor</groupId>
        <artifactId>shuba-springboot-vue</artifactId>
        <version>0.0.1-SNAPSHOT</version>
    </parent>

    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
        <frontend-maven-plugin.version>1.11.0</frontend-maven-plugin.version>
    </properties>

    <build>
        <plugins>
            <plugin>
                <groupId>com.github.eirslett</groupId>
                <artifactId>frontend-maven-plugin</artifactId>
                <version>${frontend-maven-plugin.version}</version>
                <executions>
                    <!-- Install our node and npm version to run npm/node scripts-->
                    <execution>
                        <id>install node and npm</id>
                        <goals>
                            <goal>install-node-and-npm</goal>
                        </goals>
                        <configuration>
                            <nodeVersion>v15.5.1</nodeVersion>
                        </configuration>
                    </execution>
                    <!-- Install all project dependencies -->
                    <execution>
                        <id>npm install</id>
                        <goals>
                            <goal>npm</goal>
                        </goals>
                        <!-- optional: default phase is "generate-resources" -->
                        <phase>generate-resources</phase>
                        <!-- Optional configuration which provides for running any npm command -->
                        <configuration>
                            <arguments>install</arguments>
                        </configuration>
                    </execution>
                    <!-- Build and minify static files -->
                    <execution>
                        <id>npm run build</id>
                        <goals>
                            <goal>npm</goal>
                        </goals>
                        <configuration>
                            <arguments>run build</arguments>
                        </configuration>
                    </execution>
                    <!-- Run Unit tests with Jest -->
                    <execution>
                        <id>npm run test:unit</id>
                        <goals>
                            <goal>npm</goal>
                        </goals>
                        <phase>test</phase>
                        <configuration>
                            <arguments>run test:unit</arguments>
                        </configuration>
                    </execution>
                    <!-- Run End2End tests with Nightwatch -->
                    <!--
                    <execution>
                    <id>npm run test:e2e</id>
                    <goals>
                        <goal>npm</goal>
                    </goals>
                    <phase>test</phase>
                    <configuration>
                          <arguments>run test:e2e</arguments>
                    </configuration>
                    </execution>
                    -->
                </executions>
            </plugin>
        </plugins>
    </build>
</project>
```

### Собираем проект

В корне проекта выполняем `mvn clean install`, фронт на `VueJs` и бэк на `SpringBoot` упакуются в один jar в директории `backend/target`

После успешной сборки запускаем jar выполнив `mvn --projects backend spring-boot:run`

После запуска приложения идем на [localhost:8080](http://localhost:8080).

### Деплой
Деплоить приложение будем на [heroku.com](https://heroku.com)

Создаем новый проект, даем название и выбираем сервер например `Europe`

В `Resources` добавляем базу `HerokuPostgres` с бесплатным тарифом

В `Deploy` выбираем `github.com` -> репозиторий -> ветку с которой будем разворачивать приложение

Жмем деплой.

Финиш!


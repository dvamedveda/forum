package ru.job4j.forum;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

/**
 * Запуск приложения форум.
 */
@SpringBootApplication
public class ForumMain extends SpringBootServletInitializer {

    /**
     * Бин LiquiBase, выполняющий проверку и апдейт базы данных перед стартом приложения.
     * @param dataSource источник данных для приложения.
     * @return бин liquibase.
     */
    @Bean
    public SpringLiquibase liquibase(@Qualifier("mainDataSource") DataSource dataSource) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setChangeLog("classpath:liquibase-changelog.xml");
        liquibase.setDataSource(dataSource);
        return liquibase;
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(ForumMain.class);
    }

    /**
     * Точка входа.
     * @param args параметры для запуска приложения.
     */
    public static void main(String[] args) {
        SpringApplication.run(ForumMain.class, args);
    }
}

package ru.job4j.forum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * Запуск приложения форум.
 */
@SpringBootApplication
public class ForumMain extends SpringBootServletInitializer {

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

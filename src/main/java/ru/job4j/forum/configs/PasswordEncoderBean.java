package ru.job4j.forum.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Конфигурация кодировщика паролей.
 * Вынесена в отдельный файл для избежания цикличной зависимости
 * при инициализации контекста.
 */
@Configuration
public class PasswordEncoderBean {

    /**
     * Получение кодировщика паролей.
     *
     * @return объект кодировщика.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

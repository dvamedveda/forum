package ru.job4j.forum;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Генерация зашифрованного пароля для дефолтного пользователя в скрипт создания бд.
 */
public class PassGen {
    public static void main(String[] args) {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        String pass = encoder.encode("secret");
        System.out.println(pass);
    }
}

package ru.job4j.forum.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;
import ru.job4j.forum.models.User;

/**
 * Сервис для работы с пользователями.
 */
@Service
public class UserService {

    private final InMemoryUserDetailsManager userManager;
    private final PasswordEncoder passwordEncoder;

    public UserService(InMemoryUserDetailsManager inMemoryUserDetailsManager,
                       PasswordEncoder passwordEncoder) {
        this.userManager = inMemoryUserDetailsManager;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Создание нового пользователя в приложении.
     * @param user объект пользователя.
     * @return результат сохранения.
     */
    public boolean createNewUser(User user) {
        boolean result = false;
        if (!this.userManager.userExists(user.getLogin())) {
            this.userManager.createUser(
                    org.springframework.security.core.userdetails.User
                            .withUsername(user.getLogin())
                            .password(passwordEncoder.encode(user.getPassword()))
                            .roles("USER")
                            .build()
            );
            result = true;
        }

        return result;
    }
}

package ru.job4j.forum.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.job4j.forum.models.User;
import ru.job4j.forum.repositories.AuthorityDataRepository;
import ru.job4j.forum.repositories.UserDataRepository;

/**
 * Сервис для работы с пользователями.
 */
@Service
public class UserService {

    /**
     * Хранилище пользователей.
     */
    private final UserDataRepository userDataRepository;

    /**
     * Хранилище ролей.
     */
    private final AuthorityDataRepository authorityDataRepository;

    /**
     * Кодировщик паролей.
     */
    private final PasswordEncoder passwordEncoder;

    public UserService(UserDataRepository userDataRepository,
                       AuthorityDataRepository authorityDataRepository,
                       PasswordEncoder passwordEncoder) {
        this.userDataRepository = userDataRepository;
        this.authorityDataRepository = authorityDataRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Создание нового пользователя в приложении.
     * @param user объект пользователя.
     * @return результат сохранения.
     */
    public boolean createNewUser(User user) {
        boolean result = false;
        if (!this.userDataRepository.findByLogin(user.getLogin()).isPresent()) {
            String encodedPass = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodedPass);
            user.setEnabled(true);
            user.setAuthority(this.authorityDataRepository.findAuthorityByAuthority("ROLE_USER").get());
            this.userDataRepository.save(user);
            result = true;
        }
        return result;
    }
}

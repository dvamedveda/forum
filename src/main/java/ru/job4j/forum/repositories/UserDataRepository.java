package ru.job4j.forum.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.job4j.forum.models.User;

import java.util.Optional;

/**
 * Хранилище пользователей.
 */
@Repository
public interface UserDataRepository extends CrudRepository<User, Integer> {

    /**
     * Найти пользователя по логину.
     *
     * @param login логин пользователя.
     * @return объект пользователя.
     */
    Optional<User> findByLogin(String login);
}

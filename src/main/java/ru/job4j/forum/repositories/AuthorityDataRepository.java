package ru.job4j.forum.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.job4j.forum.models.Authority;

import java.util.Optional;

/**
 * Хранилище ролей.
 */
@Repository
public interface AuthorityDataRepository extends CrudRepository<Authority, Integer> {

    /**
     * Получить объект роли по названию.
     *
     * @param authority название роли.
     * @return объект роли.
     */
    Optional<Authority> findAuthorityByAuthority(String authority);
}

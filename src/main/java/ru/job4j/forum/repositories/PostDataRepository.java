package ru.job4j.forum.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.job4j.forum.models.Post;

/**
 * Хранилище постов.
 */
@Repository
public interface PostDataRepository extends CrudRepository<Post, Integer> {
}

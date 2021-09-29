package ru.job4j.forum.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.job4j.forum.models.Comment;

import java.util.Collection;

/**
 * Хранилище комментариев.
 */
@Repository
public interface CommentDataRepository extends CrudRepository<Comment, Integer> {

    /**
     * Найти все комментарии к посту по идентификатору.
     *
     * @param id идентификатор поста.
     * @return коллекция комментов.
     */
    Collection<Comment> findAllByPostId(int id);
}

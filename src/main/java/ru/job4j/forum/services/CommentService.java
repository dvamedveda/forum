package ru.job4j.forum.services;

import org.springframework.stereotype.Service;
import ru.job4j.forum.models.Comment;
import ru.job4j.forum.repositories.CommentRepository;

import java.util.Date;
import java.util.List;

/**
 * Сервис для работы с комментариями к постам.
 */
@Service
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    /**
     * Получить все комментарии к посту по указанному идентификатору.
     *
     * @param id идентификатор поста.
     * @return список комментариев.
     */
    public List<Comment> getCommentsToPost(int id) {
        return this.commentRepository.getCommentsToPost(id);
    }

    /**
     * Добавить комментарий к посту с указанным идентификатором.
     *
     * @param id      идентификатор поста.
     * @param comment объект комментария.
     */
    public void addCommentToPostById(int id, Comment comment) {
        comment.setCommented(new Date(System.currentTimeMillis()));
        this.commentRepository.addCommentByPostId(id, comment);
    }
}

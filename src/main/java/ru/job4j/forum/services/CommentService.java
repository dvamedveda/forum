package ru.job4j.forum.services;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.forum.models.Comment;
import ru.job4j.forum.models.User;
import ru.job4j.forum.repositories.CommentDataRepository;
import ru.job4j.forum.repositories.PostDataRepository;
import ru.job4j.forum.repositories.UserDataRepository;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Сервис для работы с комментариями к постам.
 */
@Service
@Transactional
public class CommentService {

    /**
     * Хранилище комментариев.
     */
    private final CommentDataRepository commentDataRepository;

    /**
     * Хранилище постов.
     */
    private final PostDataRepository postDataRepository;

    /**
     * Хранилище пользователей.
     */
    private final UserDataRepository userDataRepository;

    public CommentService(CommentDataRepository commentDataRepository,
                          PostDataRepository postDataRepository,
                          UserDataRepository userDataRepository) {
        this.commentDataRepository = commentDataRepository;
        this.postDataRepository = postDataRepository;
        this.userDataRepository = userDataRepository;
    }

    /**
     * Получить все комментарии к посту по указанному идентификатору.
     *
     * @param id идентификатор поста.
     * @return список комментариев.
     */
    public List<Comment> getCommentsToPost(int id) {
        return StreamSupport.stream(
                this.commentDataRepository.findAllByPostId(id).spliterator(), false)
                .collect(Collectors.toList());
    }

    /**
     * Добавить комментарий к посту с указанным идентификатором.
     *
     * @param id      идентификатор поста.
     * @param comment объект комментария.
     */
    public void addCommentToPostById(int id, Comment comment) {
        comment.setCommented(new Date(System.currentTimeMillis()));
        comment.setPost(this.postDataRepository.findById(id).get());
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = this.userDataRepository.findByLogin(username).get();
        comment.setUser(user);
        this.commentDataRepository.save(comment);
    }
}

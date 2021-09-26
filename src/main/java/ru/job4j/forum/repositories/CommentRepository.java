package ru.job4j.forum.repositories;

import org.springframework.stereotype.Repository;
import ru.job4j.forum.models.Comment;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Хранилище комментариев.
 */
@Repository
public class CommentRepository {
    private final Map<Integer, LinkedList<Comment>> comments = new HashMap<>();

    /**
     * Добавление комментария к посту с указанными id.
     *
     * @param id      идентификатор поста.
     * @param comment объект комментария.
     */
    public void addCommentByPostId(int id, Comment comment) {
        if (!this.comments.containsKey(id)) {
            this.newThreadToPost(id, comment);
        } else {
            this.saveComment(id, comment);
        }
    }

    /**
     * Метод заводит пост, и сохраняет комментарий к нему, если для поста еще нет комментариев.
     *
     * @param id      идентификатор поста.
     * @param comment объект комментария.
     */
    private void newThreadToPost(int id, Comment comment) {
        comments.put(id, new LinkedList<>());
        this.saveComment(id, comment);
    }

    /**
     * Метод сохраняет комментарий к посту, у которого уже есть комментарии.
     *
     * @param id      идентификатор поста.
     * @param comment объект комментария.
     */
    private void saveComment(int id, Comment comment) {
        List<Comment> postComments = this.comments.get(id);
        postComments.add(comment);
    }

    /**
     * Получить список комментариев к посту по указанному идентификатору.
     *
     * @param id идентификатор поста.
     * @return список комментариев.
     */
    public List<Comment> getCommentsToPost(int id) {
        return this.comments.get(id);
    }
}

package ru.job4j.forum.repositories;

import org.springframework.stereotype.Repository;
import ru.job4j.forum.models.Post;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Хранилище постов приложения.
 */
@Repository
public class PostRepository {

    /**
     * Счетчик идентификаторов постов.
     */
    private AtomicInteger idCounter = new AtomicInteger(0);

    private final Map<Integer, Post> posts = new HashMap<>();

    /**
     * Получение всех постов.
     * @return список всех постов.
     */
    public List<Post> getAllPosts() {
        return new ArrayList<>(this.posts.values());
    }

    /**
     * Сохранение или обновление поста в хранилище.
     * @param post объект поста.
     */
    public void savePost(Post post) {
        if (post.getId() == 0) {
            saveNewPost(post);
        } else {
            updatePost(post);
        }
    }

    /**
     * Сохранение нового поста.
     * @param post объект поста.
     */
    private void saveNewPost(Post post) {
        int newId = this.idCounter.incrementAndGet();
        post.setId(newId);
        this.posts.put(newId, post);
    }

    /**
     * Обновление существующего поста.
     * @param post объект поста.
     */
    private void updatePost(Post post) {
        post.setCreated(getPostById(post.getId()).getCreated());
        this.posts.replace(post.getId(), post);
    }

    /**
     * Получить пост по указанному идентификатору.
     * @param id идентификатор поста.
     * @return объект поста.
     */
    public Post getPostById(int id) {
        return this.posts.get(id);
    }
}

package ru.job4j.forum.services;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.job4j.forum.models.Post;
import ru.job4j.forum.models.User;
import ru.job4j.forum.repositories.PostRepository;

import java.util.Date;
import java.util.List;

/**
 * Сервис для работы с постами.
 */
@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    /**
     * Получение всех постов форума.
     *
     * @return список постов.
     */
    public List<Post> getPosts() {
        return this.postRepository.getAllPosts();
    }

    /**
     * Дополнение данными и сохранение поста.
     *
     * @param post объект поста.
     */
    public void savePost(Post post) {
        post.setCreated(new Date(System.currentTimeMillis()));
        User user = User.of(SecurityContextHolder.getContext().getAuthentication().getName());
        post.setUser(user);
        this.postRepository.savePost(post);
    }

    /**
     * Получение объект поста по указанному идентификатору.
     *
     * @param id идентификатор поста.
     * @return объект поста.
     */
    public Post getPostById(int id) {
        return this.postRepository.getPostById(id);
    }
}

package ru.job4j.forum.services;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.forum.models.Post;
import ru.job4j.forum.models.User;
import ru.job4j.forum.repositories.PostDataRepository;
import ru.job4j.forum.repositories.UserDataRepository;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Сервис для работы с постами.
 */
@Service
@Transactional
public class PostService {

    /**
     * Хранилище постов.
     */
    private final PostDataRepository postDataRepository;

    /**
     * Хранилище пользователей.
     */
    private final UserDataRepository userDataRepository;

    public PostService(PostDataRepository postDataRepository, UserDataRepository userDataRepository) {
        this.postDataRepository = postDataRepository;
        this.userDataRepository = userDataRepository;
    }

    /**
     * Получение всех постов форума.
     *
     * @return список постов.
     */
    public List<Post> getPosts() {
        return StreamSupport
                .stream(this.postDataRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    /**
     * Дополнение данными и сохранение поста.
     *
     * @param post объект поста.
     */
    public void savePost(Post post) {
        post.setCreated(new Date(System.currentTimeMillis()));
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = this.userDataRepository.findByLogin(username).get();
        post.setUser(user);
        this.postDataRepository.save(post);
    }

    /**
     * Получение объекта поста по указанному идентификатору.
     *
     * @param id идентификатор поста.
     * @return объект поста.
     */
    public Post getPostById(int id) {
        return this.postDataRepository.findById(id).orElse(null);
    }
}

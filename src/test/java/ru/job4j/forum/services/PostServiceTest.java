package ru.job4j.forum.services;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import ru.job4j.forum.models.Authority;
import ru.job4j.forum.models.Post;
import ru.job4j.forum.models.User;
import ru.job4j.forum.repositories.PostDataRepository;
import ru.job4j.forum.repositories.UserDataRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.core.Is.is;

/**
 * Тесты класса PostService.
 */
@SpringBootTest
@ActiveProfiles("test")
public class PostServiceTest {

    @Autowired
    PostService postService;

    @MockBean
    PostDataRepository postDataRepository;

    @MockBean
    UserDataRepository userDataRepository;

    /**
     * Проверка получения всех постов.
     */
    @Test
    public void whenGetPostsThenSuccess() {
        String givenUsername = "testcreated";
        String givenPassword = "testcreatedpassword";
        Authority authority = new Authority();
        authority.setId(2);
        authority.setAuthority("ROLE_USER");
        User existUser = new User();
        existUser.setLogin(givenUsername);
        existUser.setPassword(givenPassword);
        existUser.setEnabled(true);
        existUser.setAuthority(authority);
        existUser.setId(1);
        Post givenPost = Post.of("test post name", "test post desc", existUser);
        givenPost.setId(1);
        Collection<Post> posts = new ArrayList<>();
        posts.add(givenPost);
        Mockito.when(this.postDataRepository.findAll()).thenReturn(posts);
        List<Post> result = this.postService.getPosts();
        Assert.assertThat(result.size(), is(1));
        Post resultPost = result.get(0);
        Assert.assertThat(resultPost.getName(), is("test post name"));
        Assert.assertThat(resultPost.getDesc(), is("test post desc"));
        Assert.assertThat(resultPost.getId(), is(1));
        Assert.assertThat(resultPost.getUser(), is(existUser));
    }

    /**
     * Проверка получения поста по идентификатору.
     */
    @Test
    public void whenGetPostByIdThenSuccess() {
        String givenUsername = "testcreated";
        String givenPassword = "testcreatedpassword";
        Authority authority = new Authority();
        authority.setId(2);
        authority.setAuthority("ROLE_USER");
        User existUser = new User();
        existUser.setLogin(givenUsername);
        existUser.setPassword(givenPassword);
        existUser.setEnabled(true);
        existUser.setAuthority(authority);
        existUser.setId(1);
        Post givenPost = Post.of("test post name one", "test post desc one", existUser);
        givenPost.setId(1);
        Mockito.when(this.postDataRepository.findById(1)).thenReturn(Optional.of(givenPost));
        Post result = this.postService.getPostById(1);
        Assert.assertThat(result.getName(), is("test post name one"));
        Assert.assertThat(result.getDesc(), is("test post desc one"));
        Assert.assertThat(result.getId(), is(1));
        Assert.assertThat(result.getUser(), is(existUser));
    }

    /**
     * Проверка сохранения поста.
     */
    @Test
    @WithMockUser
    public void whenSavePostThenSuccess() {
        String givenUsername = "testcreated";
        String givenPassword = "testcreatedpassword";
        Authority authority = new Authority();
        authority.setId(2);
        authority.setAuthority("ROLE_USER");
        User existUser = new User();
        existUser.setLogin(givenUsername);
        existUser.setPassword(givenPassword);
        existUser.setEnabled(true);
        existUser.setAuthority(authority);
        existUser.setId(1);
        Optional<User> userSearchResult = Optional.of(existUser);
        Mockito.when(this.userDataRepository.findByLogin(Mockito.anyString())).thenReturn(userSearchResult);
        ArgumentCaptor<Post> postCaptor = ArgumentCaptor.forClass(Post.class);
        Post givenPost = Post.of("test name for save", "test desc for save", existUser);
        givenPost.setId(1);
        this.postService.savePost(givenPost);
        Mockito.verify(this.postDataRepository).save(postCaptor.capture());
        Post resultPost = postCaptor.getValue();
        Assert.assertThat(resultPost.getName(), is("test name for save"));
        Assert.assertThat(resultPost.getDesc(), is("test desc for save"));
        Assert.assertThat(resultPost.getId(), is(1));
        Assert.assertThat(resultPost.getUser(), is(existUser));
    }
}

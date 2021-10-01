package ru.job4j.forum.controllers;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.forum.models.Comment;
import ru.job4j.forum.models.Post;
import ru.job4j.forum.models.User;
import ru.job4j.forum.services.CommentService;
import ru.job4j.forum.services.PostService;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Тесты PostController
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class PostControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PostService postService;

    @MockBean
    private CommentService commentService;

    /**
     * Проверка загрузки страницы для создания новой темы.
     *
     * @throws Exception исключения при работе контроллера.
     */
    @Test
    @WithMockUser
    public void whenGetCreateThenSuccess() throws Exception {
        this.mvc.perform(get("/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("posts/create"));
    }

    /**
     * Проверка перенаправления на главную страницу после запроса редактирования несуществующего поста.
     *
     * @throws Exception исключения при работе контроллера.
     */
    @Test
    @WithMockUser
    public void whenEditUnexistPostThenRedirect() throws Exception {
        this.mvc.perform(get("/edit").param("id", "10000000"))
                .andExpect(redirectedUrl("/?notfound=true"))
                .andExpect(model().attributeDoesNotExist("post"));
    }

    /**
     * Проверка загрузки страницы для редактирования поста.
     *
     * @throws Exception исключения при работе контроллера.
     */
    @Test
    @WithMockUser
    public void whenEditExistPostThenCorrect() throws Exception {
        User user = User.of("user");
        Post mockPost = Post.of("test post name", "test post desc", user);
        Mockito.when(postService.getPostById(1)).thenReturn(mockPost);
        this.mvc.perform(get("/edit").param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("post"))
                .andExpect(model().attribute("post", mockPost))
                .andExpect(view().name("posts/edit"));
    }

    /**
     * Проверка перенаправления на главную страницу при попытке просмотра несуществующего поста.
     *
     * @throws Exception исключения при работе контроллера.
     */
    @Test
    @WithMockUser
    public void whenViewUnexistPostThenRedirect() throws Exception {
        this.mvc.perform(get("/view").param("id", "10000000"))
                .andExpect(redirectedUrl("/?notfound=true"))
                .andExpect(model().attributeDoesNotExist("post"))
                .andExpect(model().attributeDoesNotExist("comments"))
                .andExpect(model().attributeDoesNotExist("user"));
    }

    /**
     * Проверка загрузки страницы просмотра поста.
     *
     * @throws Exception исключения при работе контроллера.
     */
    @Test
    @WithMockUser
    public void whenViewExistPostThenCorrect() throws Exception {
        User mockUser = User.of("user 1");
        Post mockPost = Post.of("test view post name", "test view post desc", mockUser);
        List<Comment> mockComments = new ArrayList<>();
        mockComments.add(Comment.of("comment1", mockUser, mockPost));
        mockComments.add(Comment.of("comment2", mockUser, mockPost));
        Mockito.when(postService.getPostById(1)).thenReturn(mockPost);
        Mockito.when(commentService.getCommentsToPost(1)).thenReturn(mockComments);
        this.mvc.perform(get("/view").param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("post"))
                .andExpect(model().attribute("post", mockPost))
                .andExpect(model().attributeExists("comments"))
                .andExpect(model().attribute("comments", mockComments))
                .andExpect(model().attributeExists("user"))
                .andExpect(view().name("posts/view"));
    }

    /**
     * Проверка сохранения поста.
     *
     * @throws Exception исключения при работе контроллера.
     */
    @Test
    @WithMockUser
    public void whenSavePostThenSuccess() throws Exception {
        String postName = "test post name";
        String postDesc = "test post desc";
        this.mvc.perform(post("/save")
                .param("name", postName)
                .param("desc", postDesc))
                .andExpect(redirectedUrl("/"));
        ArgumentCaptor<Post> postCaptor = ArgumentCaptor.forClass(Post.class);
        Mockito.verify(this.postService).savePost(postCaptor.capture());
        Post savedPost = postCaptor.getValue();
        Assert.assertThat(savedPost.getName(), is(postName));
        Assert.assertThat(savedPost.getDesc(), is(postDesc));
    }

    /**
     * Проверка сохранения комментария для поста.
     *
     * @throws Exception исключения при работе контроллера.
     */
    @Test
    @WithMockUser
    public void whenLeaveCommentThenSuccess() throws Exception {
        int postId = 1;
        String commentText = "test comment text";
        this.mvc.perform(post("/comment")
                .param("postId", String.valueOf(postId))
                .param("text", commentText))
                .andExpect(redirectedUrl("/view?id=" + String.valueOf(postId)));
        ArgumentCaptor<Integer> postIdCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Comment> commentCaptor = ArgumentCaptor.forClass(Comment.class);
        Mockito.verify(this.commentService).addCommentToPostById(postIdCaptor.capture(), commentCaptor.capture());
        Assert.assertThat(postIdCaptor.getValue(), is(postId));
        Assert.assertThat(commentCaptor.getValue().getText(), is(commentText));
    }
}

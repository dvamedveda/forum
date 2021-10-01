package ru.job4j.forum.controllers;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.forum.models.Post;
import ru.job4j.forum.services.PostService;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Тесты IndexController
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class IndexControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PostService postService;

    /**
     * Проверка получения главной страницы приложения.
     *
     * @throws Exception исключения при работе контроллера.
     */
    @Test
    @WithMockUser
    public void whenGetIndexThenSuccess() throws Exception {
        List<Post> mockPosts = new ArrayList<>();
        Mockito.when(postService.getPosts()).thenReturn(mockPosts);
        this.mvc.perform(get("/index"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeDoesNotExist("message"))
                .andExpect(model().attributeExists("posts"))
                .andExpect(model().attribute("posts", mockPosts));
    }

    /**
     * Проверка получения главной страницы после перенаправления с ошибкой.
     *
     * @throws Exception ислкючения при работе контроллера.
     */
    @Test
    @WithMockUser(value = "anonymousUser")
    public void whenGetIndexWithNotFoundThenSuccess() throws Exception {
        List<Post> mockPosts = new ArrayList<>();
        Mockito.when(postService.getPosts()).thenReturn(mockPosts);
        this.mvc.perform(get("/index").param("notfound", "true"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attribute("message", "Указанная тема не найдена"))
                .andExpect(model().attributeDoesNotExist("user"))
                .andExpect(model().attributeExists("posts"))
                .andExpect(model().attribute("posts", mockPosts));
    }
}

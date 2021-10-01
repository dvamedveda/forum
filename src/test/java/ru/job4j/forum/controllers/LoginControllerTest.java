package ru.job4j.forum.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Тесты LoginController.
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class LoginControllerTest {

    @Autowired
    private MockMvc mvc;

    /**
     * Проверка загрузки страницы логина.
     *
     * @throws Exception исключения при работе контроллера.
     */
    @Test
    @WithMockUser
    public void whenGetLoginThenSuccess() throws Exception {
        this.mvc.perform(get("/login"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(model().attributeDoesNotExist("error"))
                .andExpect(model().attributeDoesNotExist("logout"))
                .andExpect(model().attributeExists("message"))
                .andExpect(model().attribute("message", ""));
    }

    /**
     * Проверка загрузки страницы логина после перенаправления с ошибкой о неправильных данных для аутентификации.
     *
     * @throws Exception исключения при работе контроллера.
     */
    @Test
    public void whenGetLoginWithErrorThenCorrect() throws Exception {
        this.mvc.perform(get("/login").param("error", "true"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(model().attributeExists("message"))
                .andExpect(model().attribute("message", "Неудачный вход, неправильное имя пользователя или пароль"));
    }

    /**
     * Проверка загрузки страницы логина после перенаправления при логауте.
     *
     * @throws Exception исключения при работе контроллера.
     */
    @Test
    public void whenGetLoginWithLogoutThenCorrect() throws Exception {
        this.mvc.perform(get("/login").param("logout", "true"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(model().attributeExists("message"))
                .andExpect(model().attribute("message", "Вы успешно вышли из системы"));
    }

    /**
     * Проверка перенаправления на страницу логина после логаута.
     *
     * @throws Exception исключения при работе контроллера.
     */
    @Test
    @WithMockUser
    public void whenLogoutThenRedirectToLogin() throws Exception {
        this.mvc.perform(get("/logout"))
                .andExpect(redirectedUrl("/login?logout=true"))
                .andExpect(unauthenticated());
    }

}

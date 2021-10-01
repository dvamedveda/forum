package ru.job4j.forum.controllers;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.forum.models.User;
import ru.job4j.forum.services.UserService;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Тесты RegistrationController.
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class RegistrationControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    /**
     * Проверка загрузки страницы регистрации пользовтеля.
     *
     * @throws Exception исключения при работе контроллера.
     */
    @Test
    public void whenGetRegPageThenSuccess() throws Exception {
        this.mvc.perform(get("/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"));
    }

    /**
     * Проверка создания пользователя с несуществующим именем.
     *
     * @throws Exception исключения при работе контроллера.
     */
    @Test
    public void whenCreateUnexistUserThenSuccess() throws Exception {
        String username = "test username";
        String password = "test password";
        Mockito.when(this.userService.createNewUser(Mockito.any())).thenReturn(true);
        this.mvc.perform(post("/register")
                .param("login", username)
                .param("password", password))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("message"))
                .andExpect(model().attribute("message", "Регистрация завершена. Вы можете зайти используя введенные данные"))
                .andExpect(view().name("login"));
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        Mockito.verify(this.userService).createNewUser(userCaptor.capture());
        User savedUser = userCaptor.getValue();
        Assert.assertThat(savedUser.getLogin(), is(username));
        Assert.assertThat(savedUser.getPassword(), is(password));
    }

    /**
     * Проверка создания пользователя с уже существующим именем.
     *
     * @throws Exception исключения при работе контроллера.
     */
    @Test
    public void whenCreateExistUserThenSuccess() throws Exception {
        String username = "test username";
        String password = "test password";
        Mockito.when(this.userService.createNewUser(Mockito.any())).thenReturn(false);
        this.mvc.perform(post("/register")
                .param("login", username)
                .param("password", password))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("message"))
                .andExpect(model().attribute("message", "Пользователь с таким логином уже существует."))
                .andExpect(view().name("login"));
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        Mockito.verify(this.userService).createNewUser(userCaptor.capture());
        User savedUser = userCaptor.getValue();
        Assert.assertThat(savedUser.getLogin(), is(username));
        Assert.assertThat(savedUser.getPassword(), is(password));
    }
}

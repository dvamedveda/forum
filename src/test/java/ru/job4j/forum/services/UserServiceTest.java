package ru.job4j.forum.services;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import ru.job4j.forum.models.Authority;
import ru.job4j.forum.models.User;
import ru.job4j.forum.repositories.AuthorityDataRepository;
import ru.job4j.forum.repositories.UserDataRepository;

import java.util.Optional;

import static org.hamcrest.core.Is.is;

/**
 * Тесты класса UserService.
 */
@SpringBootTest
@ActiveProfiles("test")
public class UserServiceTest {

    @MockBean
    UserDataRepository userDataRepository;

    @MockBean
    AuthorityDataRepository authorityDataRepository;

    @Autowired
    UserService userService;

    /**
     * Проверка создания пользователя с незанятым именем.
     */
    @Test
    public void whenCreateUnexistUserThenSuccess() {
        String givenUsername = "testcreated";
        String givenPassword = "testcreatedpassword";
        Optional<User> userSearchResult = Optional.empty();
        Mockito.when(this.userDataRepository.findByLogin("testcreated")).thenReturn(userSearchResult);
        Authority authority = new Authority();
        authority.setId(2);
        authority.setAuthority("ROLE_USER");
        Optional<Authority> authSearchResult = Optional.of(authority);
        Mockito.when(this.authorityDataRepository.findAuthorityByAuthority("ROLE_USER")).thenReturn(authSearchResult);
        User savingUser = new User();
        savingUser.setLogin(givenUsername);
        savingUser.setPassword(givenPassword);
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        boolean result = this.userService.createNewUser(savingUser);
        Assert.assertThat(result, is(true));
        Mockito.verify(this.userDataRepository).save(userCaptor.capture());
        User savedUser = userCaptor.getValue();
        Assert.assertThat(savedUser.getLogin(), is(givenUsername));
        Assert.assertNotEquals(savedUser.getPassword(), givenPassword);
        Assert.assertTrue(savedUser.isEnabled());
        Assert.assertEquals(savedUser.getAuthority(), authority);
    }

    /**
     * Проверка создания пользователя с уже занятым именем.
     */
    @Test
    public void whenCreateExistUserThenSuccess() {
        String givenUsername = "testcreated";
        String givenPassword = "testcreatedpassword";
        Authority authority = new Authority();
        authority.setId(2);
        authority.setAuthority("ROLE_USER");
        Optional<Authority> authSearchResult = Optional.of(authority);
        User existUser = new User();
        existUser.setLogin(givenUsername);
        existUser.setPassword(givenPassword);
        existUser.setEnabled(true);
        existUser.setAuthority(authority);
        existUser.setId(1);
        Optional<User> userSearchResult = Optional.of(existUser);
        Mockito.when(this.userDataRepository.findByLogin("testcreated")).thenReturn(userSearchResult);
        Mockito.when(this.authorityDataRepository.findAuthorityByAuthority("ROLE_USER")).thenReturn(authSearchResult);
        User savingUser = new User();
        savingUser.setLogin(givenUsername);
        savingUser.setPassword(givenPassword);
        boolean result = this.userService.createNewUser(savingUser);
        Assert.assertThat(result, is(false));
    }
}

package ru.job4j.forum.models;

import java.util.Objects;

/**
 * Модель данных для пользователя приложения.
 */
public class User {

    /**
     * Идентификатор пользователя.
     */
    private int id;

    /**
     * Имя пользователя.
     */
    private String login;

    /**
     * Пароль пользователя.
     */
    private String password;

    /**
     * Отметка об активности пользователя.
     */
    private boolean enabled;

    /**
     * Роль пользователя.
     */
    private String authority;

    public static User of(String name) {
        User user = new User();
        user.setLogin(name);
        return user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return id == user.id
                && enabled == user.enabled
                && Objects.equals(login, user.login)
                && Objects.equals(password, user.password)
                && Objects.equals(authority, user.authority);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password, enabled, authority);
    }
}

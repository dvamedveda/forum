package ru.job4j.forum.models;


import java.util.Date;
import java.util.Objects;

/**
 * Модель данных для поста на форум.
 */
public class Post {

    /**
     * Идентификатор постав.
     */
    private int id = 0;

    /**
     * Тема поста.
     */
    private String name;

    /**
     * Содержимое поста.
     */
    private String desc;

    /**
     * Дата создания поста.
     */
    private Date created;

    /**
     * Пользователь, создавший пост на форуме.
     */
    private User user;

    public static Post of(String name, User user) {
        Post post = new Post();
        post.name = name;
        post.user = user;
        post.created = new Date(System.currentTimeMillis());
        return post;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Post post = (Post) o;
        return id == post.id
                && Objects.equals(name, post.name)
                && Objects.equals(desc, post.desc)
                && Objects.equals(created, post.created);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, desc, created);
    }
}

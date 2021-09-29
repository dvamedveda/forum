package ru.job4j.forum.models;


import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

/**
 * Модель данных для поста на форум.
 */
@Entity
@Table(name = "forum_posts")
public class Post {

    /**
     * Идентификатор постав.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Тема поста.
     */
    @Column(name = "post_name")
    private String name;

    /**
     * Содержимое поста.
     */
    @Column(name = "post_desc")
    private String desc;

    /**
     * Дата создания поста.
     */
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date created;

    /**
     * Пользователь, создавший пост на форуме.
     */
    @ManyToOne
    @JoinColumn(name = "forum_user_id")
    private User user;

    public Post() {
    }

    public static Post of(String name, String desc, User user) {
        Post post = new Post();
        post.name = name;
        post.desc = desc;
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

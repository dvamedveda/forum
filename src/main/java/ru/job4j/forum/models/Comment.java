package ru.job4j.forum.models;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

/**
 * Модель данных для комментария.
 */
@Entity
@Table(name = "forum_comments")
public class Comment {

    /**
     * Идентификатор комментария.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Текст комментария.
     */
    @Column(name = "comment_text")
    private String text;

    /**
     * Время создания комментария.
     */
    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "commented")
    private Date commented;

    /**
     * Пользователь, оставивший комментарий.
     */
    @ManyToOne
    @JoinColumn(name = "forum_user_id")
    private User user;

    /**
     * Пост, к которому принадлежит комментарий.
     */
    @ManyToOne
    @JoinColumn(name = "forum_post_id")
    private Post post;

    public static Comment of(String text, User user, Post post) {
        Comment comment = new Comment();
        comment.text = text;
        comment.user = user;
        comment.commented = new Date(System.currentTimeMillis());
        comment.post = post;
        return comment;
    }

    public Comment() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getCommented() {
        return commented;
    }

    public void setCommented(Date commented) {
        this.commented = commented;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Comment comment = (Comment) o;
        return Objects.equals(text, comment.text)
                && Objects.equals(commented, comment.commented);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text, commented);
    }
}

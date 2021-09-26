package ru.job4j.forum.models;

import java.util.Date;
import java.util.Objects;

/**
 * Модель данных для комментария.
 */
public class Comment {

    /**
     * Текст комментария.
     */
    private String text;

    /**
     * Время создания комментария.
     */
    private Date commented;

    /**
     * Пользователь, оставивший комментарий.
     */
    private User user;

    public static Comment of(String text, User user) {
        Comment comment = new Comment();
        comment.text = text;
        comment.user = user;
        comment.commented = new Date(System.currentTimeMillis());
        return comment;
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

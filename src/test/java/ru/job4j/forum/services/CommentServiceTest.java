package ru.job4j.forum.services;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import ru.job4j.forum.models.Authority;
import ru.job4j.forum.models.Comment;
import ru.job4j.forum.models.Post;
import ru.job4j.forum.models.User;
import ru.job4j.forum.repositories.CommentDataRepository;
import ru.job4j.forum.repositories.PostDataRepository;
import ru.job4j.forum.repositories.UserDataRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.core.Is.is;

/**
 * Тесты класса CommentService.
 */
@SpringBootTest
@ActiveProfiles("test")
public class CommentServiceTest {

    @MockBean
    CommentDataRepository commentDataRepository;

    @MockBean
    PostDataRepository postDataRepository;

    @MockBean
    UserDataRepository userDataRepository;

    @Autowired
    CommentService commentService;

    /**
     * Проверка получения комментариев для поста.
     */
    @Test
    public void whenGetCommentsToPostThenSuccess() {
        String givenUsername = "testcreated";
        String givenPassword = "testcreatedpassword";
        Authority authority = new Authority();
        authority.setId(2);
        authority.setAuthority("ROLE_USER");
        User existUser = new User();
        existUser.setLogin(givenUsername);
        existUser.setPassword(givenPassword);
        existUser.setEnabled(true);
        existUser.setAuthority(authority);
        existUser.setId(1);
        Post givenPost = Post.of("test name for save", "test desc for save", existUser);
        givenPost.setId(1);
        Collection<Comment> givenComments = new ArrayList<>();
        Comment givenComment = Comment.of("test comment text", existUser, givenPost);
        givenComment.setId(1);
        givenComments.add(givenComment);
        Mockito.when(this.commentDataRepository.findAllByPostId(1)).thenReturn(givenComments);
        List<Comment> resultList = this.commentService.getCommentsToPost(1);
        Assert.assertThat(resultList.size(), is(1));
        Comment resultComment = resultList.get(0);
        Assert.assertThat(resultComment.getId(), is(1));
        Assert.assertThat(resultComment.getText(), is("test comment text"));
        Assert.assertThat(resultComment.getPost(), is(givenPost));
        Assert.assertThat(resultComment.getUser(), is(existUser));
    }

    /**
     * Проверка сохранения комментария для поста.
     */
    @Test
    @WithMockUser
    public void whenSaveCommentToPostThenSuccess() {
        String givenUsername = "testcreated";
        String givenPassword = "testcreatedpassword";
        Authority authority = new Authority();
        authority.setId(2);
        authority.setAuthority("ROLE_USER");
        User existUser = new User();
        existUser.setLogin(givenUsername);
        existUser.setPassword(givenPassword);
        existUser.setEnabled(true);
        existUser.setAuthority(authority);
        existUser.setId(1);
        Post givenPost = Post.of("test name for save", "test desc for save", existUser);
        givenPost.setId(1);
        Comment givenComment = new Comment();
        givenComment.setText("text for test");
        Mockito.when(this.postDataRepository.findById(1)).thenReturn(Optional.of(givenPost));
        Mockito.when(this.userDataRepository.findByLogin(Mockito.any())).thenReturn(Optional.of(existUser));
        this.commentService.addCommentToPostById(1, givenComment);
        ArgumentCaptor<Comment> commentCaptor = ArgumentCaptor.forClass(Comment.class);
        Mockito.verify(this.commentDataRepository).save(commentCaptor.capture());
        Comment resultComment = commentCaptor.getValue();
        Assert.assertThat(resultComment.getText(), is(givenComment.getText()));
        Assert.assertEquals(resultComment.getPost(), givenPost);
        Assert.assertEquals(resultComment.getUser(), existUser);
    }
}

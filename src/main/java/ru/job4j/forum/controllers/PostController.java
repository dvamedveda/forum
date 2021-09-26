package ru.job4j.forum.controllers;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.forum.models.Comment;
import ru.job4j.forum.models.Post;
import ru.job4j.forum.models.User;
import ru.job4j.forum.services.CommentService;
import ru.job4j.forum.services.PostService;

/**
 * Контроллер для обработки запросов к постам.
 */
@Controller
public class PostController {

    private final PostService postService;
    private final CommentService commentService;

    public PostController(PostService postService, CommentService commentService) {
        this.postService = postService;
        this.commentService = commentService;
    }

    /**
     * Показать страницу для создания поста на форум.
     *
     * @return название вида для страницы создания поста.
     */
    @GetMapping("/create")
    public String createPost() {
        return "posts/create";
    }

    /**
     * Показать страницу для редактирования поста на форуме.
     *
     * @param id    идентификатор редактируемого поста.
     * @param model объект с данными для вида.
     * @return название вида для редактирования поста или редирект на главную,
     * если запрашивается редактирование несуществующего поста.
     */
    @GetMapping("/edit")
    public String updatePost(@RequestParam("id") int id, Model model) {
        if (postService.getPostById(id) != null) {
            Post post = this.postService.getPostById(id);
            model.addAttribute("post", post);
            return "posts/edit";
        }
        return "redirect:/?notfound=true";
    }

    /**
     * Запрос на сохранение нового или отредактированного поста.
     *
     * @param post объект поста, построенный из параметров запроса.
     * @return редирект на главную страницу форума.
     */
    @PostMapping("/save")
    public String savePost(@ModelAttribute Post post) {
        this.postService.savePost(post);
        return "redirect:/";
    }

    /**
     * Показать детали поста на форуме - пост и комментарии к нему.
     *
     * @param id    идентификатор поста для просмотра.
     * @param model объект с данными для вида.
     * @return название вида для просмотра поста и комментариев.
     */
    @GetMapping("/view")
    public String goToPost(@RequestParam("id") int id, Model model) {
        if (postService.getPostById(id) != null) {
            model.addAttribute("post", postService.getPostById(id));
            model.addAttribute("comments", commentService.getCommentsToPost(id));
            model.addAttribute("user", User.of(SecurityContextHolder.getContext().getAuthentication().getName()));
            return "posts/view";
        }
        return "redirect:/?notfound=true";
    }

    /**
     * Обработка запроса на добавление комментария к посту.
     *
     * @param id      идентификатор поста.
     * @param comment комментарий к посту.
     * @param model   объект с данными для вида.
     * @return редирект на откомментированный пост.
     */
    @PostMapping("/comment")
    public String leaveComment(@RequestParam("id") int id, @ModelAttribute Comment comment, Model model) {
        commentService.addCommentToPostById(id, comment);
        model.addAttribute("user", User.of(SecurityContextHolder.getContext().getAuthentication().getName()));
        return "redirect:/view?id=" + id;
    }
}

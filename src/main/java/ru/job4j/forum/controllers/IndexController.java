package ru.job4j.forum.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.forum.models.User;
import ru.job4j.forum.services.PostService;

/**
 * Контроллер, обрабатывающий запросы к главное странице.
 */
@Controller
public class IndexController {

    private final PostService postService;

    public IndexController(PostService postService) {
        this.postService = postService;
    }

    /**
     * Показать главную страницу форума.
     *
     * @param notFound параметр с которым на главную переадресуется обращение к несуществующей теме форума.
     * @param model    данные для вида.
     * @return название вида для главной страницы форума.
     */
    @GetMapping({"/", "/index"})
    public String index(@RequestParam(name = "notfound", required = false) String notFound, Model model) {
        model.addAttribute("posts", postService.getPosts());
        String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!currentUserName.equals("anonymousUser")) {
            model.addAttribute("user", User.of(currentUserName));
        }
        if (notFound != null) {
            model.addAttribute("message", "Указанная тема не найдена");
        }
        return "index";
    }
}

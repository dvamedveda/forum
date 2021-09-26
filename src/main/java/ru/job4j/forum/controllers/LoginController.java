package ru.job4j.forum.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Контроллер, обрабатывающий запросы к странице логина.
 */
@Controller
public class LoginController {

    /**
     * Показать страницу для входа в приложение..
     *
     * @param request объект запроса.
     * @param model   объект с данными для вида.
     * @return название вида страницы логина.
     */
    @GetMapping("/login")
    public String login(HttpServletRequest request, Model model) {
        String message = "";
        if (request.getParameter("error") != null) {
            message = "Неудачный вход, неправильное имя пользователя или пароль";
        }
        if (request.getParameter("logout") != null) {
            message = "Вы успешно вышли из системы";
        }
        model.addAttribute("message", message);
        return "login";
    }

    /**
     * Выход из приложения.
     *
     * @param request  объект запроса.
     * @param response объект ответа.
     * @return редирект на страницу входа со специальным параметром.
     */
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return "redirect:/login?logout=true";
    }
}

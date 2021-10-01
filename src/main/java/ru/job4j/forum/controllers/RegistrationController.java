package ru.job4j.forum.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.forum.models.User;
import ru.job4j.forum.services.UserService;

/**
 * Контроллер для обработки запросов регистрации.
 */
@Controller
public class RegistrationController {

    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Показать страницу регистрации.
     *
     * @return название вида страницы регистрации.
     */
    @GetMapping("/register")
    public String getRegPage() {
        return "register";
    }

    /**
     * Обработка запроса на создание нового пользователя.
     *
     * @param user  объект юзера, построенный из данных из параметров запроса.
     * @param model объект с данными для ответа..
     * @return редирект на страницу логина с параметром об успешной регистрации или ошибке.
     */
    @PostMapping("/register")
    public String saveUser(@ModelAttribute User user, Model model) {
        boolean result = userService.createNewUser(user);
        if (!result) {
            model.addAttribute("message", "Пользователь с таким логином уже существует.");
        } else {
            model.addAttribute("message", "Регистрация завершена. Вы можете зайти используя введенные данные");
        }
        return "login";
    }
}

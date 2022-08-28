package ru.spring.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.spring.blog.dao.UserDAO;
import ru.spring.blog.models.User;

import java.util.regex.Pattern;

@Controller
@RequestMapping("/users")
public class UsersController {

    private final UserDAO userDAO;

    @Autowired
    public UsersController(UserDAO userDAO){
        this.userDAO =userDAO;
    }

    @GetMapping()
    public String index(Model model){
        model.addAttribute("users", userDAO.index());
        // Получаем всех людей из DAO и передаем на отображение в представление
        return "users/index";
    }


    @GetMapping("/new")
    public String newUser(@ModelAttribute("user")User user){
        return "users/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("user")User user){
        Pattern password_check = Pattern.compile("\\w{8,20}");
        if(password_check.matcher(user.getPassword()).find()){
            userDAO.save(user);
        }
        return "redirect:/users";
    }
}

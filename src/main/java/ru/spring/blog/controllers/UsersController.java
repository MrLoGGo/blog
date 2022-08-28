package ru.spring.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.spring.blog.dao.UserDAO;
import ru.spring.blog.models.User;

@Controller
@RequestMapping("/users")
public class UsersController {

    private UserDAO userDAO;

    @Autowired
    public UsersController(UserDAO userDAO){
        this.userDAO =userDAO;
    }

    @GetMapping("/new")
    public String newUser(@ModelAttribute("user")User user){
        return "users/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("user")User user){
        userDAO.save(user);
        return "redirect:/users";
    }
}
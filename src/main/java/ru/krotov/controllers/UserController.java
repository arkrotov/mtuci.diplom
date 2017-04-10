package ru.krotov.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.krotov.models.User;
import ru.krotov.service.UserService;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(path = "/users", method = RequestMethod.GET)
    public List<User> getAllUsers() {
        return userService.getAll();
    }
}

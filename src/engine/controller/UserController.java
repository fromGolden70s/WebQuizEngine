package engine.controller;

import engine.model.User;
import engine.repo.UserRepository;
import engine.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Validated
public class UserController {

    private UserRepository userRepository;
    private UserService userService;

    @Autowired
    public UserController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @PostMapping(path = "/actuator/shutdown")
    public void shutdown(){}

    @PostMapping(path = "/api/register")
    public User register(@Valid @RequestBody User user) {

        return userService.registerUser(user);
    }




}

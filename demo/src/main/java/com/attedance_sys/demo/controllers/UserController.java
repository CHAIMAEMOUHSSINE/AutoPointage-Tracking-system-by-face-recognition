package com.attedance_sys.demo.controllers;
import com.attedance_sys.demo.entity.Employee;
import com.attedance_sys.demo.entity.User;
import com.attedance_sys.demo.services.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*") //
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping
    public List<User> getAllUsers(){
        return userService.findAll();

    }
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id){
        return userService.getUserById(id);
    }
    @PostMapping
    public User createUser(@RequestBody User user){
        return userService.addUser(user);
    }

    @PostMapping("/login")
    public User login(@RequestBody User user){
        return userService.getUserBymail(user.getEmail(),user.getPassword());
    }

}

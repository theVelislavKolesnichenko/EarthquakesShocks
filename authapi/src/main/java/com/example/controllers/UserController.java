package com.example.controllers;

import com.example.models.Role;
import com.example.models.User;
import com.example.service.base.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RestController
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public void createUser(@Valid @RequestBody User user) throws Exception{
        this.userService.register(user);
    }

    @GetMapping("/getcurrontuser")
    @ResponseBody
    public String getCurontUser(Authentication authentication) throws Exception{
        return authentication.getName();
    }

    @GetMapping("/getcurrontuserrole")
    @ResponseBody
    public String getCurrontUserRole(Authentication authentication) throws Exception{
        return ((Role)authentication.getAuthorities().iterator().next()).getRole();
    }
}

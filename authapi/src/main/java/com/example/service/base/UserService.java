package com.example.service.base;

import com.example.models.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    User findById(int id);

    List<User> findAll();

    User loadUserByUsername(String username);

    void register(User user) throws Exception;
}

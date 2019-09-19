package com.example.service;

import com.example.models.Role;
import com.example.models.User;
import com.example.repository.RoleRepository;
import com.example.repository.UserRepository;
import com.example.service.base.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private RoleRepository roleRepository;
    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    public User findById(int id) {
        Optional<User> role = userRepository.findById(id);
        return role.get();
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findOneByUsername(username);
    }

    @Override
    public void register(User user) throws Exception {
        User newUser = new User();
        User existingUser = userRepository.findOneByUsername(user.getUsername());

        if (existingUser!= null){
            throw new Exception("Username exists");
        }
        List<Role> roles = new ArrayList<>();
        Optional<Role> role = roleRepository.findById(1);
        roles.add(role.get());
        System.out.println(roles.get(0).getRole());

        String encryptedPassword = this.bCryptPasswordEncoder.encode(user.getPassword());

        newUser.setPassword(encryptedPassword);
        newUser.setRoles(roles);
        newUser.setEnabled(true);
        newUser.setUsername(user.getUsername());
        newUser.setFullname(user.getFullname());
        newUser.setEmail(user.getEmail());

        this.userRepository.saveAndFlush(newUser);
    }
}


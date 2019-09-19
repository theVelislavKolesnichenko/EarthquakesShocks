package com.example.service;

import com.example.models.Role;
import com.example.repository.RoleRepository;
import com.example.service.base.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    private RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role findById(int id) {
        Optional<Role> role = roleRepository.findById(id);
        return role.get();
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

}

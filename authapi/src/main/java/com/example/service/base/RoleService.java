package com.example.service.base;

import com.example.models.Role;

import java.util.List;

public interface RoleService {

    Role findById(int id);

    List<Role> findAll();

}

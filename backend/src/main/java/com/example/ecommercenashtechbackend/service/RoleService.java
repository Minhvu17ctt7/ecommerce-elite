package com.example.ecommercenashtechbackend.service;

import com.example.ecommercenashtechbackend.entity.Role;

import java.util.List;

public interface RoleService {
    Role getRoleByName(String roleName);

    List<Role> getAllRoles();
}

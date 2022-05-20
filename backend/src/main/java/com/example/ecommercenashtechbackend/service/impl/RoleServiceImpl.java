package com.example.ecommercenashtechbackend.service.impl;

import com.example.ecommercenashtechbackend.entity.Role;
import com.example.ecommercenashtechbackend.repository.RoleRepository;
import com.example.ecommercenashtechbackend.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role getRoleByName(String roleName) {
        return roleRepository.findByName(roleName).orElseThrow(() -> new NotFoundException("Not found role"));
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }
}

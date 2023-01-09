package com.training.service.impl;

import com.training.entities.Role;
import com.training.repository.RoleRepository;
import com.training.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleRepository roleRepository;

    @Override
    public Role findByRoleName(String roleName) {
        Optional<Role> role = roleRepository.findByRoleName(roleName);
        return role.orElse(new Role());
    }
}

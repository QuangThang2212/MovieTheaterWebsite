package com.training.service;

import com.training.entities.Role;

public interface RoleService {
    Role findByRoleName(String roleName);
}

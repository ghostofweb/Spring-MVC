package com.runnerapp.web.repository;

import com.runnerapp.web.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Role findByName (String name);
}

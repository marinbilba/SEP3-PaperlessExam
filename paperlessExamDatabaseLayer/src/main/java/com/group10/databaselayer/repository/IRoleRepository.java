package com.group10.databaselayer.repository;

import com.group10.databaselayer.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRoleRepository extends JpaRepository<Role,Long> {
    Role findByName(String name);
}

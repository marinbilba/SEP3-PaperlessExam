package com.group10.databaselayer.repositories.user;

import com.group10.databaselayer.entity.user.Role;
import com.group10.databaselayer.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository that provides CRUD operations for role entity{@link Role}
 */
@Repository
public interface IRoleRepository extends JpaRepository<Role,Long> {
    Role findByName(String name);
}

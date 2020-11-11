package com.group10.databaselayer.repository;


import com.group10.databaselayer.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository that provides CRUD operations for user com.group10.databaselayer.entity
 */
@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
    List<User> findByLastName(String lastname);


    User findByUsername(String username);


}

package com.group10.databaselayer.repository;

import com.group10.databaselayer.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IUserRepositoryString extends JpaRepository<User, String> {
    List<User> findByLastName(String lastName);

    User findByUsername(String username);
}

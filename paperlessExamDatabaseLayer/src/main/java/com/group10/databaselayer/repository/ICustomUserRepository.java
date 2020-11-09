package com.group10.databaselayer.repository;

import com.group10.databaselayer.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICustomUserRepository {
   List<User> findByLastName(String lastName);

   User findByUsername(String username);
}

package com.group10.databaselayer.repositories.user;


import com.group10.databaselayer.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository that provides CRUD operations for user entity{@link User}
 */
@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
    List<User> findByLastName(String lastname);

    User findByUsername(String username);

    User getUserByUsername(String username);

    List<User> findByFirstNameIgnoreCaseContaining(String firstName);

}


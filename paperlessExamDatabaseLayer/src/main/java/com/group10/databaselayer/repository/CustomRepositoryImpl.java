package com.group10.databaselayer.repository;

import com.group10.databaselayer.entity.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
@Repository
public class CustomRepositoryImpl implements ICustomUserRepository {
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public List<User> findByLastName(String lastName) {
        return (List<User>) entityManager.getReference(User.class,lastName);
    }
}

package com.group10.databaselayer.entity.user;

import com.group10.databaselayer.entity.Role;
import com.group10.databaselayer.entity.User;

import javax.persistence.*;

import javax.persistence.*;

@Entity
@Table(name = "user_roles")
public class UserRoles {
 // this entity needs an ID:
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;


}

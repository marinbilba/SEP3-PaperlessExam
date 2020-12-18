package com.group10.paperlessexamwebservice.controller;

import com.group10.paperlessexamwebservice.model.questions.multiplechoice.MultipleChoiceSet;
import com.group10.paperlessexamwebservice.model.user.Role;
import com.group10.paperlessexamwebservice.model.user.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {

    private static String businessTierUrl;
    private static RestTemplate restUtility;
    private static User user;

    @BeforeAll
    public static void setup() {
        businessTierUrl = "http://localhost:8080";
        restUtility = new RestTemplate();
        user = new User("Silvestru", "Mandrila", "silvmandrila", "silvmandr@va.cs", "111111", new Role(2, "Teacher"));
    }


    @Test
    void loginUser() {
        // Good case
        User user= new User("Silvestru", "Mandrila", "silvmandrila", "silvmandr@va.cs", "111111", new Role(2, "Teacher"));
        //  Wrong username
        User user1 = new User("Silvestru", "Mandrila", "silvmandrilaE", "silvmandr@va.cs", "111111", new Role(2, "Teacher"));
        // Wrong password ...
        User user2 = new User("Silvestru", "Mandrila", "silvmandrila", "silvmandr@va.cs", "111E111", new Role(2, "Teacher"));


        ResponseEntity result1 = restUtility.postForEntity(businessTierUrl + "/user/login", user, user.getClass());
        assertEquals(result1.getStatusCode(), HttpStatus.OK);

        Assertions.assertThrows(HttpClientErrorException.class, () -> {
            ResponseEntity result2 = restUtility.postForEntity(businessTierUrl + "/user/login", user1, user1.getClass());
            assertEquals(result2.getStatusCode(), HttpStatus.FORBIDDEN, "User not found");
        });
        Assertions.assertThrows(HttpClientErrorException.class, () -> {
            ResponseEntity result2 = restUtility.postForEntity(businessTierUrl + "/user/login", user2, user2.getClass());
            assertEquals(result2.getStatusCode(), HttpStatus.FORBIDDEN, "Wrong Password");
        });
        Assertions.assertThrows(HttpClientErrorException.class, () -> {
            ResponseEntity result2 = restUtility.postForEntity(businessTierUrl + "/user/login1", user, user.getClass());
            assertEquals(result2.getStatusCode(), HttpStatus.NOT_FOUND, "Not found");
        });
    }

    @Test
    void createUser() {
        // Good case - New user.
        User user= new User("Silvestru1", "Mandrila1", "silvmandrila1", "silvmandrila1@va.cs", "111111", new Role(2, "Teacher"));
        user.setConfirmPassword("111111");

        //  Username Exist exception
        User user1= new User("Silvestru1", "Mandrila1", "silvmandrila", "silvmandrila@va.cs", "111111", new Role(2, "Teacher"));
        user.setConfirmPassword("111111");

        // Wrong confirm password exception
        User user2= new User("Silvestru1", "Mandrila1", "silvmandrila1", "silvmandrila1@va.cs", "111111", new Role(2, "Teacher"));
        user2.setConfirmPassword("111111E");

        User user3 = new User("Silvestru", "Mandrila", "silvmandrila", "silvmandr@va.cs", "111111",  new Role(2, "Teacher"));

        ResponseEntity result1 = restUtility.postForEntity(businessTierUrl + "/user/createUser", user, user.getClass());
        assertEquals(result1.getStatusCode(), HttpStatus.OK);


        Assertions.assertThrows(HttpClientErrorException.class, () -> {
            ResponseEntity result2 = restUtility.postForEntity(businessTierUrl + "/user/createUser", user1, user1.getClass());
            assertEquals(result2.getStatusCode(), HttpStatus.CONFLICT, "Username found");
        });
        Assertions.assertThrows(HttpClientErrorException.class, () -> {
            ResponseEntity result2 = restUtility.postForEntity(businessTierUrl + "/user/createUser", user2, user2.getClass());
            assertEquals(result2.getStatusCode(), HttpStatus.UNAUTHORIZED, "Wrong confirm Password");
        });
        Assertions.assertThrows(HttpClientErrorException.class, () -> {
            ResponseEntity result2 = restUtility.postForEntity(businessTierUrl + "/user/createUser4", user, user.getClass());
            assertEquals(result2.getStatusCode(), HttpStatus.NOT_FOUND, "Not found");
        });
    }

    @Test
    void getAllUsersList() {
    }

    @Test
    void getUsersByFirstName() {
        // Good case
        User user= new User("Silvestru", "Mandrila", "silvmandrila", "silvmandr@va.cs", "111111", new Role(2, "Teacher"));
        //  Wrong username
        User user1 = new User("Silvestru1", "Mandrila", "silvmandrilaE", "silvmandr@va.cs", "111111", new Role(2, "Teacher"));

        List<User> userList = new ArrayList<>(); userList.add(user);

        ResponseEntity result1 = restUtility.getForEntity(businessTierUrl + "/user/getUsersByFirstName/"+user.getFirstName(), userList.getClass());
        assertEquals(result1.getStatusCode(), HttpStatus.OK);

        Assertions.assertThrows(HttpClientErrorException.class, () -> {
            ResponseEntity result2 = restUtility.getForEntity(businessTierUrl + "/user/getUsersByFirstName/"+user1.getFirstName(), userList.getClass());
            assertEquals(result2.getStatusCode(), HttpStatus.BAD_REQUEST, "User not found");
        });
    }

    @Test
    void getUserByUsername() {
        // Good case - New user.
        User user= new User("Silvestru", "Mandrila", "silvmandrila", "silvmandrila@va.cs", "111111", new Role(2, "Teacher"));
        user.setConfirmPassword("111111");

        //  Username nof found
        User user1= new User("Silvestru1", "Mandrila1", "silvmandrila1", "silvmandrila1@va.cs", "111111", new Role(2, "Teacher"));
        user.setConfirmPassword("111111");


        ResponseEntity result1 = restUtility.getForEntity(businessTierUrl + "/user/getUserByUsername/"+user.getUsername(), user.getClass());
        assertEquals(result1.getStatusCode(), HttpStatus.OK);


        Assertions.assertThrows(HttpClientErrorException.class, () -> {
            ResponseEntity result2 = restUtility.getForEntity(businessTierUrl + "/user/getUserByUsername/"+user1.getUsername(), user1.getClass());
            assertEquals(result2.getStatusCode(), HttpStatus.BAD_REQUEST, "Username not found");
        });
    }

    @Test
    void getUserStudentByUsername() {
        // Good case - New user.
        User user= new User("Marin", "Bilba", "marinbilba", "marinbilba@gmail.com", "111111", new Role(1, "Student"));

        //  Username nof found
        User user1= new User("Marin", "Bilba", "marinbilba1", "marinbilba@gmail.com", "111111", new Role(1, "Student"));


        ResponseEntity result1 = restUtility.getForEntity(businessTierUrl + "/user/getUserStudentByUsername/"+user.getUsername(), user.getClass());
        assertEquals(result1.getStatusCode(), HttpStatus.OK);


        Assertions.assertThrows(HttpClientErrorException.class, () -> {
            ResponseEntity result2 = restUtility.getForEntity(businessTierUrl + "/user/getUserStudentByUsername/"+user1.getUsername(), user1.getClass());
            assertEquals(result2.getStatusCode(), HttpStatus.BAD_REQUEST, "Username not found");
        });

    }

    @Test
    void getUsersStudentsByFirstName() {
        // Good case
        User user= new User("Bilba", "Marin", "marinbilba", "marinbilba@gmail.com", "111111", new Role(1, "Student"));
        List<User> userList = new ArrayList<>(); userList.add(user);

        //  Username with this firstName not found
        User user1= new User("Marin1", "Bilba1", "marinbilba1", "marinbilba1@gmail.com", "111111", new Role(1, "Student"));


        ResponseEntity result1 = restUtility.getForEntity(businessTierUrl + "/user/getUsersStudentsByFirstName/"+user.getFirstName(), userList.getClass());
        assertEquals(result1.getStatusCode(), HttpStatus.OK);


        Assertions.assertThrows(HttpClientErrorException.class, () -> {
            ResponseEntity result2 = restUtility.getForEntity(businessTierUrl + "/user/getUsersStudentsByFirstName/"+user1.getFirstName(), userList.getClass());
            assertEquals(result2.getStatusCode(), HttpStatus.BAD_REQUEST, "Username not found");
        });
    }

    @Test
    void updateUser() {
        // Good case
        User user= new User("Silvestru", "Mandrila", "silvmandrila", "silvmandrila@va.cs", "111111", new Role(2, "Teacher"));
        user.setConfirmPassword("111111");

        //  Username not found
        User user1= new User("Silvestru", "Mandrila", "silvmandrila1", "silvmandrila@va.cs", "111111", new Role(2, "Teacher"));
        user.setConfirmPassword("111111");

        // Wrong email format
        User user2= new User("Silvestru", "Mandrila", "silvmandrila1", "silvmandrila1va.cs", "111111", new Role(2, "Teacher"));
        user2.setConfirmPassword("111111E");
        // Null field
        User user3 = new User(null, "Mandrila", "silvmandrila", "silvmandr@va.cs", "111111",  new Role(2, "Teacher"));

        ResponseEntity result1 = restUtility.postForEntity(businessTierUrl + "/user/updateUser", user, user.getClass());
        assertEquals(result1.getStatusCode(), HttpStatus.OK);


        Assertions.assertThrows(HttpClientErrorException.class, () -> {
            ResponseEntity result2 = restUtility.postForEntity(businessTierUrl + "/user/updateUser", user1, user1.getClass());
            assertEquals(result2.getStatusCode(), HttpStatus.BAD_REQUEST, "Username not found");
        });
        Assertions.assertThrows(HttpClientErrorException.class, () -> {
            ResponseEntity result2 = restUtility.postForEntity(businessTierUrl + "/user/updateUser", user2, user2.getClass());
            assertEquals(result2.getStatusCode(), HttpStatus.CONFLICT, "Wrong email format");
        });
        Assertions.assertThrows(HttpClientErrorException.class, () -> {
            ResponseEntity result2 = restUtility.postForEntity(businessTierUrl + "/user/updateUser", user3, user3.getClass());
            assertEquals(result2.getStatusCode(), HttpStatus.BAD_REQUEST, "Null field");
        });
    }

    @Test
    void deleteUser() {
        // Good case
        User user= new User("Silvestru", "Mandrila", "silvmandrila", "silvmandrila@va.cs", "111111", new Role(2, "Teacher"));
        user.setConfirmPassword("111111");

        //  Username not found
        User user1= new User("Silvestru", "Mandrila", "silvmandrila1", "silvmandrila@va.cs", "111111", new Role(2, "Teacher"));
        user.setConfirmPassword("111111");

        // Wrong email format
        User user2= new User("Silvestru", "Mandrila", "silvmandrila1", "silvmandrila1va.cs", "111111", new Role(2, "Teacher"));
        user2.setConfirmPassword("111111E");
        // Null field
        User user3 = new User(null, "Mandrila", "silvmandrila", "silvmandr@va.cs", "111111",  new Role(2, "Teacher"));

        ResponseEntity result1 = restUtility.postForEntity(businessTierUrl + "/user/updateUser", user, user.getClass());
        assertEquals(result1.getStatusCode(), HttpStatus.OK);


        Assertions.assertThrows(HttpClientErrorException.class, () -> {
            ResponseEntity result2 = restUtility.postForEntity(businessTierUrl + "/user/updateUser", user1, user1.getClass());
            assertEquals(result2.getStatusCode(), HttpStatus.BAD_REQUEST, "Username not found");
        });
        Assertions.assertThrows(HttpClientErrorException.class, () -> {
            ResponseEntity result2 = restUtility.postForEntity(businessTierUrl + "/user/updateUser", user2, user2.getClass());
            assertEquals(result2.getStatusCode(), HttpStatus.CONFLICT, "Wrong email format");
        });
        Assertions.assertThrows(HttpClientErrorException.class, () -> {
            ResponseEntity result2 = restUtility.postForEntity(businessTierUrl + "/user/updateUser", user3, user3.getClass());
            assertEquals(result2.getStatusCode(), HttpStatus.BAD_REQUEST, "Not found");
        });
    }
}
package com.group10.paperlessexamwebservice.controller;

import com.group10.paperlessexamwebservice.model.questions.multiplechoice.MultipleChoiceSet;
import com.group10.paperlessexamwebservice.model.questions.written.WrittenSet;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class QuestionSetsControllerTest {
    private static String businessTierUrl;
    private static RestTemplate restUtility;
    private static User user;

    @BeforeAll
    public static void setup() {
        businessTierUrl = "http://localhost:8080";
        restUtility = new RestTemplate();
        user = new User("Silvestru", "Mandrila", "silvmandrila", "silvmandr@va.cs", "111111", new Role(1, "Strudent"));
    }

    @Test
    void addMultipleChoiceQuestion() {
//        MultipleChoiceQuestion createdMultipleChoiceQuestion = null;
//        createdMultipleChoiceQuestion = questionSetsService.addMultipleChoiceQuestion(multipleChoiceQuestion);
//
    }

    @Test
    void addMultipleChoiceQuestionOption() {
    }

    @Test
    void init() {
        //
//        Gson gson = new GsonBuilder().setPrettyPrinting().create();
//        User user = null;
//        try {
//            user = userController.getUsersByUsername("silvmandrila");
//        } catch (ServiceNotAvailable serviceNotAvailable) {
//            serviceNotAvailable.printStackTrace();
//        } catch (UserNotFound userNotFound) {
//            userNotFound.printStackTrace();
//        }
//
//       WrittenSet writtenSet = null;
//        try {
//            writtenSet=questionSetsService.createWrittenSet(new WrittenSet("Geo","Ner",user));
//        } catch (NullQuestionSet nullQuestionSet) {
//            nullQuestionSet.printStackTrace();
//        } catch (EmptyQuestionSetTitleOrTopic emptyQuestionSetTitleOrTopic) {
//            emptyQuestionSetTitleOrTopic.printStackTrace();
//        } catch (ServiceNotAvailable serviceNotAvailable) {
//            serviceNotAvailable.printStackTrace();
//        } catch (UserNotFound userNotFound) {
//            userNotFound.printStackTrace();
//        } catch (UnexpectedError unexpectedError) {
//            unexpectedError.printStackTrace();
//        } catch (QuestionSetAlreadyExists questionSetAlreadyExists) {
//            questionSetAlreadyExists.printStackTrace();
//        }
//        try {
//            writtenSet = questionSetsService.getWrittenSet(new WrittenSet("Romana", "Eseu", user));
//        } catch (NullQuestionSet nullQuestionSet) {
//            nullQuestionSet.printStackTrace();
//        } catch (EmptyQuestionSetTitleOrTopic emptyQuestionSetTitleOrTopic) {
//            emptyQuestionSetTitleOrTopic.printStackTrace();
//        } catch (ServiceNotAvailable serviceNotAvailable) {
//            serviceNotAvailable.printStackTrace();
//        } catch (UnexpectedError unexpectedError) {
//            unexpectedError.printStackTrace();
//        }
//
//        try {
//           writtenSet= questionSetsService.createWrittenSet(new WrittenSet("Chemist", "Eseu", user));
//        } catch (NullQuestionSet nullQuestionSet) {
//            nullQuestionSet.printStackTrace();
//        } catch (EmptyQuestionSetTitleOrTopic emptyQuestionSetTitleOrTopic) {
//            emptyQuestionSetTitleOrTopic.printStackTrace();
//        } catch (ServiceNotAvailable serviceNotAvailable) {
//            serviceNotAvailable.printStackTrace();
//        } catch (UnexpectedError unexpectedError) {
//            unexpectedError.printStackTrace();
//        } catch (UserNotFound userNotFound) {
//            userNotFound.printStackTrace();
//        } catch (QuestionSetAlreadyExists questionSetAlreadyExists) {
//            questionSetAlreadyExists.printStackTrace();
//        }

//        WrittenQuestion writtenQuestion=null;
//
//        try {
//          writtenQuestion=  questionSetsService.addWrittenQuestion(new WrittenQuestion("What is 2?",122,4,writtenSet));
//        } catch (EmptyMultipleChoiceQuestion emptyMultipleChoiceQuestion) {
//            emptyMultipleChoiceQuestion.printStackTrace();
//        } catch (NullQuestionSet nullQuestionSet) {
//            nullQuestionSet.printStackTrace();
//        } catch (EmptyQuestionSetTitleOrTopic emptyQuestionSetTitleOrTopic) {
//            emptyQuestionSetTitleOrTopic.printStackTrace();
//        } catch (UnexpectedError unexpectedError) {
//            unexpectedError.printStackTrace();
//        } catch (ServiceNotAvailable serviceNotAvailable) {
//            serviceNotAvailable.printStackTrace();
//        } catch (QuestionSetAlreadyExists questionSetAlreadyExists) {
//            questionSetAlreadyExists.printStackTrace();
//        }

//        try {
//            writtenQuestion=questionSetsService.getWrittenQuestion(new WrittenQuestion("What is lif?",12,4,writtenSet));
//        } catch (EmptyMultipleChoiceQuestion emptyMultipleChoiceQuestion) {
//            emptyMultipleChoiceQuestion.printStackTrace();
//        } catch (NullQuestionSet nullQuestionSet) {
//            nullQuestionSet.printStackTrace();
//        } catch (EmptyQuestionSetTitleOrTopic emptyQuestionSetTitleOrTopic) {
//            emptyQuestionSetTitleOrTopic.printStackTrace();
//        } catch (UnexpectedError unexpectedError) {
//            unexpectedError.printStackTrace();
//        } catch (ServiceNotAvailable serviceNotAvailable) {
//            serviceNotAvailable.printStackTrace();
//        } catch (NullQuestionSetQuestion nullQuestionSetQuestion) {
//            nullQuestionSetQuestion.printStackTrace();
//        }


        //    MultipleChoiceSet ms = null;
//       try {
//            ms = questionSetsService.getMultipleChoiceSet(new MultipleChoiceSet("Java21e1", "Capitals", user));
//        } catch (ServiceNotAvailable serviceNotAvailable) {
//            serviceNotAvailable.printStackTrace();
//        } catch (NullQuestionSet nullQuestionSet) {
//            nullQuestionSet.printStackTrace();
//        } catch (QuestionSetAlreadyExists questionSetAlreadyExists) {
//            questionSetAlreadyExists.printStackTrace();
//        } catch (EmptyQuestionSetTitleOrTopic emptyQuestionSetTitleOrTopic) {
//            emptyQuestionSetTitleOrTopic.printStackTrace();
//        } catch (UnexpectedError unexpectedError) {
//            unexpectedError.printStackTrace();
//        }

//        MultipleChoiceQuestion mq = null;
//
//        try {
//            mq = questionSetsService.getMultipleChoiceSetQuestion(new MultipleChoiceQuestion("OOP?", 50, 2, ms));
//        } catch (NullQuestionSet nullQuestionSet) {
//            nullQuestionSet.printStackTrace();
//        } catch (NegativeNumberException e) {
//            e.printStackTrace();
//        } catch (EmptyMultipleChoiceQuestion emptyMultipleChoiceQuestion) {
//            emptyMultipleChoiceQuestion.printStackTrace();
//        } catch (ServiceNotAvailable serviceNotAvailable) {
//            serviceNotAvailable.printStackTrace();
//        } catch (EmptyQuestionSetTitleOrTopic emptyQuestionSetTitleOrTopic) {
//            emptyQuestionSetTitleOrTopic.printStackTrace();
//        } catch (UnexpectedError unexpectedError) {
//            unexpectedError.printStackTrace();
//        } catch (QuestionSetAlreadyExists questionSetAlreadyExists) {
//            questionSetAlreadyExists.printStackTrace();
//        } catch (NullQuestionSetQuestion nullQuestionSetQuestion) {
//            nullQuestionSetQuestion.printStackTrace();
//        }
//
//
//        try {
//            QuestionOption qo = questionSetsService.addMultipleChoiceQuestionOption(new QuestionOption(true, "yessadsad", mq));
//            System.out.println(qo.getAnswer());
//        } catch (EmptyMultipleChoiceQuestion emptyMultipleChoiceQuestion) {
//            emptyMultipleChoiceQuestion.printStackTrace();
//        } catch (NullQuestionSet nullQuestionSet) {
//            nullQuestionSet.printStackTrace();
//        } catch (ServiceNotAvailable serviceNotAvailable) {
//            serviceNotAvailable.printStackTrace();
//        } catch (NullQuestionSetQuestion nullQuestionSetQuestion) {
//            nullQuestionSetQuestion.printStackTrace();
//        } catch (NegativeNumberException e) {
//            e.printStackTrace();
//        } catch (UnexpectedError unexpectedError) {
//            unexpectedError.printStackTrace();
//        } catch (EmptyQuestionSetTitleOrTopic emptyQuestionSetTitleOrTopic) {
//            emptyQuestionSetTitleOrTopic.printStackTrace();
//        } catch (QuestionSetAlreadyExists questionSetAlreadyExists) {
//            questionSetAlreadyExists.printStackTrace();
//        }

//        QuestionOption questionOption =new QuestionOption(true,"Yes",mq);
//        try {
//            questionSetsService.addMultipleChoiceQuestionOption(questionOption);
//            //QuestionOption questionOption2= questionSetsService.getMultipleChoiceSetQuestionOption(questionOption);
//            //System.out.println(questionOption2.getAnswer());
//        } catch (EmptyMultipleChoiceQuestion emptyMultipleChoiceQuestion) {
//            emptyMultipleChoiceQuestion.printStackTrace();
//        } catch (NullQuestionSet nullQuestionSet) {
//            nullQuestionSet.printStackTrace();
//        } catch (ServiceNotAvailable serviceNotAvailable) {
//            serviceNotAvailable.printStackTrace();
//        } catch (NullQuestionSetQuestion nullQuestionSetQuestion) {
//            nullQuestionSetQuestion.printStackTrace();
//        } catch (NegativeNumberException e) {
//            e.printStackTrace();
//        } catch (UnexpectedError unexpectedError) {
//            unexpectedError.printStackTrace();
//        } catch (EmptyQuestionSetTitleOrTopic emptyQuestionSetTitleOrTopic) {
//            emptyQuestionSetTitleOrTopic.printStackTrace();
//        } catch (QuestionSetAlreadyExists questionSetAlreadyExists) {
//            questionSetAlreadyExists.printStackTrace();
//        }


//
//
//            System.out.println(gson.toJson(mq2));
//        } catch (ServiceNotAvailable serviceNotAvailable) {
//            serviceNotAvailable.printStackTrace();
//        } catch (NullQuestionSet nullQuestionSet) {
//            nullQuestionSet.printStackTrace();
//        } catch (QuestionSetAlreadyExists questionSetAlreadyExists) {
//            questionSetAlreadyExists.printStackTrace();
//        } catch (EmptyQuestionSetTitleOrTopic emptyQuestionSetTitleOrTopic) {
//            emptyQuestionSetTitleOrTopic.printStackTrace();
//        } catch (UnexpectedError unexpectedError) {
//            unexpectedError.printStackTrace();
//        } catch (NegativeNumberException e) {
//            e.printStackTrace();
//        } catch (EmptyMultipleChoiceQuestion emptyMultipleChoiceQuestion) {
//            emptyMultipleChoiceQuestion.printStackTrace();
//        } catch (NullQuestionSetQuestion nullQuestionSetQuestion) {
//            nullQuestionSetQuestion.printStackTrace();
//        }


//        MultipleChoiceQuestion multipleChoiceQuestion = new MultipleChoiceQuestion(1, "What is this?", 20);
//        QuestionOption questionOption = new QuestionOption(true, "da");
//        QuestionOption questionOption2 = new QuestionOption(false, "nu");
//        multipleChoiceQuestion.addQuestionOption(questionOption);
//        multipleChoiceQuestion.addQuestionOption(questionOption2);


//         MultipleChoiceSet multipleChoiceSet = new MultipleChoiceSet("Java", "Capitals");
//        multipleChoiceSet.setUser(user);
//        System.out.println(gson.toJson(multipleChoiceSet));


        //System.out.println(gson.toJson(multipleChoiceSet));
//        MultipleChoiceQuestion multipleChoiceQuestion = new MultipleChoiceQuestion(1, "What is OOP?", 30);
//multipleChoiceQuestion.setMultipleChoiceSet(multipleChoiceSet);


        //  QuestionOption questionOption = new QuestionOption(true, "Object oriented programing");
        //  QuestionOption questionOption2 = new QuestionOption(false, "Hz");
//        questionOption.setMultipleChoiceQuestion(multipleChoiceQuestion);
//        questionOption2.setMultipleChoiceQuestion(multipleChoiceQuestion);
//
//        multipleChoiceQuestion.setMultipleChoiceSet(multipleChoiceSet);

        //multipleChoiceSet.addQuestion(multipleChoiceQuestion);
        // createMultipleChoiceSet(multipleChoiceSet);

//        List<MultipleChoiceSet> multipleChoiceSetList= null;
//        try {
//            multipleChoiceSetList = questionSetsService.getUsersAllMultipleChoiceSet(user.getUsername());
//        } catch (EmptyQuestionSetTitleOrTopic emptyQuestionSetTitleOrTopic) {
//            emptyQuestionSetTitleOrTopic.printStackTrace();
//        } catch (NullQuestionSet nullQuestionSet) {
//            nullQuestionSet.printStackTrace();
//        } catch (ServiceNotAvailable serviceNotAvailable) {
//            serviceNotAvailable.printStackTrace();
//        } catch (UnexpectedError unexpectedError) {
//            unexpectedError.printStackTrace();
//        }
//        System.out.println(gson.toJson(multipleChoiceSetList));
    }


    @Test
    void createMultipleChoiceSet() {
// Good case
        MultipleChoiceSet createdMultipleChoiceSet = new MultipleChoiceSet("Test", "test", user);
// Null fields
        MultipleChoiceSet createdMultipleChoiceSet2 = new MultipleChoiceSet("Test", "test");
        MultipleChoiceSet createdMultipleChoiceSet3 = new MultipleChoiceSet(null, "test");
//        Create set with not existing user
        MultipleChoiceSet createdMultipleChoiceSet4 = new MultipleChoiceSet(null, "test", new User("NotExist", "NotExist", "NotExist", "NotExist@va.cs", "111111", new Role(1, "Student")));

        ResponseEntity result1 = restUtility.postForEntity(businessTierUrl + "/questionsets/createMultipleChoiceSet", createdMultipleChoiceSet, createdMultipleChoiceSet.getClass());
        assertEquals(result1.getStatusCode(), HttpStatus.OK);

        Assertions.assertThrows(HttpClientErrorException.class, () -> {
            ResponseEntity result2 = restUtility.postForEntity(businessTierUrl + "/questionsets/createMultipleChoiceSet", createdMultipleChoiceSet2, createdMultipleChoiceSet2.getClass());
            assertEquals(result2.getStatusCode(), HttpStatus.BAD_REQUEST, "Null fields");
        });
        Assertions.assertThrows(HttpClientErrorException.class, () -> {
            ResponseEntity result2 = restUtility.postForEntity(businessTierUrl + "/questionsets/createMultipleChoiceSet", createdMultipleChoiceSet3, createdMultipleChoiceSet3.getClass());
            assertEquals(result2.getStatusCode(), HttpStatus.BAD_REQUEST, "Null fields");
        });
        Assertions.assertThrows(HttpClientErrorException.class, () -> {
            ResponseEntity result3 = restUtility.postForEntity(businessTierUrl + "/questionsets/createMultipleChoiceSet", createdMultipleChoiceSet3, createdMultipleChoiceSet3.getClass());
            assertEquals(result3.getStatusCode(), HttpStatus.BAD_REQUEST, "Null fields");
        });
        Assertions.assertThrows(HttpClientErrorException.class, () -> {
            ResponseEntity result3 = restUtility.postForEntity(businessTierUrl + "/questionsets/createMultipleChoiceSet", createdMultipleChoiceSet4, createdMultipleChoiceSet3.getClass());
            assertEquals(result3.getStatusCode(), HttpStatus.BAD_REQUEST, "User that is trying to create the question set is not authorized");
        });

    }

    @Test
    void createWrittenQuestion() {
        WrittenSet writtenSet = new WrittenSet("Test", "Test", user);
        User user1 = new User("Silvestru", "Mandrila", "silvmandrila1", "silvmandr@va.cs", "111111", new Role(1, "Strudent"));
        User user2 = null;
        WrittenSet writtenSet1 = new WrittenSet("Test", "Test", user1);
        WrittenSet writtenSet2 = new WrittenSet(null, "Test", user);
        WrittenSet writtenSet3 = new WrittenSet("", "", user);

        ResponseEntity result1 = restUtility.postForEntity(businessTierUrl + "/questionsets/createWrittenSet", writtenSet, writtenSet.getClass());
        assertEquals(result1.getStatusCode(), HttpStatus.OK);

        Assertions.assertThrows(HttpClientErrorException.class, () -> {
            ResponseEntity result2 = restUtility.postForEntity(businessTierUrl + "/questionsets/createWrittenSet", writtenSet3, writtenSet3.getClass());
            assertEquals(result2.getStatusCode(), HttpStatus.BAD_REQUEST, "Title & Topic are empyu");
        });

        Assertions.assertThrows(HttpClientErrorException.class, () -> {
            ResponseEntity result3 = restUtility.postForEntity(businessTierUrl + "/questionsets/createWrittenSet", writtenSet2, writtenSet2.getClass());
            assertEquals(result3.getStatusCode(), HttpStatus.BAD_REQUEST, "Null fields");
        });
        Assertions.assertThrows(HttpClientErrorException.class, () -> {
            ResponseEntity result3 = restUtility.postForEntity(businessTierUrl + "/questionsets/createWrittenSet", writtenSet1, writtenSet1.getClass());
            assertEquals(result3.getStatusCode(), HttpStatus.UNAUTHORIZED, "null User");
        });

    }

    @Test
    void findMultipleChoiceSet() {
// Good case
        MultipleChoiceSet createdMultipleChoiceSet = new MultipleChoiceSet("Test", "test", user);
// Null fields
        MultipleChoiceSet createdMultipleChoiceSet2 = new MultipleChoiceSet("Test", "test");
        MultipleChoiceSet createdMultipleChoiceSet3 = new MultipleChoiceSet(null, "test");
//        Create set with not existing user
        MultipleChoiceSet createdMultipleChoiceSet4 = new MultipleChoiceSet(null, "test", new User("NotExist", "NotExist", "NotExist", "NotExist@va.cs", "111111", new Role(1, "Student")));

        ResponseEntity result1 = restUtility.getForEntity(businessTierUrl + "/questionsets/findMultipleChoiceSet", createdMultipleChoiceSet.getClass());
        assertEquals(result1.getStatusCode(), HttpStatus.OK);

        Assertions.assertThrows(HttpClientErrorException.class, () -> {
            ResponseEntity result2 = restUtility.getForEntity(businessTierUrl + "/questionsets/findMultipleChoiceSet", createdMultipleChoiceSet2.getClass());
            assertEquals(result2.getStatusCode(), HttpStatus.BAD_REQUEST, "Null fields");
        });
        Assertions.assertThrows(HttpClientErrorException.class, () -> {
            ResponseEntity result2 = restUtility.getForEntity(businessTierUrl + "/questionsets/findMultipleChoiceSet", createdMultipleChoiceSet3.getClass());
            assertEquals(result2.getStatusCode(), HttpStatus.BAD_REQUEST, "Null fields");
        });
        Assertions.assertThrows(HttpClientErrorException.class, () -> {
            ResponseEntity result3 = restUtility.getForEntity(businessTierUrl + "/questionsets/findMultipleChoiceSet", createdMultipleChoiceSet3.getClass());
            assertEquals(result3.getStatusCode(), HttpStatus.BAD_REQUEST, "Null fields");
        });
        Assertions.assertThrows(HttpClientErrorException.class, () -> {
            ResponseEntity result3 = restUtility.getForEntity(businessTierUrl + "/questionsets/findMultipleChoiceSet", createdMultipleChoiceSet3.getClass());
            assertEquals(result3.getStatusCode(), HttpStatus.BAD_REQUEST, "User that is trying to create the question set is not authorized");
        });

    }

    @Test
    void findAllMultipleChoiceSet() {
    }

    @Test
    void findAllWrittenSets() {
    }
}
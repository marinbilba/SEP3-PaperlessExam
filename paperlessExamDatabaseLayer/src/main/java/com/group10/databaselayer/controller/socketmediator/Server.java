package com.group10.databaselayer.controller.socketmediator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.group10.databaselayer.controller.*;
import com.group10.databaselayer.controller.questions.MultipleChoiceQuestionsController;
import com.group10.databaselayer.controller.questions.WrittenQuestionsController;

import com.group10.databaselayer.annotations.hidden.HiddenAnnotationExclusionStrategy;
import com.group10.databaselayer.entity.questions.Question;
import com.group10.databaselayer.entity.questions.multiplechoice.MultipleChoiceQuestion;
import com.group10.databaselayer.entity.questions.multiplechoice.MultipleChoiceSet;
import com.group10.databaselayer.entity.questions.multiplechoice.QuestionOption;
import com.group10.databaselayer.entity.user.User;
import com.group10.databaselayer.exception.questions.QuestionSetNotFound;
import com.group10.databaselayer.exception.questions.TitleOrTopicAreNull;
import com.group10.databaselayer.repositories.user.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class Server {
    private static final int SERVER_PORT = 8000;

    @Autowired
    private RoleController roleController;
    @Autowired
    private UserController userController;

    @Autowired
    private WrittenQuestionsController writtenQuestionsController;

    @Autowired
    private MultipleChoiceQuestionsController multipleChoiceQuestionsController;

    @Autowired
    private IUserRepository userRepository;


    private ExecutorService executorService = Executors.newFixedThreadPool(100);

    private static final HashSet<Object> controllersSet = new HashSet<>();
    private GsonBuilder gson;

    @PostConstruct
    public void init() {
        controllersSet.add(roleController);
        controllersSet.add(userController);
        controllersSet.add(multipleChoiceQuestionsController);
        controllersSet.add(writtenQuestionsController);

        gson = new GsonBuilder();
        gson.setExclusionStrategies(new HiddenAnnotationExclusionStrategy());

        gson.setExclusionStrategies(new HiddenAnnotationExclusionStrategy());
        Gson gson2 = gson.setPrettyPrinting().create();


//        User user = userRepository.getUserByUsername("silvmandrila");
//        MultipleChoiceSet multipleChoiceSet = new MultipleChoiceSet("Java2", "Capitals");
//        multipleChoiceSet.setUser(user);
//        System.out.println(gson2.toJson(multipleChoiceSet));
        //multipleChoiceQuestionsController.createUpdateMultipleChoiceSet(multipleChoiceSet);

//        User user2 = userRepository.getUserByUsername("marinbilba");
//
////        !!!!!!!!!!!!!! Multiple choice
//       MultipleChoiceSet multipleChoiceSet = new MultipleChoiceSet("Java", "Capitals",user2);
//        MultipleChoiceQuestion multipleChoiceQuestion=new MultipleChoiceQuestion(1,"What is OOP?",30);
//       multipleChoiceQuestion.addQuestionOption(new QuestionOption(true,"Object oriented programing"));
//        multipleChoiceQuestion.addQuestionOption(new QuestionOption(false,"Hz"));
//        multipleChoiceSet.addQuestion(multipleChoiceQuestion);
//        System.out.println(gson2.toJson(multipleChoiceSet));
        //multipleChoiceQuestionsController.createUpdateMultipleChoiceSet(multipleChoiceSet);

//        !!!!!!!!!!!!!! Multiple choice

//        MultipleChoiceSet multipleChoiceSet2 = new MultipleChoiceSet("Geography", "Capitals",user2);
//       multipleChoiceQuestionsController.createUpdateMultipleChoiceSet(multipleChoiceSet);
//        multipleChoiceQuestionsController.createUpdateMultipleChoiceSet(multipleChoiceSet2);


        // user.addMultipleChoiceSet(multipleChoiceSet);
        //  userController.deleteUser(user);

        //multipleChoiceQuestionsController.createUpdateMultipleChoiceSet(multipleChoiceSet);

        ///MultipleChoiceQuestion multipleChoiceQuestion = new MultipleChoiceQuestion(1, "Capital of Argentina?", 20,multipleChoiceSet);

        //    MultipleChoiceQuestion multipleChoiceQuestion = new MultipleChoiceQuestion(1, "Capital of Argentina?", 20);
//        multipleChoiceQuestion.addQuestionOption(new QuestionOption(true, "Buenos Aires"));
//        multipleChoiceQuestion.addQuestionOption(new QuestionOption(false, "Berlin"));
        //     multipleChoiceSet.addQuestion(multipleChoiceQuestion);

        //   user.addMultipleChoiceSet(multipleChoiceSet);


//

//

//        System.out.println(user.getFirstName());
//WrittenSet writtenSet=new WrittenSet("ads","dssd",user);
//        WrittenQuestion writtenQuestion=new WrittenQuestion("dsfa2",20,1);
//        writtenSet.addQuestion(writtenQuestion);

//

//
//        String multipleChoiceSetSerialized = gson2.toJson(multipleChoiceSet);
//        System.out.println(multipleChoiceSetSerialized);
//          writtenQuestionsController.createUpdateWrittenSet(multipleChoiceSet);

        runServer();
    }

    public void runServer() {

        try {
            ServerSocket server = new ServerSocket(SERVER_PORT);
            while (true) {
                System.out.println("[SERVER] Waiting for client connection on port " + SERVER_PORT);
                Socket socket = server.accept();
                System.out.println("[SERVER] Connected to client");
                ServerSocketHandler serverSocketHandler = new ServerSocketHandler(socket, controllersSet);
                serverSocketHandler.run();
                // executorService.execute(serverSocketHandler);
                //  executorService.shutdown();


                // serverSocketHandler.start();
                // executorService.execute(serverSocketHandler.run());
                //   executeWithResult(serverSocketHandler);
                //     serverSocketHandler.run();
//                Thread thread=new Thread(new ServerSocketHandler(socket, controllersSet));
//                thread.start();
                System.out.println("[SERVER] Disconnected from client");

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

//    public <T> Future<T> executeWithResult(Callable<T> callable) {
//        return executorService.submit(callable);
//    }


//@Bean
//    private static ServerSocketHandler createSocketHandler(Socket socket, ConnectionPool pool) throws IOException {
//    return new ServerSocketHandler(socket, pool, userController);
//    }

//
//    @Bean
//    Socket createSocket() {
//        return new Socket();
//    }

}

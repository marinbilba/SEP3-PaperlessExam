package com.group10.databaselayer.controller.socketmediator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.group10.databaselayer.controller.*;
import com.group10.databaselayer.controller.questions.MultipleChoiceQuestionsController;
import com.group10.databaselayer.controller.questions.WrittenQuestionsController;
import com.group10.databaselayer.entity.questions.Question;
import com.group10.databaselayer.entity.questions.multiplechoice.MultipleChoiceQuestion;
import com.group10.databaselayer.entity.questions.multiplechoice.MultipleChoiceSet;
import com.group10.databaselayer.entity.questions.multiplechoice.QuestionOption;

import com.group10.databaselayer.entity.questions.written.HiddenAnnotationExclusionStrategy;
import com.group10.databaselayer.entity.questions.written.WrittenQuestion;
import com.group10.databaselayer.entity.questions.written.WrittenSet;
import com.group10.databaselayer.entity.user.User;
import com.group10.databaselayer.exception.questions.QuestionAlreadyExists;
import com.group10.databaselayer.exception.questions.QuestionNotFound;
import com.group10.databaselayer.exception.questions.QuestionSetNotFound;
import com.group10.databaselayer.exception.questions.TitleOrTopicAreNull;
import com.group10.databaselayer.repositories.user.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.lang.reflect.Modifier;
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
        gson = new GsonBuilder();
        // gson.setExclusionStrategies( new HiddenAnnotationExclusionStrategy() );

        gson.setExclusionStrategies(new HiddenAnnotationExclusionStrategy());
        Gson gson2 = gson.setPrettyPrinting().create();


        controllersSet.add(roleController);
        controllersSet.add(userController);
        controllersSet.add(multipleChoiceQuestionsController);

        User user = userRepository.getUserByUsername("silvmandrila");
        System.out.println(user.getFirstName());
//WrittenSet writtenSet=new WrittenSet("ads","dssd",user);
//        WrittenQuestion writtenQuestion=new WrittenQuestion("dsfa2",20,1);
//        writtenSet.addQuestion(writtenQuestion);


        MultipleChoiceSet multipleChoiceSet = new MultipleChoiceSet("Geography", "Capitals", user);

        MultipleChoiceQuestion multipleChoiceQuestion = new MultipleChoiceQuestion(1, "Capital of Argentina?", 20);
        multipleChoiceQuestion.addQuestionOption(new QuestionOption(true, "Buenos Aires"));
        multipleChoiceQuestion.addQuestionOption(new QuestionOption(false, "Berlin"));
        multipleChoiceSet.addQuestion(multipleChoiceQuestion);

        String multipleChoiceSetSerialized = gson2.toJson(multipleChoiceSet);
        System.out.println(multipleChoiceSetSerialized);
//          writtenQuestionsController.createUpdateWrittenSet(multipleChoiceSet);

        runServer();
    }

    public void runServer() {

        try {
            ServerSocket server = new ServerSocket(SERVER_PORT);
            while (true) {
                System.out.println("[SERVER] Waiting for client connection on port " + SERVER_PORT);
                Socket socket = server.accept();
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
                System.out.println("[SERVER] Connected to client");

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

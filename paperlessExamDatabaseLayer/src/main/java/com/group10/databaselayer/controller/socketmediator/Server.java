package com.group10.databaselayer.controller.socketmediator;

import com.group10.databaselayer.controller.*;
import com.group10.databaselayer.controller.questions.MultipleChoiceQuestionsController;
import com.group10.databaselayer.controller.questions.WrittenQuestionsController;
import com.group10.databaselayer.entity.questions.Question;
import com.group10.databaselayer.entity.questions.multiplechoice.MultipleChoiceQuestion;
import com.group10.databaselayer.entity.questions.multiplechoice.MultipleChoiceSet;
import com.group10.databaselayer.entity.questions.multiplechoice.QuestionOption;
import com.group10.databaselayer.exception.questions.QuestionAlreadyExists;
import com.group10.databaselayer.exception.questions.QuestionNotFound;
import com.group10.databaselayer.exception.questions.QuestionSetNotFound;
import com.group10.databaselayer.exception.questions.TitleOrTopicAreNull;
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
    WrittenQuestionsController writtenQuestionsController;

    @Autowired
    MultipleChoiceQuestionsController multipleChoiceQuestionsController;

    private ExecutorService executorService = Executors.newFixedThreadPool(10);

    private static final HashSet<Object> controllersSet = new HashSet<>();

    @PostConstruct
    public void init() {
        controllersSet.add(roleController);
        controllersSet.add(userController);
        runServer();
    }

    public void runServer() {

        try {
            ServerSocket server = new ServerSocket(SERVER_PORT);
            while (true) {
                System.out.println("[SERVER] Waiting for client connection on port " + SERVER_PORT);
                Socket socket = server.accept();
                ServerSocketHandler serverSocketHandler = new ServerSocketHandler(socket, controllersSet);
                executorService.execute(serverSocketHandler);

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

package com.group10.databaselayer.controller.socketmediator;

import com.group10.databaselayer.controller.RoleController;
import com.group10.databaselayer.controller.UserController;
import com.group10.databaselayer.controller.UserControllerTEMO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Component

public class Server {
    private static final int SERVER_PORT = 8000;
    @Autowired
    private UserControllerTEMO userControllerTEMO;
    @Autowired
    private RoleController roleController;
    @Autowired
    private UserController userController;

    @Autowired
    @Qualifier("fixedThreadPool")
    private ExecutorService executorService;

    private static final HashSet<Object> controllersSet = new HashSet<>();

    @PostConstruct
    public void init() {
        controllersSet.add(userControllerTEMO);
        controllersSet.add(roleController);
        controllersSet.add(userController);
        runServer();
    }

@Scope
    public void runServer() {

        try {
            ServerSocket server = new ServerSocket(SERVER_PORT);
            while (true) {
                System.out.println("[SERVER] Waiting for client connection on port " + SERVER_PORT);
                Socket socket = server.accept();
               // ServerSocketHandler serverSocketHandler = new ServerSocketHandler(socket, controllersSet);
             //   executeWithResult(serverSocketHandler);
           //     serverSocketHandler.run();
                Thread thread=new Thread(new ServerSocketHandler(socket, controllersSet));
                thread.start();
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

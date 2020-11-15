package com.group10.databaselayer.controller.socketmediator;

import com.group10.databaselayer.controller.RoleController;
import com.group10.databaselayer.controller.UserController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Component

public class Server {
    private static final int SERVER_PORT = 8000;
    @Autowired
    private UserController userController;
    @Autowired
    private RoleController roleController;
    private static final HashSet<Object> controllersSet = new HashSet<>();

    @PostConstruct
    public void init() throws IOException {
        controllersSet.add(userController);
        controllersSet.add(roleController);
        runServer();
    }

    public void runServer() throws IOException {

        try {
            ServerSocket server = new ServerSocket(SERVER_PORT);
            while (true) {
                System.out.println("[SERVER] Waiting for client connection");
                Socket socket = server.accept();
                ServerSocketHandler socketHandler = new ServerSocketHandler(socket, controllersSet);
                new Thread(socketHandler).start();
                System.out.println("[SERVER] Connected to client");

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
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

package com.group10.databaselayer.controller.socketmediator;

import com.group10.databaselayer.controller.RoleController;
import com.group10.databaselayer.controller.UserController;
import com.group10.databaselayer.controller.UserControllerTEMO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;

@Component

public class Server {
    private static final int SERVER_PORT = 8000;
    @Autowired
    private UserControllerTEMO userControllerTEMO;
    @Autowired
    private RoleController roleController;
    @Autowired
    private UserController userController;


    private static final HashSet<Object> controllersSet = new HashSet<>();

    @PostConstruct
    public void init() throws IOException {
        controllersSet.add(userControllerTEMO);
        controllersSet.add(roleController);
        controllersSet.add(userController);
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

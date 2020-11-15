package com.group10.paperlessexamwebservice.databaserequests.socketmediator;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

@Component
public class SocketConnector implements ISocketConnector {
    public static final String SERVER_IP = "localhost";
    public static final int SERVER_PORT = 8000;
    private Socket socket;
    private OutputStream outputStream;
    private InputStream inputStream;


    public SocketConnector() {
    }

    @Override
    public void connect() throws IOException {
        socket = new Socket(SERVER_IP, SERVER_PORT);
        outputStream = socket.getOutputStream();
        inputStream = socket.getInputStream();

    }

    @Override
    public void disconnect() throws IOException {
        socket.close();
    }

    @Override
    public void sendToServer(String message) throws IOException {
        byte[] responseAsBytes = message.getBytes();
        outputStream.write(responseAsBytes, 0, responseAsBytes.length);
    }

    @Override
    public String readFromServer() throws IOException {
        byte[] lenbytes = new byte[1024];
        int read = inputStream.read(lenbytes, 0, lenbytes.length);
        String message = new String(lenbytes, 0, read);
        return message;
    }
}
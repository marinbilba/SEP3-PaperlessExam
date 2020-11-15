package com.group10.paperlessexamwebservice.databaserequests.socketmediator;

import java.io.IOException;

public interface ISocketConnector {

    void connect() throws IOException;

    void disconnect() throws IOException;

    void sendToServer(String message) throws IOException;

    String readFromServer() throws IOException;
}



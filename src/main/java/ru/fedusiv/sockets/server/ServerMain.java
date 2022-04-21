package ru.fedusiv.sockets.server;

import java.io.IOException;
import java.net.*;
import java.util.Enumeration;

public class ServerMain {

    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.openSocket(22800);
    }

}

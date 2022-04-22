package ru.fedusiv.sockets.server;

public class ServerMain {

    public static void main(String[] args) {
        Server server = new Server();
        server.openSocket(22800);
    }

}

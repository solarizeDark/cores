package ru.fedusiv.sockets.client;

public class ClientMain {

    public static void main(String[] args) {
        Client client = new Client();
        client.start("127.0.0.1", 22800);
    }

}

package ru.fedusiv.sockets.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private ServerSocket serverSocket;
    private List<String> messages = Collections.synchronizedList(new ArrayList<>());

    public void openSocket(Integer port) {
        serverSocket = null;
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        try {
            serverSocket = new ServerSocket(port != null ? port : 0);
            System.out.println(serverSocket.getLocalPort());
            System.out.println(serverSocket.getInetAddress());

            while (true) {
                executorService.submit(() -> {
                    try {
                        handleClient(serverSocket.accept());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }


        } catch (IOException exception) {
            System.err.println(exception);
        } finally {
            try {
                if (serverSocket != null) {
                    serverSocket.close();
                }
            } catch (IOException exception) {
                System.err.println(exception);
            }
        }
    }

    public void handleClient(Socket socket) throws IOException {

        int current = 0;

        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));

        String input;
        while((input = bufferedReader.readLine()) != null) {
            System.out.println(input);
            messages.add(input);

            int size = messages.size();

            if (size > current) {
                for (; current <= size; current++) {
                    out.println(messages.get(current));
                }
            }
        }
    }

}

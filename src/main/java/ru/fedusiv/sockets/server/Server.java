package ru.fedusiv.sockets.server;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Server {

    @Data
    @AllArgsConstructor
    public class Message {

        // client id
        private String clientId;
        private String message;

    }


    private final AtomicInteger clientsAmount = new AtomicInteger(0);
    private final ExecutorService executorService = Executors.newFixedThreadPool(5);
    private List<Message> messages = new ArrayList<>();
    private final Map<String, Integer> counters = new HashMap<>();
    private final Lock messagesLock = new ReentrantLock();

    public void openSocket(Integer port) {
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(port != null ? port : 0);
            System.out.println(serverSocket.getLocalPort());
            System.out.println(serverSocket.getInetAddress());

            Socket newClient;
            while ((newClient = serverSocket.accept()) != null) {
                Socket finalNewClient = newClient;
                String alias = "Client".concat(String.valueOf(clientsAmount.get()));
                clientsAmount.incrementAndGet();
                System.out.println(alias);
                executorService.submit(() -> {
                    try {
                        handleClient(finalNewClient, alias);
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

    public void handleClient(Socket socket, String alias) throws IOException {
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));

        int current;
        try {
            messagesLock.lock();
            current = messages.size();
            counters.put(alias, current);

            messages.subList(0, current).forEach(System.out::println);

            for (Message message : messages.subList(0, current)) {
                out.println(message.getClientId() + ": " + message.getMessage());
            }
        } finally {
            messagesLock.unlock();
        }

//        executorService.submit(() -> sendToClient(out, alias));

        String input;

        while ((input = bufferedReader.readLine()) != null) {
            System.out.println(input);
            Message message = new Message(alias, input);
            try {
                messagesLock.lock();
                messages.add(message);
                System.out.println("m");
                messages.forEach(System.out::println);
            } finally {
                messagesLock.unlock();
            }
        }
    }

    public void sendToClient(PrintWriter out, String alias) {
        int current;
        synchronized (counters) {
            current = counters.get(alias);
        }
        while (current < messages.size()) {
            int old = current;
            current = messages.size();
            for (Message message : messages.subList(old, current)) {
                out.println(message.getClientId() + ": " + message.getMessage());
            }
        }
    }

}

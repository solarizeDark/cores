package ru.fedusiv.sockets.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client {

    public void start(String hostname, Integer port) {

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        Socket socket = null;

        try {
            socket = new Socket(hostname, port);
            PrintWriter socketOut = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            List<Callable<Object>> tasks = new ArrayList<>();
            tasks.add(Executors.callable(() -> send(socketOut)));
            tasks.add(Executors.callable(() -> receive(socketIn)));

            executorService.invokeAll(tasks);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void send (PrintWriter socketOut) {
        Scanner scanner = new Scanner(System.in);
        String consoleInput;
        while ((consoleInput = scanner.nextLine()) != null) {
            socketOut.println(consoleInput);
        }
    }

    public void receive(BufferedReader socketIn) {
        try {
            String socketOutput;
            while ((socketOutput = socketIn.readLine()) != null) {
                System.out.println(socketOutput);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

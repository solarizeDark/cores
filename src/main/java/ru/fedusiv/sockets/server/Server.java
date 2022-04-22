package ru.fedusiv.sockets.server;

import org.apache.commons.collections4.OrderedMap;
import org.apache.commons.collections4.map.LinkedMap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class Server {

    private final OrderedMap<String, String> messages = new LinkedMap<>();
    private Map.Entry<String, String> last;
    private AtomicInteger clientsAmount = new AtomicInteger(0);
    private AtomicInteger currentSize = new AtomicInteger(0);
    private ExecutorService executorService = Executors.newFixedThreadPool(5);

    public void openSocket(Integer port) {
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(port != null ? port : 0);
            System.out.println(serverSocket.getLocalPort());
            System.out.println(serverSocket.getInetAddress());

            Socket newClient;
            while ((newClient = serverSocket.accept()) != null) {
                Socket finalNewClient = newClient;
                executorService.submit(() -> {
                    try {
                        handleClient(finalNewClient);
                        clientsAmount.incrementAndGet();
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
        String alias = "Client".concat(String.valueOf(clientsAmount.get()));

        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));

        synchronized (messages) {
            for (Map.Entry<String, String> entry : messages.entrySet()) {
                out.println(entry.getKey().concat(": ").concat(entry.getValue()));
                last = entry;
            }

            currentSize.set(messages.size());
        }

        System.out.println("initial sent");
        executorService.submit(() -> sendToClient(out));

        String input;
        while ((input = bufferedReader.readLine()) != null) {
            System.out.println(input);

            messages.put(alias, input);
        }
    }

    public void sendToClient(PrintWriter out) {
        while (true) {
            synchronized (messages) {
                int current = currentSize.get();
                if (current < messages.size()) {
                    for (String key = messages.nextKey(last.getKey());
                         key != messages.nextKey(messages.lastKey()); key = messages.nextKey(key)) {
                        out.println(key.concat(": ").concat(messages.get(key)));
                    }
                }
            }
        }
    }

}

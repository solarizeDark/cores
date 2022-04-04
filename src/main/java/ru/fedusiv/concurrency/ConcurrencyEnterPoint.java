package ru.fedusiv.concurrency;

import java.io.*;
import java.net.URL;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class ConcurrencyEnterPoint {

    static Consumer<URL> imageDownloader =
        url ->
            {
                String name = url.getPath();
                name = name.substring(name.lastIndexOf("/") + 1);

                long stamp = Thread.currentThread().getId();

                String randomTag = UUID.randomUUID().toString();

                try (InputStream inputStream = url.openStream();
                        OutputStream outputStream =
                                new FileOutputStream("images\\" + stamp + "_" + randomTag + "_" + name)) {

                    System.out.format("Thread #%s loading image\n", stamp);

                    int n;
                    while ((n = inputStream.read()) != -1) {
                        outputStream.write(n);
                    }
                } catch (FileNotFoundException e) {
                    throw new IllegalArgumentException(e);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            };


    public static void main(String[] args) throws IOException {
        ExecutorService threadPool = Executors.newFixedThreadPool(5);

        FileReader fileReader = new FileReader("images.txt");
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        for (String current = bufferedReader.readLine(); current != null;
             current = bufferedReader.readLine()) {
            String finalCurrent = current;
            threadPool.submit(() -> {
                try {
                    imageDownloader.accept(new URL(finalCurrent));
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            });
        }
    }

}

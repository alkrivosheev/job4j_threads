package ru.job4j.concurrent;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;

public class Wget implements Runnable {
    private final String url;
    private final int speed;
    private static final String FILE_NAME = "tmp.xml";

    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
        var startAt = System.currentTimeMillis();
        var file = new File(FILE_NAME);
        try (var input = new URL(this.url).openStream();
             var output = new FileOutputStream(file)) {
            System.out.println("Open connection: " + (System.currentTimeMillis() - startAt) + " ms");
            var dataBuffer = new byte[512];
            int bytesRead;
            long totalBytesRead = 0;
            long startTime = System.currentTimeMillis();
            while ((bytesRead = input.read(dataBuffer, 0, dataBuffer.length)) != -1) {
                var downloadAt = System.nanoTime();
                output.write(dataBuffer, 0, bytesRead);
                System.out.println("Read bytes : " + (System.nanoTime() - downloadAt) + " nano.");
                totalBytesRead += bytesRead;
                if (totalBytesRead >= speed) {
                    long elapsedTime = System.currentTimeMillis() - startTime;
                    long pauseTime = Math.max(0, speed - elapsedTime);
                    if (pauseTime > 0) {
                        System.out.println("Pausing for " + pauseTime + " ms");
                        Thread.sleep(pauseTime);
                    }
                    startTime = System.currentTimeMillis();
                    totalBytesRead = 0;
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        try {
            System.out.println(Files.size(file.toPath()) + " bytes");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        if (args.length != 2) {
            throw new IllegalArgumentException("Usage: java Wget <url> <speed>");
        }
        String url = args[0];
        int speed = 0;
        if (url == null || url.isEmpty()) {
            throw new IllegalArgumentException("Error: URL cannot be null or empty.");
        }
        try {
            speed = Integer.parseInt(args[1]);
            if (speed <= 0) {
                throw new IllegalArgumentException("Error: Speed must be a positive integer.");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Error: Speed must be a valid integer.");
        }
        Thread wget = new Thread(new Wget(url, speed));
        wget.start();
        wget.join();
    }
}
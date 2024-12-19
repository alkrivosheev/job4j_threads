package ru.job4j.concurrent;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;

public class Wget implements Runnable {
    private final String url;
    private final int speed;

    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
        var startAt = System.currentTimeMillis();
        var file = new File("tmp.xml");
        try (var input = new URL(this.url).openStream();
             var output = new FileOutputStream(file)) {
            System.out.println("Open connection: " + (System.currentTimeMillis() - startAt) + " ms");
            var dataBuffer = new byte[512];
            int bytesRead;
            while ((bytesRead = input.read(dataBuffer, 0, dataBuffer.length)) != -1) {
                var downloadAt = System.nanoTime();
                output.write(dataBuffer, 0, bytesRead);
                System.out.println("Read bytes : " + (System.nanoTime() - downloadAt) + " nano.");
            }
        } catch (IOException e) {
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
            System.err.println("Usage: java Wget <url> <speed>");
            System.exit(1);
        }
        String url = args[0];
        int speed = 0;
        if (url == null || url.isEmpty()) {
            System.err.println("Error: URL cannot be null or empty.");
            System.exit(1);
        }
        try {
            speed = Integer.parseInt(args[1]);
            if (speed <= 0) {
                System.err.println("Error: Speed must be a positive integer.");
                System.exit(1);
            }
        } catch (NumberFormatException e) {
            System.err.println("Error: Speed must be a valid integer.");
            System.exit(1);
        }
        Thread wget = new Thread(new Wget(url, speed));
        wget.start();
        wget.join();
    }
}
package ru.job4j.skillbox.imageresizer.runnablehomework;

import ru.job4j.skillbox.imageresizer.thread.ImageResizer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static  int newWidth = 300;

    public static void main(String[] args) {
        String srcFolder = "C:\\Users\\Alex\\src";
        String dstFolder = "C:\\Users\\Alex\\dst";

        File srcDir = new File(srcFolder);
        long start = System.currentTimeMillis();
        File[] files = srcDir.listFiles();
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        int filesPerThread = (int) Math.ceil((double) files.length / availableProcessors);
        List<List<File>> fileBatches = new ArrayList<>();

        for (int i = 0; i < availableProcessors; i++) {
            int fromIndex = i * filesPerThread;
            int toIndex = Math.min(fromIndex + filesPerThread, files.length);
            List<File> batch = new ArrayList<>();
            for (int j = fromIndex; j < toIndex; j++) {
                batch.add(files[j]);
            }
            fileBatches.add(batch);
        }

        for (List<File> batch : fileBatches) {
            File[] batchArray = batch.toArray(new File[0]);
            ImageResizer resizer = new ImageResizer(batchArray, newWidth, dstFolder, start);
            new Thread(resizer).start();
        }

    }
}

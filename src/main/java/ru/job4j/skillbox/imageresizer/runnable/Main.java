package ru.job4j.skillbox.imageresizer.runnable;

import ru.job4j.skillbox.imageresizer.thread.ImageResizer;

import java.io.File;

public class Main {
    private static  int newWidth = 300;

    public static void main(String[] args) {
        String srcFolder = "C:\\Users\\Alex\\src";
        String dstFolder = "C:\\Users\\Alex\\dst";

        File srcDir = new File(srcFolder);
        long start = System.currentTimeMillis();
        File[] files = srcDir.listFiles();
        int middle = files.length / 2;
        File[] files1 = new File[middle];
        System.arraycopy(files, 0, files1, 0, files1.length);
        ru.job4j.skillbox.imageresizer.thread.ImageResizer resizer1 = new ru.job4j.skillbox.imageresizer.thread.ImageResizer(files1, newWidth, dstFolder, start);
        new Thread(resizer1).start();

        File[] files2 = new File[files.length - middle];
        System.arraycopy(files, middle, files2, 0, files2.length);
        ru.job4j.skillbox.imageresizer.thread.ImageResizer resizer2 = new ImageResizer(files2, newWidth, dstFolder, start);
        new Thread(resizer2).start();

    }
}

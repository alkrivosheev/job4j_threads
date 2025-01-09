package ru.job4j.io;

import java.io.File;
import java.io.IOException;
import java.util.function.Predicate;

public class Main {
    public static void main(String[] args) {
        File file = new File("example.txt");

        Predicate<Character> fullContentFilter = character -> true;
        ContentReader fullContent = new FilteredContentReader(fullContentFilter);
        FileReader fullContentReader = new FileReader(file, fullContent);
        try {
            String content = fullContentReader.getContent();
            System.out.println("Full Content: " + content);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Predicate<Character> nonUnicodeFilter = character -> character < 0x80;
        ContentReader nonUnicodeContent = new FilteredContentReader(nonUnicodeFilter);
        FileReader nonUnicodeContentReader = new FileReader(file, nonUnicodeContent);
        try {
            String content = nonUnicodeContentReader.getContent();
            System.out.println("Non-Unicode Content: " + content);
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileWriter fileWriter = new FileWriter(file);
        try {
            fileWriter.saveContent("New content");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

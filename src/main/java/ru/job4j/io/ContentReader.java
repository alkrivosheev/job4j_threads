package ru.job4j.io;

import java.io.BufferedInputStream;
import java.io.IOException;

import java.util.function.Predicate;

interface ContentReader {
    String readContent(BufferedInputStream input) throws IOException;
}

class FilteredContentReader implements ContentReader {
    private final Predicate<Character> filter;

    public FilteredContentReader(Predicate<Character> filter) {
        this.filter = filter;
    }

    @Override
    public String readContent(BufferedInputStream input) throws IOException {
        StringBuilder output = new StringBuilder();
        int data;
        while ((data = input.read()) != -1) {
            char character = (char) data;
            if (filter.test(character)) {
                output.append(character);
            }
        }
        return output.toString();
    }
}

package ru.job4j.io;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public final class FileReader {
    private final File file;
    private final ContentReader contentReader;

    public FileReader(File file, ContentReader contentReader) {
        if (file == null) {
            throw new IllegalArgumentException("File cannot be null");
        }
        if (contentReader == null) {
            throw new IllegalArgumentException("Strategy cannot be null");
        }
        this.file = file;
        this.contentReader = contentReader;
    }

    public String getContent() throws IOException {
        try (BufferedInputStream input = new BufferedInputStream(new FileInputStream(file))) {
            return contentReader.readContent(input);
        }
    }
}


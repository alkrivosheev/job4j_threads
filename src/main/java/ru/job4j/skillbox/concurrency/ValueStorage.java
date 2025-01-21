package ru.job4j.skillbox.concurrency;

public class ValueStorage {
    private static int value;

    public static void incrementValue() {
        value = value + 1;
    }

    public static int getValue() {
        return value;
    }
}

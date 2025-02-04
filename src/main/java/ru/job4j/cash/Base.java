package ru.job4j.cash;

public record Base(int id, String name, int version) {
    @Override
    public int id() {
        return id;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public int version() {
        return version;
    }
}

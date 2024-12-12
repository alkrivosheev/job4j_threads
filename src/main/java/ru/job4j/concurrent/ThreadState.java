package ru.job4j.concurrent;

public class ThreadState {
    public static void main(String[] args) {
        Thread first = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );
        Thread second = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );
        first.start();
        second.start();
        try {
            while (first.getState() != Thread.State.TERMINATED || second.getState() != Thread.State.TERMINATED) {
                Thread.sleep(1);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Работа завершена");
    }
}

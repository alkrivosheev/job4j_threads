package ru.job4j.pool;

public class ThreadPoolExample {
    public static void main(String[] args) {
        ThreadPool pool = new ThreadPool();

        for (int i = 0; i < 10; i++) {
            int taskId = i;
            pool.work(() -> {
                System.out.println("Task " + taskId + " is running on thread " + Thread.currentThread().getName());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        pool.shutdown();
    }
}
package ru.job4j.buffer;

public class ParallelSearch {
    public static void main(String[] args) {
    SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<Integer>(3);
    final Thread consumer = new Thread(() -> {
        boolean done = false;
        while (!done) {
            try {
                Integer item = queue.poll();
                if (item == -1) {
                    done = true;
                } else {
                    System.out.println(item);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
    });
        consumer.start();
        Thread producer = new Thread(() -> {
            try {
                for (int index = 0; index < 3; index++) {
                    queue.offer(index);
                    Thread.sleep(500);
                }
                queue.offer(-1); // Сигнал о завершении работы
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        });
        producer.start();
    }
}

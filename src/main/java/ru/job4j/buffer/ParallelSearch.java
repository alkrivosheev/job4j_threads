package ru.job4j.buffer;

public class ParallelSearch {
    public static void main(String[] args) throws InterruptedException {
    SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<Integer>(3);
    final Thread consumer = new Thread(() -> {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                System.out.println(queue.poll());
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    });
        consumer.start();
        Thread producer = new Thread(() -> {
            try {
                for (int index = 0; index < 3; index++) {
                    queue.offer(index);
                    Thread.sleep(500);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        producer.start();
        producer.join();
        consumer.interrupt();
    }
}

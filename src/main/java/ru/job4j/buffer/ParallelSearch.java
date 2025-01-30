package ru.job4j.buffer;

public class ParallelSearch {
    public static void main(String[] args) {
    SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<Integer>(3);
    final Thread consumer = new Thread(() -> {
        try {
            Integer item = queue.poll();
            do {
                System.out.println(item);
                item = queue.poll();
            } while (item != null);
        } catch (InterruptedException e) {
            e.printStackTrace();
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
                queue.offer(null);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        });
        producer.start();
    }
}

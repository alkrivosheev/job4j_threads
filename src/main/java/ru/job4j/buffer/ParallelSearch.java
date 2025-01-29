package ru.job4j.buffer;

public class ParallelSearch {
    public static void main(String[] args) {
    SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<Integer>(3);
    final Thread consumer = new Thread(() -> {
        while (true) {
            try {
                Integer item = queue.poll();
                if (item == null) {
                    break;
                }
                System.out.println(item);
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
                queue.offer(null);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        });
        producer.start();
    }
}

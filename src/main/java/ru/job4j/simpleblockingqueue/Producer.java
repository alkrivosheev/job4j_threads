package ru.job4j.simpleblockingqueue;

public class Producer implements Runnable {
    private final SimpleBlockingQueue<Integer> queue;

    public Producer(SimpleBlockingQueue<Integer> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            int value = 0;
            while (true) {
                queue.offer(value++);
                System.out.println("Produced: " + (value - 1));
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
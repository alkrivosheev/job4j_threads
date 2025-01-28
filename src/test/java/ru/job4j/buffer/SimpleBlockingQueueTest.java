package ru.job4j.buffer;


import org.junit.Test;
import static org.junit.Assert.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.IntStream;
import static org.assertj.core.api.Assertions.assertThat;

public class SimpleBlockingQueueTest {

    @Test
    public void testProducerConsumer() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(5);
        Thread producerThread = new Thread(new Producer(queue));
        Thread consumerThread = new Thread(new Consumer(queue));
        producerThread.start();
        consumerThread.start();
        Thread.sleep(1000);
        synchronized (queue) {
            assertFalse(queue.isEmpty());
            assertFalse(queue.size() == queue.getCapacity());
        }
        producerThread.interrupt();
        consumerThread.interrupt();
        producerThread.join();
        consumerThread.join();
    }

    @Test
    public void testBlockingWhenFull() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(2);
        Thread producerThread = new Thread(() -> {
            try {
                for (int i = 0; i < 5; i++) {
                    queue.offer(i);
                    System.out.println("Produced: " + i);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        Thread consumerThread = new Thread(() -> {
            try {
                Thread.sleep(500);
                for (int i = 0; i < 5; i++) {
                    Integer value = queue.poll();
                    System.out.println("Consumed: " + value);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        producerThread.start();
        consumerThread.start();
        producerThread.join();
        consumerThread.join();
        synchronized (queue) {
            assertTrue(queue.isEmpty());
        }
    }

    @Test
    public void testBlockingWhenEmpty() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(2);
        Thread consumerThread = new Thread(() -> {
            try {
                for (int i = 0; i < 5; i++) {
                    Integer value = queue.poll();
                    System.out.println("Consumed: " + value);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        Thread producerThread = new Thread(() -> {
            try {
                Thread.sleep(500);
                for (int i = 0; i < 5; i++) {
                    queue.offer(i);
                    System.out.println("Produced: " + i);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        consumerThread.start();
        producerThread.start();
        consumerThread.join();
        producerThread.join();
        synchronized (queue) {
            assertTrue(queue.isEmpty());
        }
    }

    @Test
    public void testConsumer() throws InterruptedException {
        final CopyOnWriteArrayList<Integer> buffer = new CopyOnWriteArrayList<>();
        final SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(6);
        Thread producer = new Thread(
                () -> {
                    IntStream.range(0, 5).forEach(
                            queue :: safeOffer
                    );
                }
        );
        producer.start();
        Thread consumer = new Thread(
                () -> {
                    while (!queue.isEmpty() || !Thread.currentThread().isInterrupted()) {
                        try {
                            buffer.add(queue.poll());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        consumer.start();
        producer.join();
        consumer.interrupt();
        consumer.join();
        assertThat(buffer).containsExactly(0, 1, 2, 3, 4);
    }

    @Test
    public void testConsumerWithLargerQueue() throws InterruptedException {
        final CopyOnWriteArrayList<Integer> buffer = new CopyOnWriteArrayList<>();
        final SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(10);
        Thread producer = new Thread(
                () -> {
                    IntStream.range(0, 8).forEach(queue::safeOffer);
                }
        );
        Thread consumer = new Thread(
                () -> {
                    while (!queue.isEmpty() || !Thread.currentThread().isInterrupted()) {
                        try {
                            buffer.add(queue.poll());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        producer.start();
        consumer.start();
        producer.join();
        consumer.interrupt();
        consumer.join();
        assertThat(buffer).containsExactly(0, 1, 2, 3, 4, 5, 6, 7);
    }
}
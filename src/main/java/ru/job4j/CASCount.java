package ru.job4j;

import net.jcip.annotations.ThreadSafe;
import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class CASCount {
    private final AtomicReference<Integer> count = new AtomicReference<>(0);

    public void increment() {
        Integer current;
        Integer next;
        do {
            current = count.get();
            next = current + 1;
        } while (!count.compareAndSet(current, next));
    }

    public int get() {
        return count.get();
    }

    public static void main(String[] args) {
        CASCount casCount = new CASCount();
        for (int i = 0; i < 4; i++) {
            new Thread(() -> {
               for (int j = 0; j < 100000; j++) {
                   casCount.increment();
               }
                System.out.println(casCount.get());
            }).start();
        }
    }
}

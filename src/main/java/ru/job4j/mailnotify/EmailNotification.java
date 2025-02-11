package ru.job4j.mailnotify;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailNotification {
    private final ExecutorService pool;

    public EmailNotification(int poolSize) {
        this.pool = Executors.newFixedThreadPool(poolSize);
    }

    public void emailTo(User user) {
        pool.submit(() -> {
            String subject = String.format("Notification %s to email %s", user.getUsername(), user.getEmail());
            String body = String.format("Add a new event to %s", user.getUsername());
            send(subject, body, user.getEmail());
        });
    }

    public void close() {
        pool.shutdown();
        while (!pool.isTerminated()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void send(String subject, String body, String email) {
        /*
        *TODO Реализовать метод отправки сообщений на почтовый сервер
        * - а пока заглушка с  выводом в терминал
        */
        System.out.println("Sending email to: " + email);
        System.out.println("Subject: " + subject);
        System.out.println("Body: " + body);
    }

    public static void main(String[] args) {
        EmailNotification notification = new EmailNotification(5);

        User user1 = new User("Alice", "alice@yandex.ru");
        User user2 = new User("Bob", "bob@mail.ru");
        notification.emailTo(user1);
        notification.emailTo(user2);
        notification.close();
    }
}


package ru.job4j.cash;

import java.util.HashMap;
import java.util.Optional;

public class AccountStorage {
    private final HashMap<Integer, Account> accounts = new HashMap<>();

    public synchronized boolean add(Account account) {
        boolean result = false;
        try {
            accounts.put(account.id(), account);
            result = true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public synchronized boolean update(Account account) {
        return add(account);
    }

    public synchronized void delete(int id) {
        accounts.remove(id);
    }

    public synchronized Optional<Account> getById(int id) {
        Optional<Account> result;
        try {
            result = Optional.ofNullable(accounts.get(id));
        } catch (Exception e) {
            throw new RuntimeException("Not found account by id = " + id);
        }
        return result;
    }

    private synchronized void extracted(int id) {
        accounts.get(id);
    }

    public boolean transfer(int fromId, int toId, int amount) {
        boolean result = false;
        try {
            Account fromAccount = accounts.get(fromId);
            Account toAccount = accounts.get(toId);
            if (fromAccount != null && toAccount != null && fromAccount.amount() >= amount) {
                update(new Account(fromId, fromAccount.amount() - amount));
                update(new Account(toId, toAccount.amount() + amount));
            }
            result = true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}


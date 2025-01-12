package ru.job4j.cash;

import java.util.HashMap;
import java.util.Optional;

public class AccountStorage {
    private final HashMap<Integer, Account> accounts = new HashMap<>();

    public synchronized boolean add(Account account) {
            if (accounts.putIfAbsent(account.id(), account) == null) {
                return true;
            }
            return false;
    }

    public synchronized boolean update(Account account) {
        if (accounts.replace(account.id(), account) != null) {
            return true;
        }
        return false;
    }

    public synchronized void delete(int id) {
        accounts.remove(id);
    }

    public synchronized Optional<Account> getById(int id) {
            return Optional.ofNullable(accounts.get(id));
    }

    private synchronized void extracted(int id) {
        accounts.get(id);
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        boolean result = false;
        Account fromAccount = accounts.get(fromId);
        Account toAccount = accounts.get(toId);
        if (fromAccount != null && toAccount != null && fromAccount.amount() >= amount && amount >= 0) {
            update(new Account(fromId, fromAccount.amount() - amount));
            update(new Account(toId, toAccount.amount() + amount));
            result = true;
        }
        return result;
    }
}


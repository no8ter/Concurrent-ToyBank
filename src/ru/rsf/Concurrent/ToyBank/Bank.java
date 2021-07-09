package ru.rsf.Concurrent.ToyBank;

import java.util.concurrent.atomic.AtomicInteger;

public class Bank {

    // Singleton сразу при инициализации класса
    private static final Bank instance = new Bank();
    private Bank() {}
    public static Bank getInstance() {
        return instance;
    }

    // AtomicInteger для того чтобы операции изменения баланса были атомарны
    // final по рекомендации Idea
    private final AtomicInteger storage = new AtomicInteger();

    public int getStorage() {
        return storage.get();
    }

    public int repayMoney(int money) {
        return storage.addAndGet(money);
    }

    public int creditMoney(int money) {
        if (storage.get() < money) {
            throw new NotEnoughMoneyException(""+getStorage());
        } else {
            return storage.addAndGet(-money);
        }
    }
}

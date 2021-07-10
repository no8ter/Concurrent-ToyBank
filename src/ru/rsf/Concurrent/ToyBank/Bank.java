package ru.rsf.Concurrent.ToyBank;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Bank {

    // Singleton сразу при инициализации класса
    private static final Bank instance = new Bank();
    private Bank() {}
    public static Bank getInstance() {
        return instance;
    }
    private final Lock lock = new ReentrantLock();
    private final Condition cond = lock.newCondition();
    private final boolean warmedUp = false;

    // AtomicInteger для того чтобы операции изменения баланса были атомарны
    // final по рекомендации Idea
    private final AtomicInteger storage = new AtomicInteger();

    public int getBalance() {
        return storage.get();
    }

    public  void warmingUp(int startMoney) {
        if (!warmedUp) {
            storage.set(startMoney);
        } else {
            throw new RuntimeException("Bank was warmed up already.");
        }
    }

    public void runTask(Task task, String sender) {
        lock.lock();
        try {
            switch (task.operation) {
                case REPAYMENT:
                    System.out.println("Бек система: Запрос "+task+" УСПЕШНО ВЫПОЛНЕНА. Получена от "+sender+". Баланс банка: "+storage.addAndGet(task.money));
                    break;
                case CREDIT:
                    if (storage.get() >= task.money) {
                        System.out.println("Бек система: Запрос "+task+" УСПЕШНО ВЫПОЛНЕНА. Получена от "+sender+". Баланс банка: "+storage.addAndGet(-task.money));
                    } else {
                        System.out.println("Бек система: Запрос "+task+" НЕ ВЫПОЛНЕНА. Недостаточно средств. Получена от "+sender+". Баланс банка: "+storage.get());
                    }
                    break;
            }
            cond.signalAll();
        } finally {
            lock.unlock();
        }

    }
}

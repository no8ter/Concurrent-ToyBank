package ru.rsf.Concurrent.ToyBank;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class TaskManager implements Runnable{
    Bank bank = Bank.getInstance();
    FrontSystem fs = FrontSystem.getInstance();
    Condition condition = FrontSystem.getInputCodition();
    Lock lock = FrontSystem.getLock();

    private Task currentTask = null;

    @Override
    public void run(){
        lock.lock();
        // WIP
    }
}

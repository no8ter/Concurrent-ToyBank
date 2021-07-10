package ru.rsf.Concurrent.ToyBank;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FrontSystem {

    // Выбрал ArrayBlockingQueue потому что:
    //  1) есть возможность выбрать размер очереди (требуется по заданию);
    //  2) очередь блокируемая - потоки точно дождутся момента когда они смогут в неё положить/взять;
    //  3) очередь реализует "честный" подбор, что полезно для наглядности работы
    private final BlockingQueue<Task> queue = new ArrayBlockingQueue<>(2, true);

    private static final FrontSystem instance = new FrontSystem();
    private FrontSystem() {}
    public static FrontSystem getInstance() {
        return instance;
    }

    // Два условия для отдельной блокировки клиентов и менеджеров
    private static final Lock lock = new ReentrantLock();
    private static final Condition cond = lock.newCondition();

    public void putInQueue(Task newTask){
        lock.lock();
        try {
            while (queue.size() >= 2) {
                cond.await();
            }
            queue.put(newTask);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public Task pullFromQueue(){
        lock.lock();
        try {
            while (queue.size() < 1) {
                cond.await();
            }
            return queue.poll();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        } finally {
            cond.signalAll();
            lock.unlock();
        }
    }
}

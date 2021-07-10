package ru.rsf.Concurrent.ToyBank;

public class Client implements Runnable{

    private final Task task;
    private final FrontSystem fs = FrontSystem.getInstance();

    public Client(String name, Task task) {
        this.task = task;

        this.task.owner = name;
    }

    @Override
    public void run() {
        fs.putInQueue(task);
        System.out.println(task.owner+": Запрос "+task+" отправлен в банк.");
    }
}

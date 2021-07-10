package ru.rsf.Concurrent.ToyBank;

public class TaskManager implements Runnable{
    Bank bank = Bank.getInstance();
    FrontSystem fs = FrontSystem.getInstance();

    private boolean stop;
    private final String name;

    public TaskManager(String name) {
        this.name = name;
    }

    @Override
    public void run(){
        while (!stop) {
            Task currentTask = fs.pullFromQueue();
            System.out.println(name+": Получена заявка на обработку по клиенту - "+ currentTask.owner);
            bank.runTask(currentTask, name);
        }
    }

    public void stop(){
        stop = true;
    }
}

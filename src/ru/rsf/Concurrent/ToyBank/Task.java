package ru.rsf.Concurrent.ToyBank;

public class Task {
    public int money;
    public String owner;
    public Orders operation;

    public Task(String owner, int money, Orders operation) {
        this.owner = owner;
        this.money = money;
        this.operation = operation;
    }
}

package ru.rsf.Concurrent.ToyBank;

public class Task {
    public int money;
    public String owner;
    public Orders operation;

    public Task(int money, Orders operation) {
        this.money = money;
        this.operation = operation;
    }

    @Override
    public String toString() {
        return "{money=" + money +
                ", owner='" + owner + '\'' +
                ", operation=" + operation +
                '}';
    }
}

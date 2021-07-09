package ru.rsf.Concurrent.ToyBank;

public class NotEnoughMoneyException extends RuntimeException{
    static final long serialVersionUID = 1000757419460592865L;

    public NotEnoughMoneyException(){}

    public NotEnoughMoneyException(String message) {
        super(message);
    }
}

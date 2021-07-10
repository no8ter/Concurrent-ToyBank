package ru.rsf.Concurrent.ToyBank;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static java.util.Arrays.asList;

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        List<Task> tasks = new ArrayList<>(asList(
                new Task(1000, Orders.REPAYMENT),
                new Task(3000, Orders.REPAYMENT),
                new Task(3000, Orders.REPAYMENT),
                new Task(9000, Orders.CREDIT),
                new Task(1000, Orders.CREDIT)
        ));

        // Создаём CompletableFuture для прогрева банка
        CompletableFuture<Integer> warmingSumm1 = CompletableFuture.supplyAsync(new WarmingService());
        CompletableFuture<Integer> warmingSumm2 = CompletableFuture.supplyAsync(new WarmingService());
        CompletableFuture<Integer> warmingSumm3 = CompletableFuture.supplyAsync(new WarmingService());

        CompletableFuture<Void> waitWarming = CompletableFuture.allOf(warmingSumm1, warmingSumm2, warmingSumm3);

        try {
            Bank.getInstance().warmingUp(warmingSumm1.get() + warmingSumm2.get() + warmingSumm3.get());
        } catch (ExecutionException | InterruptedException e) {
            System.err.println(e);
        }

        System.out.println("Стартовый баланс банка: "+Bank.getInstance().getBalance());

        ExecutorService clients = Executors.newFixedThreadPool(tasks.size());

        List<Future<?>> clientsFutures = new ArrayList<>();

        int number = 1;
        for (Task task: tasks) {
            clientsFutures.add(clients.submit(new Client("Клиент №"+number, task)));
            number++;
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ExecutorService managers = Executors.newFixedThreadPool(2);
        for (int i=0; i<2; i++) {
            managers.submit(new TaskManager("Обработчик заявок №"+(i+1)));
        }

        for (Future f: clientsFutures) {
            f.get();
        }
    }
}

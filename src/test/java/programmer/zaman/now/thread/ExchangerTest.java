package programmer.zaman.now.thread;

import org.junit.jupiter.api.Test;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ExchangerTest {

    @Test
    void test() throws InterruptedException {
        final Exchanger exchanger = new Exchanger<String>();
        final ExecutorService executor = Executors.newFixedThreadPool(10);

        executor.execute(() -> {
            try {
                System.out.println("Thread 1 : Send : First");
                Thread.sleep(1000);
                Object result = exchanger.exchange("First");
                System.out.println("Thread 1 : Receive : " + result);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        executor.execute(() -> {
            try {
                System.out.println("Thread 2 : Send : Second");
                Thread.sleep(2000);
                Object result = exchanger.exchange("Second");
                System.out.println("Thread 2 : Receive : " + result);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        executor.awaitTermination(1, TimeUnit.DAYS);
    }
}

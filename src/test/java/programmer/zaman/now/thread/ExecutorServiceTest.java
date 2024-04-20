package programmer.zaman.now.thread;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ExecutorServiceTest {

    @Test
    void testExecutorService() throws InterruptedException {

        var executor = Executors.newSingleThreadExecutor();

        for (int index = 0; index < 100; index++) {
            executor.execute(() -> {
                try {
                    Thread.sleep(1000);
                    System.out.println("Runnable in thread : " + Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        executor.awaitTermination(1, TimeUnit.DAYS);

    }

    @Test
    void testExecutorServiceFix() throws InterruptedException {

        var executor = Executors.newFixedThreadPool(10);

        for (int index = 0; index < 100; index++) {
            executor.execute(() -> {
                try {
                    Thread.sleep(1000);
                    System.out.println("Runnable in thread : " + Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.DAYS);
    }
}

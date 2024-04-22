package programmer.zaman.now.thread;

import org.junit.jupiter.api.Test;

import java.util.concurrent.*;

public class CyclicBarrierTest {

    @Test
    void test() throws InterruptedException {

        final CyclicBarrier cyclicBarrier = new CyclicBarrier(5);
        final ExecutorService executor = Executors.newFixedThreadPool(10);

        for (int index = 0; index < 5; index++) {
            executor.execute(() -> {
                try {
                    System.out.println("Waiting");
                    cyclicBarrier.await();
                    System.out.println("Done Waiting");
                } catch (InterruptedException | BrokenBarrierException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        executor.awaitTermination(1, TimeUnit.DAYS);
    }
}

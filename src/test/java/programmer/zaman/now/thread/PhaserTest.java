package programmer.zaman.now.thread;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

public class PhaserTest {

    @Test
    void countDownLatch() throws InterruptedException {

        final Phaser phaser = new Phaser();
        final ExecutorService executor = Executors.newFixedThreadPool(15);

        phaser.bulkRegister(5);
        phaser.bulkRegister(5);

        for (int index = 0; index < 10; index++) {
            executor.execute(() -> {
                try {
                    System.out.println("Start Task");
                    Thread.sleep(2000);
                    System.out.println("End Task");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    phaser.arrive();
                }
            });
        }

        executor.execute(() -> {
            phaser.awaitAdvance(0);
            System.out.println("All tasks done");
        });

        executor.awaitTermination(1, TimeUnit.DAYS);
    }

    @Test
    void cyclicBarrier() throws InterruptedException {

        final Phaser phaser = new Phaser();
        final ExecutorService executor = Executors.newFixedThreadPool(15);

        phaser.bulkRegister(5);

        for (int index = 0; index < 5; index++) {
            executor.execute(() -> {
                phaser.arriveAndAwaitAdvance();
                System.out.println("Done");
            });
        }

        executor.awaitTermination(1, TimeUnit.DAYS);

    }
}

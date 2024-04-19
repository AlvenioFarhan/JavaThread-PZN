package programmer.zaman.now.thread;

import org.junit.jupiter.api.Test;

import java.util.concurrent.*;

public class ThreadpoolTest {

    @Test
    void create() {

        int minThread = 10;
        int maxThread = 100;
        int alive = 1;
        TimeUnit aliveTime = TimeUnit.MINUTES;
        ArrayBlockingQueue queue = new ArrayBlockingQueue<Runnable>(100);

        ThreadPoolExecutor executor = new ThreadPoolExecutor(minThread, maxThread, alive, aliveTime, queue);
    }

    @Test
    void executeRunnable() throws InterruptedException {

        int minThread = 10;
        int maxThread = 100;
        int alive = 1;
        TimeUnit aliveTime = TimeUnit.MINUTES;
        ArrayBlockingQueue queue = new ArrayBlockingQueue<Runnable>(100);

        ThreadPoolExecutor executor = new ThreadPoolExecutor(minThread, maxThread, alive, aliveTime, queue);

        Runnable runnable = () -> {
            try {
                Thread.sleep(5000);
                System.out.println("Runnable from thread : " + Thread.currentThread().getName());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };

        executor.execute(runnable);

        Thread.sleep(6000);
    }

    @Test
    void shutdown() throws InterruptedException {

        int minThread = 10;
        int maxThread = 100;
        int alive = 1;
        TimeUnit aliveTime = TimeUnit.MINUTES;
        ArrayBlockingQueue queue = new ArrayBlockingQueue<Runnable>(1000);

        ThreadPoolExecutor executor = new ThreadPoolExecutor(minThread, maxThread, alive, aliveTime, queue);

        for (int index = 0; index < 1000; index++) {
            final int task = index;
            Runnable runnable = () -> {
                try {
                    Thread.sleep(1000);
                    System.out.println("Task " + task + " from thread : " + Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            };

            executor.execute(runnable);
        }

//        executor.shutdown();
//        executor.shutdownNow();
        executor.awaitTermination(1, TimeUnit.DAYS);
    }

    @Test
    void rejected() throws InterruptedException {

        int minThread = 10;
        int maxThread = 100;
        int alive = 1;
        TimeUnit aliveTime = TimeUnit.MINUTES;
        ArrayBlockingQueue queue = new ArrayBlockingQueue<Runnable>(10);
        LogRejectedExecutionHandler rejectedHandler = new LogRejectedExecutionHandler();

        ThreadPoolExecutor executor = new ThreadPoolExecutor(minThread, maxThread, alive, aliveTime, queue, rejectedHandler);

        for (int index = 0; index < 1000; index++) {
            final int task = index;
            Runnable runnable = () -> {
                try {
                    Thread.sleep(1000);
                    System.out.println("Task " + task + " from thread : " + Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            };

            executor.execute(runnable);
        }

//        executor.shutdown();
//        executor.shutdownNow();
        executor.awaitTermination(1, TimeUnit.DAYS);
    }

    public static class LogRejectedExecutionHandler implements RejectedExecutionHandler {

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            System.out.println("Task " + r + " is rejected");
        }
    }
}

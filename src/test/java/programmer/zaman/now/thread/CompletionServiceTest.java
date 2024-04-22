package programmer.zaman.now.thread;

import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.concurrent.*;

public class CompletionServiceTest {

    private Random random = new Random();

    @Test
    void test() throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(10);
        ExecutorCompletionService<String> completionService = new ExecutorCompletionService<String>(executor);

        // submit task
        Executors.newSingleThreadExecutor().execute(() -> {
            for (int index = 0; index < 100; index++) {
                final int index1 = index;
                completionService.submit(() -> {
                    Thread.sleep(random.nextInt(2000));
                    return "Task=" + index1;
                });
            }
        });

        // pool task
        Executors.newSingleThreadExecutor().execute(() -> {
            while (true) {

                try {
                    Future<String> future = null;
                    future = completionService.poll(5, TimeUnit.SECONDS);
                    if (future == null) {
                        break;
                    } else {
                        System.out.println(future.get());
                    }
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                    break;
                }

            }
        });

//        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.DAYS);
    }
}

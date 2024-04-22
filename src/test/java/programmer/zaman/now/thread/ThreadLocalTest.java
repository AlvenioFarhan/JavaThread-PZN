package programmer.zaman.now.thread;

import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadLocalTest {

    @Test
    void test() throws InterruptedException {

        final Random random = new Random();
        final UserService userService = new UserService();
        final ExecutorService executor = Executors.newFixedThreadPool(10);

        for (int index = 0; index < 50; index++) {
            final int number = index;
            executor.execute(() -> {
                try {
                    userService.setUser("User-" + number);
                    Thread.sleep(1000 + random.nextInt(3000));
                    userService.doAction();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        executor.awaitTermination(1, TimeUnit.DAYS);
    }
}

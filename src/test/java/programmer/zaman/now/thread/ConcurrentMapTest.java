package programmer.zaman.now.thread;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;

public class ConcurrentMapTest {

    @Test
    void concurrentMap() throws InterruptedException {

        final CountDownLatch countDown = new CountDownLatch(100);
        final ConcurrentHashMap<Integer, String> map = new ConcurrentHashMap<>();
        final ExecutorService executor = Executors.newFixedThreadPool(100);

        for (int index = 0; index < 100; index++) {
            final int number = index;
            executor.execute(() -> {
                try {
                    Thread.sleep(1000);
                    map.putIfAbsent(number, "Data-" + number);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    countDown.countDown();
                }
            });
        }

        executor.execute(() -> {
            try {
                countDown.await();
                map.forEach((integer, s) -> System.out.println(integer + " : " + s));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        executor.awaitTermination(1, TimeUnit.DAYS);
    }

    @Test
    void testCollection() {

        List<String> list = List.of("Alvenio", "Farhan", "Prayogo");
        List<String> synchronizedList = Collections.synchronizedList(list);
    }
}

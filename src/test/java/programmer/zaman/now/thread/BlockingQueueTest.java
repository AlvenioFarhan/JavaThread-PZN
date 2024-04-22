package programmer.zaman.now.thread;

import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.concurrent.*;

public class BlockingQueueTest {

    @Test
    void arrayBlockingQueue() throws InterruptedException {
        final ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(5);
        final ExecutorService executor = Executors.newFixedThreadPool(20);

        for (int index = 0; index < 10; index++) {
            executor.execute(() -> {
                try {
                    queue.put("Data");
                    System.out.println("Finish Put Data");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        executor.execute(() -> {
            while (true) {
                try {
                    Thread.sleep(2000);
                    String value = queue.take();
                    System.out.println("Receive data : " + value);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        executor.awaitTermination(1, TimeUnit.DAYS);
    }

    @Test
    void linkedBlockingQueue() throws InterruptedException {
        final LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<String>();
        final ExecutorService executor = Executors.newFixedThreadPool(20);

        for (int index = 0; index < 10; index++) {
            executor.execute(() -> {
                try {
                    queue.put("Data");
                    System.out.println("Finish Put Data");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        executor.execute(() -> {
            while (true) {
                try {
                    Thread.sleep(2000);
                    String value = queue.take();
                    System.out.println("Receive data : " + value);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        executor.awaitTermination(1, TimeUnit.DAYS);
    }

    @Test
    void priorityBlockingQueue() throws InterruptedException {
        final PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<Integer>(10, Comparator.reverseOrder());
        final ExecutorService executor = Executors.newFixedThreadPool(20);

        for (int index = 0; index < 10; index++) {
            final int number = index;
            executor.execute(() -> {
                queue.put(number);
                System.out.println("Finish Put Data");
            });
        }

        executor.execute(() -> {
            while (true) {
                try {
                    Thread.sleep(2000);
                    Integer value = queue.take();
                    System.out.println("Receive data : " + value);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        executor.awaitTermination(1, TimeUnit.DAYS);
    }

    @Test
    void delayedQueue() throws InterruptedException {
        final DelayQueue<ScheduledFuture<String>> queue = new DelayQueue<>();
        final ExecutorService executor = Executors.newFixedThreadPool(20);
        final ScheduledExecutorService executorScheduled = Executors.newScheduledThreadPool(10);

        for (int index = 1; index <= 10; index++) {
            final int number = index;
            queue.put(executorScheduled.schedule(() -> "Data" + number, index, TimeUnit.SECONDS));
        }

        executor.execute(() -> {
            while (true) {
                try {
                    ScheduledFuture<String> value = queue.take();
                    System.out.println("Receive data : " + value.get());
                } catch (InterruptedException | ExecutionException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        executor.awaitTermination(1, TimeUnit.DAYS);
    }

    @Test
    void synchronousQueue() throws InterruptedException {
        final SynchronousQueue<String> queue = new SynchronousQueue<String>();
        final ExecutorService executor = Executors.newFixedThreadPool(20);

        for (int index = 0; index < 10; index++) {
            final int number = index;
            executor.execute(() -> {
                try {
                    queue.put("Data - " + number);
                    System.out.println("Finish Put Data : " + number);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        executor.execute(() -> {
            while (true) {
                try {
                    Thread.sleep(2000);
                    String value = queue.take();
                    System.out.println("Receive data : " + value);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        executor.awaitTermination(1, TimeUnit.DAYS);
    }

    @Test
    void blockingDeque() throws InterruptedException {
        final LinkedBlockingDeque<String> queue = new LinkedBlockingDeque<>();
        final ExecutorService executor = Executors.newFixedThreadPool(20);

        for (int index = 0; index < 10; index++) {
            final int number = index;
            try {
                queue.putLast("Data - " + number);
                System.out.println("Finish Put Data : " + number);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        executor.execute(() -> {
            while (true) {
                try {
                    Thread.sleep(2000);
                    String value = queue.takeFirst();
                    System.out.println("Receive data : " + value);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        executor.awaitTermination(1, TimeUnit.DAYS);
    }

    @Test
    void transferQueue() throws InterruptedException {
        final LinkedTransferQueue<String> queue = new LinkedTransferQueue<>();
        final ExecutorService executor = Executors.newFixedThreadPool(20);

        for (int index = 0; index < 10; index++) {
            final int number = index;
            executor.execute(() -> {
                try {
                    queue.transfer("Data - " + number);
                    System.out.println("Finish Put Data : " + number);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        executor.execute(() -> {
            while (true) {
                try {
                    Thread.sleep(2000);
                    String value = queue.take();
                    System.out.println("Receive data : " + value);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        executor.awaitTermination(1, TimeUnit.DAYS);
    }
}

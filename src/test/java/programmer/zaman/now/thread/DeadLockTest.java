package programmer.zaman.now.thread;

import org.junit.jupiter.api.Test;

public class DeadLockTest {

    @Test
    void transfer() throws InterruptedException {

        Balance balance1 = new Balance(1_000_000L);
        Balance balance2 = new Balance(1_000_000L);

        Thread thread1 = new Thread(() -> {
            try {
                Balance.transfer(balance1, balance2, 300_000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        Thread thread2 = new Thread(() -> {
            try {
                Balance.transfer(balance2, balance1, 800_000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println("Balance 1 : " + balance1.getValue());
        System.out.println("Balance 2 : " + balance2.getValue());
    }
}

package programmer.zaman.now.thread;

import org.junit.jupiter.api.Test;

import java.util.Timer;
import java.util.TimerTask;

public class TimerTest {

    @Test
    void delayedJob() throws InterruptedException {

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                System.out.println("Delayed Job");
            }
        };

        Timer timer = new Timer();
        timer.schedule(task, 2000);

        Thread.sleep(3000L);
    }

    @Test
    void periodicJob() throws InterruptedException {

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                System.out.println("Delayed Job");
            }
        };

        Timer timer = new Timer();
        timer.schedule(task, 2000, 2000);

        Thread.sleep(10_000L);
    }
}

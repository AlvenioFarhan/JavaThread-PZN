package programmer.zaman.now.thread;

public class DaemonApp {
    public static void main(String[] args) {

        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(3_000);
                System.out.println("Run Thread");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        thread.setDaemon(true);
        thread.start();
    }
}

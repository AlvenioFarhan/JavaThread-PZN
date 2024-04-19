package programmer.zaman.now.thread;

public class SynchronizedCounter {

    private Long value = 0L;

    //menghindari Race Condition
    public synchronized void increment() {
        value++;
    }
    public void incrementLock() {
        synchronized (this){
            value++;
        }
    }

    public Long getValue() {
        return value;
    }
}

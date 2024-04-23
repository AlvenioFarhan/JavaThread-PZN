package programmer.zaman.now.thread;

import org.junit.jupiter.api.Test;

import java.util.concurrent.*;

public class ReactiveStreamTest {

    @Test
    void publish() throws InterruptedException {
        SubmissionPublisher<String> publisher = new SubmissionPublisher<String>();
        PrintSubscriber subscriber1 = new PrintSubscriber("A", 1000L);
        PrintSubscriber subscriber2 = new PrintSubscriber("B", 500L);
        publisher.subscribe(subscriber1);
        publisher.subscribe(subscriber2);

        ExecutorService executor = Executors.newFixedThreadPool(10);
        executor.execute(() -> {
            for (int index = 0; index < 100; index++) {
                try {
                    Thread.sleep(2000);
                    publisher.submit("Alvenio-" + index);
                    System.out.println(Thread.currentThread().getName() + " : Send Alvenio-" + index);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.DAYS);

        Thread.sleep(100 * 1000);
    }

    @Test
    void buffer() throws InterruptedException {
//        SubmissionPublisher<String> publisher = new SubmissionPublisher<String>(Executors.newFixedThreadPool(10), 50);
        SubmissionPublisher<String> publisher = new SubmissionPublisher<String>(Executors.newWorkStealingPool(), 50);
        PrintSubscriber subscriber1 = new PrintSubscriber("A", 1000L);
        PrintSubscriber subscriber2 = new PrintSubscriber("B", 500L);
        publisher.subscribe(subscriber1);
        publisher.subscribe(subscriber2);

        ExecutorService executor = Executors.newFixedThreadPool(10);
        executor.execute(() -> {
            for (int index = 0; index < 100; index++) {
                try {
                    Thread.sleep(2000);
                    publisher.submit("Alvenio-" + index);
                    System.out.println(Thread.currentThread().getName() + " : Send Alvenio-" + index);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.DAYS);

        Thread.sleep(100 * 1000);
    }

    @Test
    void processor() throws InterruptedException {

        SubmissionPublisher publisher = new SubmissionPublisher<String>();

        HelloProcessor processor = new HelloProcessor();
        publisher.subscribe(processor);

        PrintSubscriber subscriber = new PrintSubscriber("A", 1000L);
        processor.subscribe(subscriber);

        ExecutorService executor = Executors.newFixedThreadPool(10);
        executor.execute(() -> {
            for (int index = 0; index < 100; index++) {
                publisher.submit("Alvenio-" + index);
                System.out.println(Thread.currentThread().getName() + " : Send Alvenio-" + index);
            }
        });

        Thread.sleep(100 * 1000);
    }

    public static class PrintSubscriber implements Flow.Subscriber<String> {


        private Flow.Subscription subscription;

        private String name;

        private Long sleep;

        public PrintSubscriber(String name, Long sleep) {
            this.name = name;
            this.sleep = sleep;
        }

        @Override
        public void onSubscribe(Flow.Subscription subscription) {
            this.subscription = subscription;
            this.subscription.request(1);
        }

        @Override
        public void onNext(String item) {
            try {
                Thread.sleep(sleep);
                System.out.println(Thread.currentThread().getName() + " : " + name + " : " + item);
                this.subscription.request(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public void onError(Throwable throwable) {
            throwable.printStackTrace();
        }

        @Override
        public void onComplete() {
            System.out.println(Thread.currentThread().getName() + " : DONE");
        }
    }


    public static class HelloProcessor extends SubmissionPublisher<String> implements Flow.Processor<String, String> {

        private Flow.Subscription subscription;

        @Override
        public void onSubscribe(Flow.Subscription subscription) {
            this.subscription = subscription;
            this.subscription.request(1);
        }

        @Override
        public void onNext(String item) {

            String value = "Hello " + item;
            submit(value);
            this.subscription.request(1);
        }

        @Override
        public void onError(Throwable throwable) {
            throwable.printStackTrace();
        }

        @Override
        public void onComplete() {
            close();
        }
    }

}

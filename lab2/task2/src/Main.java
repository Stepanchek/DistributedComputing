import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Main extends Thread {
    public static void main(String[] args) {
        final Long interval = 500L;

        BlockingQueue<Notification> firstQueue = new LinkedBlockingQueue<>();
        BlockingQueue<Notification> secondQueue = new LinkedBlockingQueue<>();
        List<Notification> notifications = new LinkedList<>();

        for (int i =0; i<20; i++){
            notifications.add(new Notification(i));
        }

        SleepHandler<Notification> sleepHandler1 = new SleepHandler<>(interval);
        SleepHandler<Notification> sleepHandler2 = new SleepHandler<>(2 * interval);
        SleepHandler<Notification> sleepHandler3 = new SleepHandler<>(3 * interval);

        TransitiveProducer<Notification> transitiveHandler = new TransitiveProducer<>(secondQueue,sleepHandler2);

        Producer<Notification> producer = new Producer<>(firstQueue,notifications,sleepHandler1);
        Consumer<Notification> transitiveConsumer = new Consumer<>(firstQueue,transitiveHandler);
        Consumer<Notification> consumer = new Consumer<>(secondQueue,sleepHandler3);

        transitiveConsumer.setConsumerId("transitive");
        consumer.setConsumerId("final");

        producer.start();
        transitiveConsumer.start();
        consumer.start();
    }
}
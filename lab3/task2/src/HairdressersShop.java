import java.util.Currency;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class HairdressersShop {
    private BlockingQueue<Consumer> consumers;

    public HairdressersShop() {
        this.consumers = new LinkedBlockingQueue<>();
    }

    public void start(){
        Thread[] threads = new Thread[Constants.THREADS];
        for (int i = 0;i < threads.length;i++){
            threads[i] = new Thread(new Consumer(this,i,Constants.INTERVAL +(i+1) / threads.length));
        }

        Thread haircutter = new Thread(new Haircutter(this));

        for (Thread thread : threads){
            thread.start();
        }
        haircutter.start();
    }

    public void addConsumer(Consumer consumer) throws InterruptedException {
        consumers.put(consumer);
    }

    public Consumer takeConsumer() throws InterruptedException {
        return consumers.take();
    }

    public int consumersCount(){
        return consumers.size();
    }
}

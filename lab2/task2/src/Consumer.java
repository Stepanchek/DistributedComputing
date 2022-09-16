import Interfaces.IConsumer;
import Interfaces.IHandler;
import Interfaces.INotification;

import java.util.concurrent.BlockingQueue;

public class Consumer<T extends INotification> extends Thread implements IConsumer {

    private BlockingQueue<T> consumerQueue;
    private IHandler<T> handler;
    private String consumerId = "consumer";
    public Consumer(BlockingQueue<T> consumerQueue,IHandler<T> handler){
        this.consumerQueue = consumerQueue;
        this.handler = handler;
    }

    public void setConsumerId(String name) {
        this.consumerId = name;
    }

    @Override
    public boolean consume() throws InterruptedException {
        T notification = consumerQueue.take();
        if(notification.isLast()){
            handler.handle((T)new INotification(){
                @Override
                public boolean isLast() {
                    return true;
                }
            });
            return false;
        }
        System.out.println("Consumed by: " + consumerId + "\t" + notification.toString());
        handler.handle(notification);
        return true;
    }

    @Override
    public void run() {
        boolean notEmpty;
        do {
            try {
                notEmpty = consume();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        } while (notEmpty);
    }
}

import Interfaces.IHandler;
import Interfaces.INotification;
import Interfaces.IProducer;

import java.util.concurrent.BlockingQueue;

public class TransitiveProducer<T extends INotification> implements IHandler<T>, IProducer {
    private BlockingQueue<T> transitiveQueue;
    private IHandler<T> handler;
    private T currentNotification;
    public TransitiveProducer(BlockingQueue<T> transitiveQueue, IHandler<T> handler) {
        this.transitiveQueue = transitiveQueue;
        this.handler = handler;
    }

    @Override
    public void handle(T notification) {
        try {
            handler.handle(notification);
            currentNotification = notification;
            produce();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean produce() throws InterruptedException {
        transitiveQueue.put(currentNotification);
        return true;
    }
}

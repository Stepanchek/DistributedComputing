import Interfaces.IHandler;
import Interfaces.INotification;
import Interfaces.IProducer;

import java.util.List;
import java.util.concurrent.BlockingQueue;

public class Producer<T extends INotification> extends Thread implements IProducer {

    private BlockingQueue<T> producerQueue;
    private List<T> dataList;
    private IHandler<T> handler;
    private int index = 0;

    public Producer(BlockingQueue<T> queue, List<T> dataList, IHandler<T> handler) {
        this.producerQueue = queue;
        this.dataList = dataList;
        this.handler = handler;
    }

    @Override
    public boolean produce() throws InterruptedException {
        if (index < dataList.size()) {
            T notification = dataList.get(index);
            handler.handle(notification);
            System.out.println("Produced :" + notification.toString());
            producerQueue.put(notification);
            index++;
            return true;
        }
        producerQueue.put((T)new INotification(){
            @Override
            public boolean isLast() {
                return true;
            }
        });
        return false;
    }

    @Override
    public void run() {
        boolean notEmpty;
        do{
            try {
                notEmpty = produce();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        } while (notEmpty);
    }
}

import Interfaces.IHandler;
import Interfaces.INotification;

public class SleepHandler<T extends INotification> implements IHandler<T> {
    private Long milliseconds;

    public SleepHandler(Long milliseconds) {
        this.milliseconds = milliseconds;
    }

    @Override
    public void handle(T notification) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

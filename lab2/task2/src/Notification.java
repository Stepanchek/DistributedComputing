import Interfaces.INotification;

public class Notification implements INotification {
    private int id;

    public Notification(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Id: " + id;
    }

    @Override
    public boolean isLast() {
        return false;
    }
}

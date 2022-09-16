package Interfaces;
@FunctionalInterface
public interface IHandler<T extends INotification> {
    void handle(T notification);
}

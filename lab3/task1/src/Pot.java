public class Pot {
    private final int maxCapacity;
    private int currentCapacity;
    final Object full = new Object();
    final Object empty = new Object();

    public Pot(int maxCapacity){
        this.maxCapacity = maxCapacity;
        currentCapacity = 0;
    }

    private boolean isFull(){
        return currentCapacity == maxCapacity;
    }

    private synchronized boolean check(){
        if(!isFull()){
            ++currentCapacity;
            if (isFull()){
                fillingReport();
            }
        }else {
            return true;
        }
        return false;
    }

    public void waitForFull(){
        synchronized (full){
            try {
                full.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void fillingReport(){
        synchronized (full){
            full.notifyAll();
        }
    }

    public void waitForEmpty(){
        synchronized (empty){
            try {
                empty.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void desolationReport(){
        synchronized (empty){
            empty.notifyAll();
        }
    }

    public void fill(){
        if(check()){
            System.out.println("Waiting...");
            waitForEmpty();
            fill();
        }
    }

    public void eatHoney(){
        try {
            Thread.sleep((long)(Constants.INTERVAL*10));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        synchronized (this){
            currentCapacity = 0;
        }
    }
}

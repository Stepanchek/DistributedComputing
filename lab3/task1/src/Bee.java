import static java.lang.Thread.sleep;

public class Bee implements Runnable{
    private final Pot pot;
    private final Bear bear;

    public Bee(Pot pot, Bear bear){
        this.bear = bear;
        this.pot = pot;
    }

    @Override
    public void run(){
        while (true){
            try {
                sleep(Constants.INTERVAL);
                pot.fill();
                System.out.printf("Bee: %d brought honey into pot%n", Thread.currentThread().getId());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

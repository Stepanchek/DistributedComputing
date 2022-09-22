public class Bear implements Runnable{
    private final Pot pot;

    public Bear(Pot pot){
        this.pot = pot;
    }

    @Override
    public void run(){
        while(true){
            pot.waitForFull();
            System.out.println("Time when bear woke up");
            pot.eatHoney();
            System.out.println("Honey has been eaten");
        }
    }
}

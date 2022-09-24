import static java.lang.Thread.sleep;

public class Haircutter implements Runnable{
    HairdressersShop hairdressersShop;

    public Haircutter(HairdressersShop hairdressersShop) {
        this.hairdressersShop = hairdressersShop;
    }


    @Override
    public void run() {
        while (true){
            try {
                if (hairdressersShop.consumersCount() == 0){
                    System.out.println("No consumers...");
                }

                Consumer consumer = hairdressersShop.takeConsumer();
                System.out.printf("Haircutter started working %d%n",consumer.getId());

                sleep(Constants.INTERVAL);

                System.out.printf("Haircutter finished his work", consumer.getId());

                synchronized (consumer){
                    consumer.notifyAll();
                }
            }catch (InterruptedException e){
                throw new RuntimeException(e);
            }
        }
    }
}

import static java.lang.Thread.sleep;

public class Consumer implements Runnable{
    private HairdressersShop hairdressersShop;
    private int id;
    private long periodicity;

    public Consumer(HairdressersShop hairdressersShop, int id, long periodicity) {
        this.hairdressersShop = hairdressersShop;
        this.id = id;
        this.periodicity = periodicity;
    }

    public int getId() {
        return id;
    }

    @Override
    public void run() {
        while (true){
            try {
                sleep(periodicity);

                int current = hairdressersShop.consumersCount();

                if(current > 0){
                    System.out.printf("Consumer %d is sleeping %d in queue%n",id,current + 1);
                }else{
                    System.out.printf("Consumer %d is waiting on his turn%n",id);
                }

                hairdressersShop.addConsumer(this);

                synchronized (this){
                    wait();
                }
                System.out.printf("Consumer %d will appear through %d %n",id,periodicity);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

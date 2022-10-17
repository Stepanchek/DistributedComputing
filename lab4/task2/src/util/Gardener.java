package util;

public class Gardener implements Runnable {
    private final Garden garden;

    public Gardener(Garden garden) {
        this.garden = garden;
    }

    @Override
    public void run() {
        while(true) {
            garden.waterPlants();
            System.out.println("util.Gardener watered plants");
        }
    }
}
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Thread[] threads = new Thread[Util.PARTS];
        int[] recruits = Util.generateRecruits();
        CustomCyclicBarrier barrier = new CustomCyclicBarrier(Util.PARTS);

        RecruitPart.fillFinishedArray(Util.PARTS);

        for(int i = 0; i < threads.length; i++){
            threads[i] = new Thread(new RecruitPart(recruits, barrier, i, i * 50, (i + 1) * 50));
        }

        for (Thread thread : threads) {
            thread.start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.printf("Result: %s%n", Arrays.toString(recruits));
    }
}
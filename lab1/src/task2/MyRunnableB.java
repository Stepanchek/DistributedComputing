package task2;

import javax.swing.*;
import java.util.concurrent.atomic.AtomicInteger;

//B
public class MyRunnableB extends Thread {
    private final JSlider slider;
    private final int step;
    private final AtomicInteger semaphore;
    public MyRunnableB(JSlider slider, int step, AtomicInteger semaphore) {
        this.slider = slider;
        this.step = step;
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        if (!semaphore.compareAndSet(0, 1)){
            return;
        }

        boolean flag = (slider.getValue() == 10 && step > 0) || (slider.getValue() == 90 && step<0);

        while(!isInterrupted()) {
            try {
                sleep(100);
                int sliderValue = slider.getValue();
                if ((sliderValue > 10 && sliderValue < 90) || flag) {
                    slider.setValue(sliderValue + step);
                }
            } catch (InterruptedException e) {
                interrupt();
                System.out.println("Interruption id: " + getId());
            }
        }
        semaphore.set(0);
    }
}
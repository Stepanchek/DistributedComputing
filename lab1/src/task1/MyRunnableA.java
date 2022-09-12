package task1;

import javax.swing.*;

public class MyRunnableA extends Thread{
    private int step;
    private JSlider jSlider;

    public MyRunnableA(int step, JSlider jSlider)
    {
        this.step = step;
        this.jSlider = jSlider;
    }

    @Override
    public void run() {
        while(!isInterrupted()){
            synchronized (jSlider)
            {
                try {
                    sleep(100);
                    int sliderValue = jSlider.getValue();
                    if (sliderValue > 10 && sliderValue < 90) {
                        jSlider.setValue(sliderValue + step);
                    } else
                    {
                        interrupt();
                    }
                } catch (InterruptedException e) {
                    System.out.println("Interruption id: " + getId());
                }
            }
        }
        System.out.println("Finished with id: " + getId());
    }
}

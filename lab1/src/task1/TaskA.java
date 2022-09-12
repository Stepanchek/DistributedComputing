package task1;

import javax.swing.*;

public class TaskA {
    private static final JSlider slider = new JSlider();
    private static MyRunnableA firstThread = new MyRunnableA(-1, slider);
    private static MyRunnableA secondThread = new MyRunnableA( 1, slider);

    public static void main(String[] args) {
        setGUI();
    }

    private static void setGUI() {
        JFrame frame = new JFrame("ApplicationA");

        slider.setBounds(40, 40 ,420, 40);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setMajorTickSpacing(10);

        JButton buttonStart = new JButton("Start");
        buttonStart.setBounds(200, 250,100, 50);
        buttonStart.addActionListener(e -> {
            firstThread.start();
            secondThread.start();
            buttonStart.setEnabled(false);
        });

        SpinnerModel firstSpinnerModel = new SpinnerNumberModel(5, 0, 10, 1);
        JSpinner firstSpinner = new JSpinner(firstSpinnerModel);
        firstSpinner.setBounds(40, 130, 150, 80);
        firstSpinner.addChangeListener(e -> {
            int priority = (int) firstSpinner.getValue();
            firstThread.setPriority(priority);
        });

        SpinnerModel secondSpinnerModel = new SpinnerNumberModel(5, 0, 10, 1);
        JSpinner secondSpinner = new JSpinner(secondSpinnerModel);
        secondSpinner.setBounds(300, 130, 150, 80);
        secondSpinner.addChangeListener(e -> {
            int priority = (int) secondSpinner.getValue();
            secondThread.setPriority(priority);
        });

        frame.add(slider);
        frame.add(buttonStart);
        frame.add(firstSpinner);
        frame.add(secondSpinner);

        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }
}
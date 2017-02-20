package lab1_search;

import javax.swing.*;

public class ThreadForm extends JFrame{
    private JPanel rootPanel;
    private JButton settingsButton2;
    private JButton settingsButton1;
    private JButton pauseThread1;
    private JButton playThread1;
    private JButton stopThread1;
    private JButton playThread2;
    private JButton pauseThread2;
    private JButton stopThread2;
    private JTextPane textPane1;
    private JTextPane textPane2;

    SearchInfo searchInfo1;
    SearchInfo searchInfo2;

    SearchThread thread1;
    SearchThread thread2;

    boolean isCheckThread1 = false;
    boolean isCheckThread2 = false;
    boolean suspendThread1 = false;
    boolean suspendThread2 = false;

    ThreadForm() {
        setContentPane(rootPanel);
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setSize(845, 350);
        setVisible(true);

        settingsButton1.addActionListener(e -> {
            isCheckThread1 = true;
            callDialog();
        });
        settingsButton2.addActionListener(e -> {
            isCheckThread2 = true;
            callDialog();
        });


        playThread1.addActionListener(e -> {
            thread1 = new SearchThread("First thread", searchInfo1);
            thread1.start();
        });
        playThread2.addActionListener(e -> {
            thread2 = new SearchThread("Second thread", searchInfo2);
            thread2.start();
        });


        stopThread1.addActionListener(e -> {
            thread1.interrupt();
            textPane1.setText(textPane1.getText() + "Первый поток завершен.");
        });
        stopThread2.addActionListener(e -> {
            thread2.interrupt();
            textPane2.setText(textPane2.getText() + "Второй поток завершен");
        });


    }

    private void callDialog() {
        ThreadSettings threadSettingsDialog = new ThreadSettings(this);
        threadSettingsDialog.setVisible(true);
    }

    public void inputData(String s){
        if (isCheckThread1)
            textPane1.setText(textPane1.getText() + s);
        if (isCheckThread2)
            textPane2.setText(textPane2.getText() + s);
    }
}

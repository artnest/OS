package lab1_search;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ThreadForm extends JFrame {
    private JPanel mainPanel;
    private JButton settingsButton1;
    private JButton settingsButton2;
    private JButton startButton1;
    private JButton startButton2;
    private JButton pauseButton1;
    private JButton pauseButton2;
    private JButton stopButton1;
    private JButton stopButton2;
    private JTextPane textPane1;
    private JTextPane textPane2;

//    SearchAttributes searchAttributes1;
//    SearchAttributes searchAttributes2;

    private SearchThread thread1 = new SearchThread();
    private SearchThread thread2 = new SearchThread();

//    boolean isCheckThread1 = false;
//    boolean isCheckThread2 = false;
//    boolean suspendThread1 = false;
//    boolean suspendThread2 = false;

    public ThreadForm() {
        setTitle("Search Application");
        setContentPane(mainPanel);
        setSize(845, 350);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        settingsButton1.addActionListener(e -> {
            new ThreadSettings(thread1);
//            isCheckThread1 = true;
//            callDialog();
        });
        settingsButton2.addActionListener(e -> {
            new ThreadSettings(thread2);
//            isCheckThread2 = true;
//            callDialog();
        });


        startButton1.addActionListener(e -> {
//            thread1 = new SearchThread("First thread", searchAttributes1); // TODO add check
            thread1.start();
        });
        startButton2.addActionListener(e -> {
//            thread2 = new SearchThread("Second thread", searchAttributes2); // TODO add check
            thread2.start();
        });

        pauseButton1.addActionListener(e -> { // TODO rewrite!!
            if (thread1.isAlive()) {
                try {
                    thread1.wait();
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        });
        pauseButton2.addActionListener(e -> { // TODO rewrite!!
            if (thread2.isAlive()) {
                try {
                    thread2.wait();
                } catch (InterruptedException e2) {
                    e2.printStackTrace();
                }
            }
        });

        stopButton1.addActionListener(e -> {
            if (thread1.isAlive()) {
                thread1.interrupt();
                textPane1.setText(textPane1.getText() + "Первый поток завершен."); // TODO rewrite
            }
        });
        stopButton2.addActionListener(e -> {
            if (thread2.isAlive()) {
                thread2.interrupt();
                textPane2.setText(textPane2.getText() + "Второй поток завершен"); // TODO rewrite
            }
        });

        pack();
        // TODO UIManager setLookAndFeel
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /*private void callDialog() {
        new ThreadSettings(this); // TODO rearrange
    }*/

    /*public void inputData(String s) { // TODO ?
        if (isCheckThread1)
            textPane1.setText(textPane1.getText() + s);
        if (isCheckThread2)
            textPane2.setText(textPane2.getText() + s);
    }*/
}

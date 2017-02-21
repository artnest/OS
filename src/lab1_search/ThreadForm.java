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

    SearchParameters searchParameters1;
    SearchParameters searchParameters2;

    SearchThread thread1;
    SearchThread thread2;

    boolean isCheckThread1 = false;
    boolean isCheckThread2 = false;
    boolean suspendThread1 = false;
    boolean suspendThread2 = false;

    ThreadForm() {
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
            isCheckThread1 = true;
            callDialog();
        });
        settingsButton2.addActionListener(e -> {
            isCheckThread2 = true;
            callDialog();
        });


        startButton1.addActionListener(e -> {
            thread1 = new SearchThread("First thread", searchParameters1);
            thread1.start();
        });
        startButton2.addActionListener(e -> {
            thread2 = new SearchThread("Second thread", searchParameters2);
            thread2.start();
        });


        stopButton1.addActionListener(e -> {
            if (thread1.isAlive()) {
                thread1.interrupt();
                textPane1.setText(textPane1.getText() + "Первый поток завершен.");
            }
        });
        stopButton2.addActionListener(e -> {
            if (thread2.isAlive()) {
                thread2.interrupt();
                textPane2.setText(textPane2.getText() + "Второй поток завершен");
            }
        });

        pack();
        // TODO UIManager setLookAndFeel
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void callDialog() {
        ThreadSettings threadSettingsDialog = new ThreadSettings(this);
        threadSettingsDialog.setVisible(true);
    }

    public void inputData(String s) {
        if (isCheckThread1)
            textPane1.setText(textPane1.getText() + s);
        if (isCheckThread2)
            textPane2.setText(textPane2.getText() + s);
    }
}

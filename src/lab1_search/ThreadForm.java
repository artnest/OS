package lab1_search;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

class ThreadForm extends JFrame {
    private JPanel mainPanel;
    private JButton settingsButton1;
    private JButton settingsButton2;
    private JButton startButton1;
    private JButton startButton2;
    private JButton pauseButton1;
    private JButton pauseButton2;
    private JButton stopButton1;
    private JButton stopButton2;
    private JTextArea textArea1;
    private JTextArea textArea2;

    private SearchThread thread1;
    private SearchThread thread2;

    ThreadForm() {
        setTitle("Search Application");
        setContentPane(mainPanel);

        DefaultCaret caret; // autoscroll
        caret = (DefaultCaret) textArea1.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        caret = (DefaultCaret) textArea2.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        settingsButton1.addActionListener(e -> {
            thread1 = new SearchThread(textArea1,
                    startButton1, pauseButton1, stopButton1);
            new ThreadSettings(thread1);
        });
        settingsButton2.addActionListener(e -> {
            thread2 = new SearchThread(textArea2,
                    startButton2, pauseButton2, stopButton2);
            new ThreadSettings(thread2);
        });


        startButton1.addActionListener(e -> {
            if (!thread1.isPaused()) {
                thread1.start();
            } else {
                thread1.unpause();
            }
        });
        startButton2.addActionListener(e -> {
            if (!thread2.isPaused()) {
                thread2.start();
            } else {
                thread2.unpause();
            }
        });

        pauseButton1.addActionListener(e -> {
            if (thread1.isAlive()) {
                thread1.pause();
            }
        });
        pauseButton2.addActionListener(e -> {
            if (thread2.isAlive()) {
                thread2.pause();
            }
        });

        stopButton1.addActionListener(e -> {
            thread1.interruptThread();

            startButton1.setEnabled(false);
            pauseButton1.setEnabled(false);
            stopButton1.setEnabled(false);
        });
        stopButton2.addActionListener(e -> {
            thread2.interruptThread();

            startButton2.setEnabled(false);
            pauseButton2.setEnabled(false);
            stopButton2.setEnabled(false);
        });

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

}

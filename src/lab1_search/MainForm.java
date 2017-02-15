package lab1_search;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainForm {
    private JFrame frame;
    private JButton thread1Button;
    private JPanel panel;
    private JButton thread2Button;
    private JCheckBox thread1CheckBox;
    private JCheckBox thread2CheckBox;
    private JButton startButton;

    private MainForm() {
        prepareGUI();
        thread1Button.addActionListener(e -> {
            PatternChoosing patternChoosing = new PatternChoosing();
        });
    }

    private void prepareGUI() {
        frame = new JFrame("Search Application");
        frame.setSize(250, 150);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        frame.add(panel);
        frame.setResizable(false);
    }

    private void showForm() {
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        MainForm form = new MainForm();
        form.showForm();
    }
}

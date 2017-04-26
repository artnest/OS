package lab2_threads;

import javax.swing.*;
import java.io.File;
import java.util.Arrays;

/**
 * Created by theme on 26/04/17.
 */
public class MainForm extends JFrame {
    private JTextArea textArea;
    private JPanel panel;
    private JButton startButton;
    private JButton stopButton;

    public MainForm() {
        setSize(640, 480);
        setTitle("Fibonacci Threads");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        add(panel);
        setVisible(true);

        startButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            if (fileChooser.showOpenDialog(MainForm.this) == JFileChooser.APPROVE_OPTION) {
                InputThread.fileQueue
                        .addAll(Arrays.asList(
                                new File(fileChooser
                                        .getSelectedFile()
                                        .getAbsolutePath())
                                        .listFiles((dir, name) -> name.endsWith(".in"))));
            }
        });
    }
}

package lab2_threads;

import javax.swing.*;
import java.io.File;
import java.util.Arrays;
import java.util.Collections;

/**
 * Created by theme on 26/04/17.
 */
public class MainForm extends JFrame {
    private JTextArea textArea;
    private JPanel panel;
    private JButton startButton;
    private JButton stopButton;

    private FibonacciThreads fibonacciThreads;

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
                File[] inputFiles = new File(fileChooser
                        .getSelectedFile()
                        .getAbsolutePath())
                        .listFiles((dir, name) -> name.endsWith(".in"));

                if (inputFiles == null) {
                    JOptionPane.showMessageDialog(this,
                            "Could not open the directory for input!",
                            "Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                if (inputFiles.length == 0) {
                    JOptionPane.showMessageDialog(this,
                            "Directory does not contain any tests (input)!",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Arrays.sort(inputFiles);
                InputThread.fileQueue.addAll(Arrays.asList(inputFiles));

                File[] outputFiles = new File(fileChooser
                        .getSelectedFile()
                        .getAbsolutePath())
                        .listFiles((dir, name) -> name.endsWith(".out"));

                if (outputFiles == null) {
                    JOptionPane.showMessageDialog(this,
                            "Could not open the directory for output!",
                            "Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                if (outputFiles.length == 0) {
                    JOptionPane.showMessageDialog(this,
                            "Directory does not contain any tests (output)!",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Arrays.sort(outputFiles);
                OutputThread.fileQueue.addAll(Arrays.asList(outputFiles));
            }

            FibonacciThreads.initWorkingThreads();

            while (ExecutionThread.isTasksFinished() || OutputThread.fileQueue.isEmpty()) { // break rule // ???
                    if (OutputThread.fileSuccessQueue.element().exists()) {
                        FibonacciThreads.initOutputThread();
                        try {
                            FibonacciThreads.getOutputThread().join();
                        } catch (InterruptedException ignored) {
                        }
                    }
                }
        });
    }
}

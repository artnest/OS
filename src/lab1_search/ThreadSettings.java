package lab1_search;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

class ThreadSettings extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField directoryTextField;
    private JTextField templateTextField;
    private JTextField substringTextField;
    private JCheckBox subdirectoryCheckBox;
    private JCheckBox patternCheckBox;
    private JCheckBox substringCheckBox;
    private JButton chooseDirectoryButton;

    private SearchThread thread;

    ThreadSettings(SearchThread thread) {
        this.thread = thread;

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(e -> onOK());
        buttonCancel.addActionListener(e -> onCancel());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        chooseDirectoryButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            if (fileChooser.showDialog(null,
                    "Choose directory...") == JFileChooser.APPROVE_OPTION) {
                directoryTextField.setText(fileChooser.getSelectedFile().getAbsolutePath());
            }
        });

        patternCheckBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                templateTextField.setEnabled(true);
            } else {
                templateTextField.setEnabled(false);
            }
        });

        substringCheckBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                substringTextField.setEnabled(true);
            } else {
                substringTextField.setEnabled(false);
            }
        });

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void onOK() {
        if (directoryTextField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "You didn't choose a directory!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (substringCheckBox.isSelected() && patternCheckBox.isSelected()) {
            if (!templateTextField.getText().endsWith(".txt") ||
                    !templateTextField.getText().endsWith(".doc") ||
                    !templateTextField.getText().endsWith(".docx") ||
                    !templateTextField.getText().endsWith(".rtf") ||
                    !templateTextField.getText().endsWith(".log") ||
                    !templateTextField.getText().endsWith(".java")) {
                JOptionPane.showMessageDialog(this,
                        "Allowed file formats: .txt, .doc, .docx, .rtf, .log, .java",
                        "File format error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
        } else if (substringCheckBox.isSelected()) {
            JOptionPane.showMessageDialog(this,
                    "Only .txt files will be viewed for containing the substring",
                    "Warning",
                    JOptionPane.WARNING_MESSAGE);
            templateTextField.setText("*.txt"); // TODO remove?
        }

        // TODO check if pattern is valid: FileSystems.getDefault().getPathMatcher("glob:" + pattern) ?

        thread.setSearchAttributes(
                new SearchAttributes(
                        directoryTextField.getText(),
                        templateTextField.getText(),
                        substringTextField.getText(),
                        subdirectoryCheckBox.isSelected(),
                        patternCheckBox.isSelected(),
                        substringCheckBox.isSelected()));

        dispose();
    }

    private void onCancel() {
        dispose();
    }
}

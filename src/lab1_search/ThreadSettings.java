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
    private JTextField patternTextField;
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
                patternTextField.setEnabled(true);
            } else {
                patternTextField.setEnabled(false);
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

        if (patternCheckBox.isSelected() && patternTextField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "You didn't specify a pattern!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (substringCheckBox.isSelected() && substringTextField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "You didn't specify a substring!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (substringCheckBox.isSelected() && patternCheckBox.isSelected()) {
            if (!patternTextField.getText().endsWith(".txt") ||
                    !patternTextField.getText().endsWith(".doc") ||
                    !patternTextField.getText().endsWith(".docx") ||
                    !patternTextField.getText().endsWith(".rtf") ||
                    !patternTextField.getText().endsWith(".log") ||
                    !patternTextField.getText().endsWith(".java")) {
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
        }

        thread.setSearchAttributes(
                new SearchAttributes(
                        directoryTextField.getText(),
                        patternTextField.getText(),
                        substringTextField.getText(),
                        subdirectoryCheckBox.isSelected(),
                        patternCheckBox.isSelected(),
                        substringCheckBox.isSelected()));

        thread.setButtons();
        dispose();
    }

    private void onCancel() {
        dispose();
    }
}

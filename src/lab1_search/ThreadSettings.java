package lab1_search;

import javax.swing.*;
import java.awt.event.*;

class ThreadSettings extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField directoryTextField;
    private JTextField templateTextField;
    private JTextField substringTextField;
    private JCheckBox subdirectoryCheckBox;
    private JCheckBox templateCheckBox;
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

        templateCheckBox.addItemListener(e -> {
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
        // TODO review!
        if (directoryTextField.getText().isEmpty()) { // TODO remove, search in / if directory is not chosen
            JOptionPane.showMessageDialog(this,
                    "Не выбрана директория для поиска!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (substringCheckBox.isSelected()) {
            if (templateCheckBox.isSelected()) {
                int size = templateTextField.getText().length();
                if (!templateTextField.getText().substring(size - 4).equals(".txt")
                        && !templateTextField.getText().substring(size - 4).equals(".doc")
                        && !templateTextField.getText().substring(size - 5).equals(".docx")) {
                    JOptionPane.showMessageDialog(this,
                            "Поиск по строке осуществляется только в файлах следующих форматов: .txt, .doc, .docx",
                            JOptionPane.ICON_PROPERTY, JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } else {
                templateTextField.setText("*.txt");
            }
        }

        thread.setSearchAttributes(new SearchAttributes(directoryTextField.getText(),
                                                        templateTextField.getText(),
                                                        substringTextField.getText(),
                                                        subdirectoryCheckBox.isSelected(),
                                                        templateCheckBox.isSelected(),
                                                        substringCheckBox.isSelected()));

        dispose();
    }

    private void onCancel() {
        dispose();
    }
}

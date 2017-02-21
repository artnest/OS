package lab1_search;

import javax.swing.*;
import java.awt.event.*;

public class ThreadSettings extends JDialog {
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

//    private ThreadForm parentForm;
    private SearchThread thread;

    /*public ThreadSettings(ThreadForm parentForm) {
        this.parentForm = parentForm;

        setContentPane(contentPane);
        setBounds(350, 150, 510, 250);
        setResizable(false);
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
            int ret = fileChooser.showDialog(null, "Choose directory..");
            if (ret == JFileChooser.APPROVE_OPTION) {
                directoryTextField.setText(fileChooser.getSelectedFile().getAbsolutePath());
            }
        });

        pack();
        setVisible(true);
    }*/

    public ThreadSettings(SearchThread thread) {
        this.thread = thread;

        setContentPane(contentPane);
        setBounds(350, 150, 510, 250);
        setResizable(false);
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
        setVisible(true);
    }

    private void onOK() {
        // TODO review!
        if (directoryTextField.getText().isEmpty()) {
            ErrorDialog errorDialog = new ErrorDialog("Не выбрана директория для поиска!");
            errorDialog.setVisible(true);
            return;
        }

        if (substringCheckBox.isSelected()) {
            if (templateCheckBox.isSelected()) {
                int size = templateTextField.getText().length();
                if (!templateTextField.getText().substring(size - 4).equals(".txt")
                        && !templateTextField.getText().substring(size - 4).equals(".doc")
                        && !templateTextField.getText().substring(size - 5).equals(".docx")) {
                    ErrorDialog errorDialog = new ErrorDialog("<html>Поиск по строке осуществляется только в <br> " +
                            "   файлах следующих форматов:<br>     .txt .doc .docx</html>");
                    errorDialog.setVisible(true);
                    return;
                }
            }
            else {
                templateTextField.setText("*.txt");
            }
        }

/*
        if (parentForm.isCheckThread1) {
            parentForm.searchAttributes1 = new SearchAttributes(directoryTextField.getText(), templateTextField.getText(), substringTextField.getText(),
                    subdirectoryCheckBox.isSelected(), templateCheckBox.isSelected(), substringCheckBox.isSelected());
            parentForm.isCheckThread1 = false;
        }
*/

/*
        if (parentForm.isCheckThread2) {
            parentForm.searchAttributes2 = new SearchAttributes(directoryTextField.getText(), templateTextField.getText(), substringTextField.getText(),
                    subdirectoryCheckBox.isSelected(), templateCheckBox.isSelected(), substringCheckBox.isSelected());
            parentForm.isCheckThread2 = false;
        }
*/
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

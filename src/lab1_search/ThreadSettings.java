package lab1_search;

import javax.swing.*;
import java.awt.event.*;

public class ThreadSettings extends JDialog {
    private ThreadForm parentForm;
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField directoryText;
    private JTextField templateText;
    private JTextField searchLineText;
    private JCheckBox subdirectoryCheckBox;
    private JCheckBox templateCheckBox;
    private JCheckBox searchLineCheckBox;
    private JButton chooserOpen;

    public ThreadSettings(ThreadForm parentForm) {
        this.parentForm = parentForm;

        setContentPane(contentPane);
        setBounds(350, 150, 510, 250);
        setResizable(false);
        //setSize(510, 250);
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

        chooserOpen.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int ret = fileChooser.showDialog(null, "Choose directory..");
            if (ret == JFileChooser.APPROVE_OPTION) {
                directoryText.setText(fileChooser.getSelectedFile().getAbsolutePath());
            }
        });
    }

    private void onOK() {
        if (directoryText.getText().isEmpty()) {
            ErrorDialog errorDialog = new ErrorDialog("Не выбрана директрория для поиска!");
            errorDialog.setVisible(true);
            return;
        }

        if (searchLineCheckBox.isSelected()) {
            if (templateCheckBox.isSelected()) {
                int size = templateText.getText().length();
                if (!templateText.getText().substring(size - 4).equals(".txt")
                        && !templateText.getText().substring(size - 4).equals(".doc")
                        && !templateText.getText().substring(size - 5).equals(".docx")) {
                    ErrorDialog errorDialog = new ErrorDialog("<html>Поиск по строке осуществляется только в <br> " +
                            "   файлах следующих форматов:<br>     .txt .doc .docx</html>");
                    errorDialog.setVisible(true);
                    return;
                }
            }
            else {
                templateText.setText("*.txt");
            }
        }

        if (parentForm.isCheckThread1) {
            parentForm.searchInfo1 = new SearchInfo(directoryText.getText(), templateText.getText(), searchLineText.getText(),
                    subdirectoryCheckBox.isSelected(), templateCheckBox.isSelected(), searchLineCheckBox.isSelected());
            parentForm.isCheckThread1 = false;
        }

        if (parentForm.isCheckThread2) {
            parentForm.searchInfo1 = new SearchInfo(directoryText.getText(), templateText.getText(), searchLineText.getText(),
                    subdirectoryCheckBox.isSelected(), templateCheckBox.isSelected(), searchLineCheckBox.isSelected());
            parentForm.isCheckThread2 = false;
        }
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
}

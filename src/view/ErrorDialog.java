package view;

import javax.swing.*;

/**
 * Created by hammer on 19.07.2017.
 */
public class ErrorDialog {
    public ErrorDialog(String message) {
        JOptionPane.showMessageDialog(null, message, "Что то пошло не так...", JOptionPane.ERROR_MESSAGE);
        System.out.println("press OK button");
        System.exit(0);
    }
}

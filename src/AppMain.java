import autorization.LoginDialog;
import dal.HibernateUtil;
import properties.AppSettings;
import view.ErrorDialog;
import view.MainFrame;

import javax.swing.*;
import java.io.File;

/**
 * Created by hammer on 14.07.2017.
 */
public class AppMain {
    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {

        UIManager.put("OptionPane.yesButtonText", "Да");
        UIManager.put("OptionPane.noButtonText", "Нет");
        UIManager.put("OptionPane.cancelButtonText", "Отмена");
        UIManager.put("OptionPane.okButtonText", "Готово");

        new MainFrame();
    }
}

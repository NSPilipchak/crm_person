package view;

import autorization.LoginDialog;
import com.mysql.jdbc.SQLError;
import com.mysql.jdbc.exceptions.jdbc4.CommunicationsException;
import dal.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.oxbow.swingbits.dialog.task.TaskDialog;
import org.oxbow.swingbits.dialog.task.TaskDialogs;
import properties.AppSettings;
import view.mainPane.GenerelMainPane;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.net.ConnectException;
import java.sql.SQLDataException;
import java.sql.SQLException;

import static properties.Properies.currentVersion;
import static properties.Properies.currentVersionDate;
import static properties.Strings.USER_ROLES;
import static properties.Strings.WORK_USER_NAME;
import static properties.Strings.WORK_USER_PERMISSION;

/**
 * Created by hammer on 14.07.2017.
 */
public class MainFrame extends JFrame {
    public static StatusBar statusBar;

    public MainFrame() {
        try {
            File file = new File("./", "crm_person.cfg");
            AppSettings.clear();
            AppSettings.load(file);
        } catch (Exception e) {
            e.printStackTrace();
        }

        statusBar = new StatusBar();

        Dimension sSize = Toolkit.getDefaultToolkit().getScreenSize();

        setBounds(30, 30, sSize.width - 100, sSize.height - 260);
        setTitle("Регистрация (ver." + currentVersion + ") " + currentVersionDate);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        statusBar.setStatusEnd();

        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        MenuBar mb = new MenuBar();
        setJMenuBar(mb);
        add(statusBar, BorderLayout.SOUTH);

        HibernateUtil.getSessionFactory().getCurrentSession();

        setVisible(true);
        new JFrame();

        mb.setEnableMenu();
        add(new GenerelMainPane(), BorderLayout.CENTER);

        setTitle("Регистрация (ver." + currentVersion + ") "
                + currentVersionDate
                + " Пользователь: " + WORK_USER_NAME
                + " Уровень доступа: " + USER_ROLES[WORK_USER_PERMISSION]);
    }
}

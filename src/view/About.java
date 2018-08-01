package view;

import blogic.entity.AppDM;
import properties.AppSettings;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static properties.Properies.currentVersion;
import static properties.Properies.currentVersionDate;
import static properties.Strings.USER_ROLES;
import static properties.Strings.WORK_USER_NAME;
import static properties.Strings.WORK_USER_PERMISSION;


/**
 * Created by hammer on 07.09.2017.
 */
public class About extends JDialog {
    private JTextArea textArea;
    private JTable tbl;
    private AppDM dm;
    private int realRow;

    public About() {
        setLayout(null);
        setTitle("О программе...");
        dm = new AppDM();

        Dimension sSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(sSize.width / 2 - 200, sSize.height / 2 - 225, 400, 450);
        setLayout(new BorderLayout());

        JPanel main = new JPanel();
        main.setLayout(new BorderLayout());
        main.setBorder(new EmptyBorder(10, 10, 10, 10));

        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setBackground(null);
        textArea.setBorder(null);

        String str = "Программное обеспечение разработанно \n" +
                "для регистрации, учета и хранения \n" +
                "персональных данных \n" +
                "Версия приложения: " + currentVersion + "\n" +
                "Дата сборки: " + currentVersionDate + "\n" +
                "Адрес сервера: " + AppSettings.get("DBurl").toString() + "\n" +
                "База данных: " + AppSettings.get("DBname").toString() + "\n" +
                "\n" +
                "Текущий пользователь: " + WORK_USER_NAME + "\n" +
                "Уровень доступа: " + USER_ROLES[WORK_USER_PERMISSION] + "\n" +
                "\n" +
                "E-mail авторa: kolich.x10@gmail.com";

        textArea.setText(str);

        main.add(textArea, BorderLayout.CENTER);

        main.add(appInfo(), BorderLayout.SOUTH);

        if (WORK_USER_NAME.equals("hammer")) {
            main.add(appEdit(), BorderLayout.NORTH);
            editTable();
        }

        add(main);
        setVisible(true);
    }

    private JPanel appInfo() {
        JPanel pane = new JPanel();
        pane.setLayout(null);
        pane.setLayout(new BorderLayout());
        pane.setPreferredSize(new Dimension(150, 100));
        pane.setBorder(new TitledBorder("База данных:"));

        tbl = new JTable(dm);
        tbl.getTableHeader().setReorderingAllowed(false);
        tbl.setEnabled(false);
        JScrollPane scr = new JScrollPane(tbl);
        tbl.getColumnModel().getColumn(0).setMinWidth(60);
        tbl.getColumnModel().getColumn(0).setMaxWidth(100);
        pane.add(scr, BorderLayout.CENTER);
        return pane;
    }

    private JPanel appEdit() {
        JPanel pane = new JPanel();
        pane.setLayout(null);
        pane.setLayout(new BorderLayout());
        pane.setPreferredSize(new Dimension(60, 20));

        JButton btn = new JButton("Edit");
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dm.update();
            }
        });
        pane.add(btn, BorderLayout.CENTER);
        return pane;
    }

    private void editTable() {
        tbl.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = tbl.rowAtPoint(e.getPoint());
                    if (row > -1) {
                        realRow = tbl.convertRowIndexToModel(row);
                        dm.row = realRow;
                    }
                    dm.update();
                } else if (e.getClickCount() == 1) {
                    int row = tbl.rowAtPoint(e.getPoint());
                    if (row > -1) {
                        realRow = tbl.convertRowIndexToModel(row);
                        dm.row = realRow;
                    }
                }
            }
        });
        tbl.getTableHeader().setReorderingAllowed(false);
        tbl.setEnabled(true);
    }
}

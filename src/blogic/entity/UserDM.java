package blogic.entity;

import dal.implementations.UserDAO_MySQL_Hibernate;
import dal.interfaces.UserDAO;
import view.dialogs.DgSmena;
import view.dialogs.DgUser;

import javax.swing.table.AbstractTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.ArrayList;

import static properties.Strings.USER_ROLES;
import static properties.Strings.USER_STATUS;

/**
 * Created by hammer on 12.09.2017.
 */
public class UserDM extends AbstractTableModel {
    private UserDAO pd = null;
    ArrayList<User> pp = null;
    private int userId = 0;
    public int row;

    public ActionRead aRead = new ActionRead();
    public ActionCreate aCreate = new ActionCreate();
    public ActionUpdate aUpdate = new ActionUpdate();
    public ActionDelete aDelete = new ActionDelete();

    public UserDM() {
        pd = new UserDAO_MySQL_Hibernate();
        pp = pd.getUserList();
    }

    public void update() {
        DgUser dg;
        try {
            dg = new DgUser();
            dg.setTitle("Карточка пользователя");
            dg.setModal(true);

            int i = row;
            User p;

            if (i >= 0) {
                p = pp.get(i);
                dg.setUser(p);
            }
            dg.setVisible(true);
            if (dg.ret.equals("Ok")) {
                pd.updateUser(dg.getUser());
            }
            UpdateTable up = new UpdateTable();
            up.start();
        } catch (ParseException e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public int getRowCount() {
        return pp.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        User u = pp.get(rowIndex);
        Object ret = null;
        switch (columnIndex) {
            case 0:
                ret = u.getId();
                break;
            case 1:
                ret = u.getUser();
                break;
            case 2:
                ret = USER_ROLES[u.getPermission()];
                break;
            case 3:
                ret = USER_STATUS[u.getStatus()];
                break;
        }
        return ret;
    }

    @Override
    public String getColumnName(int col) {
        String[] str = {"ИД", "Наименование", "Роль", "Статус"};
        return str[col];
    }

    private class ActionCreate implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            DgUser dd;
            try {
                dd = new DgUser();
                dd.setTitle("Содать нового пользователя");
                dd.setModal(true);
                dd.setVisible(true);

                if (dd.ret.equals("Ok")) {
                    pd.addUser(dd.getUser());
                }
                UpdateTable up = new UpdateTable();
                up.start();
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
        }
    }

    private class ActionUpdate implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            update();
        }
    }

    private class ActionDelete implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    private class ActionRead implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            UpdateTable up = new UpdateTable();
            up.start();
        }
    }

    private class UpdateTable extends Thread {
        @Override
        public void run() {
            pp = pd.getUserList();
            fireTableDataChanged();
        }
    }
}

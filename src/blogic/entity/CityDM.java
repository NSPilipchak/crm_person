package blogic.entity;

import dal.implementations.LibraryDAO_MySQL_Hibernate;
import dal.interfaces.LibraryDAO;
import view.dialogs.DgCity;

import javax.swing.table.AbstractTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.ArrayList;

public class CityDM extends AbstractTableModel {
    private LibraryDAO pd = null;
    ArrayList<City> pp = null;
    private int cognateId = 0;
    public int row;

    public ActionRead aRead = new ActionRead();
    public ActionCreate aCreate = new ActionCreate();
    public ActionUpdate aUpdate = new ActionUpdate();
    public ActionDelete aDelete = new ActionDelete();

    public CityDM() {
        pd = new LibraryDAO_MySQL_Hibernate();
        pp = pd.getCityList();
    }

    public void update() {
        DgCity dg;
        try {
            dg = new DgCity();
            dg.setTitle("Карточка город");
            dg.setModal(true);

            int i = row;
            City p;

            if (i >= 0) {
                p = pp.get(i);
                dg.setCity(p);
            }
            dg.setVisible(true);
            if (dg.ret.equals("Ok")) {
                pd.updateCity(dg.getCity());
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
        return 3;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        City u = pp.get(rowIndex);
        Object ret = null;
        switch (columnIndex) {
            case 0:
                ret = u.getId();
                break;
            case 1:
                ret = u.getCity();
                break;
            case 2:
                ret = u.getStatus();
                break;
        }
        return ret;
    }

    @Override
    public String getColumnName(int col) {
        String[] str = {"ИД", "Наименование", "Статус"};
        return str[col];
    }

    private class ActionRead implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            UpdateTable up = new UpdateTable();
            up.start();
        }
    }

    private class ActionCreate implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            DgCity dd;
            try {
                dd = new DgCity();
                dd.setTitle("Содание записи города");
                dd.setModal(true);
                dd.setVisible(true);

                if (dd.ret.equals("Ok")) {
                    pd.addCity(dd.getCity());
                }
                UpdateTable up = new UpdateTable();
                up.start();
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
        }
    }

    private class ActionDelete implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    private class ActionUpdate implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            update();
        }
    }

    private class UpdateTable extends Thread {
        @Override
        public void run() {
            pp = pd.getCityList();
            fireTableDataChanged();
        }
    }
}

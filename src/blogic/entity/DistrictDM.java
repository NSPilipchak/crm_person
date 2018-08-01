package blogic.entity;

import dal.implementations.LibraryDAO_MySQL_Hibernate;
import dal.interfaces.LibraryDAO;
import view.dialogs.DgCognate;
import view.dialogs.DgDistrict;

import javax.swing.table.AbstractTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.ArrayList;

public class DistrictDM extends AbstractTableModel {
    private LibraryDAO pd = null;
    ArrayList<District> pp = null;
    private int districtId = 0;
    public int row;

    public ActionRead aRead = new ActionRead();
    public ActionCreate aCreate = new ActionCreate();
    public ActionUpdate aUpdate = new ActionUpdate();
    public ActionDelete aDelete = new ActionDelete();

    public DistrictDM() {
        pd = new LibraryDAO_MySQL_Hibernate();
        pp = pd.getDistrictList();
    }

    public void update() {
        DgDistrict dg;
        try {
            dg = new DgDistrict();
            dg.setTitle("Карточка района города");
            dg.setModal(true);

            int i = row;
            District p;

            if (i >= 0) {
                p = pp.get(i);
                dg.setDistrict(p);
            }
            dg.setVisible(true);
            if (dg.ret.equals("Ok")) {
                pd.updateDistrict(dg.getDistrict());
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
        District u = pp.get(rowIndex);
        Object ret = null;
        switch (columnIndex) {
            case 0:
                ret = u.getId();
                break;
            case 1:
                ret = u.getDistrict();
                break;
            case 2:
                ret = u.getQuota();
                break;
            case 3:
                ret = u.getStatus();
                break;
        }
        return ret;
    }

    @Override
    public String getColumnName(int col) {
        String[] str = {"ИД", "Наименование", "Квота", "Статус"};
        return str[col];
    }

    private class ActionCreate implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            DgDistrict dd;
            try {
                dd = new DgDistrict();
                dd.setTitle("Содать новый район города");
                dd.setModal(true);
                dd.setVisible(true);

                if (dd.ret.equals("Ok")) {
                    pd.addDistrict(dd.getDistrict());
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
            pp = pd.getDistrictList();
            fireTableDataChanged();
        }
    }
}

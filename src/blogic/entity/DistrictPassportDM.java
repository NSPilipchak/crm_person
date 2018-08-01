package blogic.entity;

import dal.implementations.LibraryDAO_MySQL_Hibernate;
import dal.interfaces.LibraryDAO;
import view.dialogs.DgCognate;
import view.dialogs.DgDistrictPassport;

import javax.swing.table.AbstractTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.ArrayList;

public class DistrictPassportDM extends AbstractTableModel {
    private LibraryDAO pd = null;
    ArrayList<DistrictPassport> pp = null;
    private int districtPassportId = 0;
    public int row;

    public ActionRead aRead = new ActionRead();
    public ActionCreate aCreate = new ActionCreate();
    public ActionUpdate aUpdate = new ActionUpdate();
    public ActionDelete aDelete = new ActionDelete();

    public DistrictPassportDM() {
        pd = new LibraryDAO_MySQL_Hibernate();
        pp = pd.getDistrictPassportList();
    }

    public void update() {
        DgDistrictPassport dg;
        try {
            dg = new DgDistrictPassport();
            dg.setTitle("Карточка создания кем выдан паспорт");
            dg.setModal(true);

            int i = row;
            DistrictPassport p;

            if (i >= 0) {
                p = pp.get(i);
                dg.setDistrictPassport(p);
            }
            dg.setVisible(true);
            if (dg.ret.equals("Ok")) {
                pd.updateDistrictPassport(dg.getDistrictPassport());
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
        return 2;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        DistrictPassport u = pp.get(rowIndex);
        Object ret = null;
        switch (columnIndex) {
            case 0:
                ret = u.getId();
                break;
            case 1:
                ret = u.getDistrictPassport();
                break;
        }
        return ret;
    }

    @Override
    public String getColumnName(int col) {
        String[] str = {"ИД", "Наименование"};
        return str[col];
    }

    private class ActionCreate implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            DgDistrictPassport dd;
            try {
                dd = new DgDistrictPassport();
                dd.setTitle("Содание новой записи");
                dd.setModal(true);
                dd.setVisible(true);

                if (dd.ret.equals("Ok")) {
                    pd.addDistrictPassport(dd.getDistrictPassport());
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
            pp = pd.getDistrictPassportList();
            fireTableDataChanged();
        }
    }
}

package blogic.entity;

import dal.Factory;
import view.dialogs.DgApp;

import javax.swing.table.AbstractTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.ArrayList;

public class AppDM extends AbstractTableModel {
    ArrayList<App> pp = null;
    public int row;

    public AppDM() {
        pp = Factory.getLibraryDAO().getAppList();
    }

    public void update() {
        DgApp dg;
        try {
            dg = new DgApp();
            dg.setTitle("Изменение параметров базы");
            dg.setModal(true);

            int i = row;
            App p;

            if (i >= 0) {
                p = pp.get(i);
                dg.setApp(p);
            }
            dg.setVisible(true);
            if (dg.ret.equals("Ok")) {
                Factory.getLibraryDAO().updateApp(dg.getApp());
            }
            UpdateTable up = new UpdateTable();
            up.start();
        } catch (ParseException e1) {
            e1.printStackTrace();
        }
    }

    public ActionUpdate aUpdate = new ActionUpdate();

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
        App a = pp.get(rowIndex);
        Object ret = null;
        switch (columnIndex) {
            case 0:
                ret = a.getKeyStr();
                break;
            case 1:
                ret = a.getValueStr();
                break;
        }
        return ret;
    }

    @Override
    public String getColumnName(int col) {
        String[] str = {"Key", "Value"};
        return str[col];
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
            pp = Factory.getLibraryDAO().getAppList();
            fireTableDataChanged();
        }
    }
}

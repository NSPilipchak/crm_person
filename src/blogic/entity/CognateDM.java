package blogic.entity;

import dal.Factory;
import dal.implementations.LibraryDAO_MySQL_Hibernate;
import dal.interfaces.LibraryDAO;
import view.dialogs.DgCognate;

import javax.swing.table.AbstractTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.ArrayList;

public class CognateDM extends AbstractTableModel {
    private LibraryDAO pd = null;
    ArrayList<Cognate> pp = null;
    private int cognateId = 0;
    public int row;

    public ActionRead aRead = new ActionRead();
    public ActionCreate aCreate = new ActionCreate();
    public ActionUpdate aUpdate = new ActionUpdate();
    public ActionDelete aDelete = new ActionDelete();

    public CognateDM() {
        pd = new LibraryDAO_MySQL_Hibernate();
        pp = pd.getCognateList();
    }

    public void update() {
        DgCognate dg;
        try {
            dg = new DgCognate();
            dg.setTitle("Карточка родственной связи");
            dg.setModal(true);

            int i = row;
            Cognate p;

            if (i >= 0) {
                p = pp.get(i);
                dg.setCognate(p);
            }
            dg.setVisible(true);
            if (dg.ret.equals("Ok")) {
                pd.updateCognate(dg.getCognate());
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
        Cognate u = pp.get(rowIndex);
        Object ret = null;
        switch (columnIndex) {
            case 0:
                ret = u.getId();
                break;
            case 1:
                ret = u.getCognate();
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
            DgCognate dd;
            try {
                dd = new DgCognate();
                dd.setTitle("Содание новой родственной связи");
                dd.setModal(true);
                dd.setVisible(true);

                if (dd.ret.equals("Ok")) {
                    pd.addCognate(dd.getCognate());
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
            pp = pd.getCognateList();
            fireTableDataChanged();
        }
    }
}

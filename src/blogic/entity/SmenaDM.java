package blogic.entity;

import dal.Factory;
import dal.interfaces.SmenaDAO;
import dal.implementations.SmenaDAO_MySQL_Hibernate;
import view.dialogs.DgSmena;
import view.mainPane.dialog.ListSmena;

import javax.persistence.PersistenceException;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.ArrayList;

import static properties.Properies.permission;
import static properties.Strings.ITEM_YEAR;
import static view.MainFrame.statusBar;

/**
 * Created by hammer on 20.07.2017.
 */
public class SmenaDM extends AbstractTableModel implements DataModel {

    private SmenaDAO pd = null;
    ArrayList<Smena> pp = null;
    public int row;
    public int selectedId;
    private int personId = 0;

    public SmenaDM() {
        pd = new SmenaDAO_MySQL_Hibernate();
        pp = pd.getAllSmena();
    }


    public SmenaDM(int personId) {
        this.personId = personId;
        pd = new SmenaDAO_MySQL_Hibernate();
        pp = pd.getSmenaByPerson(personId);
    }

    public ActionRead aRead = new ActionRead();
    public ActionCreate aCreate = new ActionCreate();
    public ActionUpdate aUpdate = new ActionUpdate();
    public ActionDelete aDelete = new ActionDelete();
    public ActionAddToPerson aAddToPerson = new ActionAddToPerson();
    public ActionDeleteFromPerson aDeleteFromPerson = new ActionDeleteFromPerson();

    public void update() {
        DgSmena dg;
        try {
            dg = new DgSmena();
            dg.setTitle("Обновление карточки персоны");
            dg.setModal(true);

            int i = row;
            Smena p;

            if (i >= 0) {
                p = pp.get(i);
                dg.setTitle("Обновление карточки: " + p.getName());
                dg.setSmena(p);
            }
            dg.setVisible(true);
            if (dg.ret.equals("Ok") && permission()) {
                pd.updateSmena(dg.getSmena());
            }
            UpdateTable up = new UpdateTable();
            up.start();
        } catch (ParseException e1) {
            e1.printStackTrace();
        }
    }

    public void read() {
        UpdateTable up = new UpdateTable();
        up.start();
    }

    @Override
    public int getRowCount() {
        return pp.size();
    }

    @Override
    public int getColumnCount() {
        return 6;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (pp.size() == 0)
            return null;

        Smena p = pp.get(rowIndex);
        Object ret = null;
        switch (columnIndex) {
            case 0:
                ret = p.getId();
                break;
            case 1:
                ret = ITEM_YEAR[p.getYear()];
                break;
            case 2:
                ret = p.getNumber();
                break;
            case 3:
                ret = p.getName();
                break;
            case 4:
                ret = p.getPlaces();
                break;
            case 5:
                ret = p.getPlaces() - p.getBusyPlaces();
                break;
        }
        return ret;
    }

    @Override
    public String getColumnName(int col) {
        String[] str = {"ИД", "Сезон", "Номер недели", "Наименование", "Кол-во мест", "Свободно"};
        return str[col];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (pp.size() == 0)
            return null;

        Class returnValue;
        if ((columnIndex >= 0) && (columnIndex < getColumnCount() - 1)) {
            returnValue = getValueAt(0, columnIndex).getClass();
        } else {
            returnValue = String.class;
        }
        return returnValue;
    }

    private class ActionRead implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            statusBar.setStatus("Вычитка из базы данных)");
            UpdateTable up = new UpdateTable();
            up.start();
        }
    }

    private class ActionCreate implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            DgSmena dd;
            try {
                dd = new DgSmena();
                dd.setTitle("Содать новый элемент");
                dd.setModal(true);
                dd.setVisible(true);

                if (dd.ret.equals("Ok") && permission()) {
                    pd.addSmena(dd.getSmena());

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

    private class ActionAddToPerson implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ListSmena ls = new ListSmena(personId);
            ls.setTitle("Доступные смены");
            ls.setModal(true);
            ls.setVisible(true);

            if (ls.ret.equals("Ok")) {
                try {
                    Person person = Factory.getPersonDAO().getPerson(personId);
                    Smena smena = Factory.getSmenaDAO().getSmena(ls.realRow);
                    int firstSize = person.getSmenasList().size();
                    person.getSmenasList().add(smena);
                    int newSize = person.getSmenasList().size();
                    if (firstSize != newSize) {
                        Factory.getPersonDAO().updatePerson(person);
                        Factory.getSmenaDAO().changeCountSmena(smena.getId(), 0, 1);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(ls, "Данная смена уже добавлена", "Что то пошло не так...", JOptionPane.INFORMATION_MESSAGE);
                }
            }
            read();
        }
    }

    private class ActionDeleteFromPerson implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                Person person = Factory.getPersonDAO().getPerson(personId);
                Smena smena = Factory.getSmenaDAO().getSmena(selectedId);
                int firstSize = person.getSmenasList().size();
                person.getSmenasList().remove(smena);
                int newSize = person.getSmenasList().size();
                if (firstSize != newSize) {
                    Factory.getPersonDAO().updatePerson(person);
                    Factory.getSmenaDAO().changeCountSmena(smena.getId(), 0, -1);
                }
            } catch (PersistenceException ex) {
                JOptionPane.showMessageDialog(null, "Данная смена отсутсвует в списке", "Что то пошло не так...", JOptionPane.INFORMATION_MESSAGE);
            }
            read();
        }
    }

    private class UpdateTable extends Thread {
        @Override
        public void run() {
            if (personId == 0) {
                pp = pd.getAllSmena();
            } else {
                pp = pd.getSmenaByPerson(personId);
            }
            fireTableDataChanged();
        }
    }

    /**
     * Кусок кода для отбора смен при построении отчетов, множественного отбора
     */
    public SmenaDM(ArrayList<Smena> smenaList) {
        fireTableDataChanged();
        pp = smenaList;
        fireTableDataChanged();
    }

    public void reportDeleteSemena(int index) {
        pp.remove(index);
        System.out.println("pp.remove(index)" + index);
        try {
            fireTableDataChanged();
        } catch (NullPointerException ex) {

        }

    }

    public void reportAddSemena(Smena smena) {
        pp.add(smena);
        fireTableDataChanged();
    }


}

package blogic.entity;

import dal.Factory;
import dal.interfaces.FamilyDAO;
import dal.implementations.FamilyDAO_MySQL_Hibernate_2;
import view.dialogs.DgFamily;

import javax.swing.table.AbstractTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.ArrayList;

import static properties.Properies.permission;
import static view.MainFrame.statusBar;

/**
 * Created by hammer on 20.07.2017.
 */
public class FamilyDM extends AbstractTableModel implements DataModel {

    private FamilyDAO pd = null;
    ArrayList<Family> pp = null;
    public int row;
    public Person person;
    private int personId = 0;

    public FamilyDM() {
        statusBar.setStatus("Загрузка персон...");
        pd = new FamilyDAO_MySQL_Hibernate_2();
        pp = pd.getAllFamily();
        statusBar.setStatusEnd();
    }

    public FamilyDM(int personId) {
        this.personId = personId;
        pd = new FamilyDAO_MySQL_Hibernate_2();
        pp = pd.getFamilyByPerson(personId);
    }

    public ActionRead aRead = new ActionRead();
    public ActionAddToPerson aAddToPerson = new ActionAddToPerson();
    public ActionUpdateToPerson aUpdateToPerson = new ActionUpdateToPerson();
    public ActionCreate aCreate = new ActionCreate();
    public ActionUpdate aUpdate = new ActionUpdate();
    public ActionDelete aDelete = new ActionDelete();

    public void update() {
        DgFamily dg;
        try {
            dg = new DgFamily();
            dg.setModal(true);

            int i = row;
            Family p;

            if (i >= 0) {
                p = pp.get(i);
                dg.setTitle("Обновление карточки: " + p.getName());
                dg.setFamily(p);
            }
            dg.setVisible(true);
            if (dg.ret.equals("Ok") && permission()) {
                pd.updateFamily(dg.getFamily());
                pp = pd.getAllFamily();
                fireTableDataChanged();
            }
        } catch (ParseException e1) {
            e1.printStackTrace();
        }
    }

    public void updateToPerson() {
        DgFamily dg;
        try {
            dg = new DgFamily();
            dg.setModal(true);

            int i = row;
            Family p;

            if (i >= 0) {
                p = pp.get(i);
                dg.setTitle("Обновление карточки: " + p.getName());
                dg.setFamily(p);
            }
            dg.setVisible(true);
            if (dg.ret.equals("Ok") && permission()) {
                Person person = Factory.getPersonDAO().getPerson(personId);
                Family family = dg.getFamily();
                Factory.getFamilyDAO().updateFamily(family);
                person.getFamilyList().add(family);
                Factory.getPersonDAO().updatePerson(person);
            }
            read();
            fireTableDataChanged();
        } catch (ParseException e1) {
            e1.printStackTrace();
        }
    }

    public void read() {
        if (personId == 0) {
            pp = pd.getAllFamily();
        } else {
            pp = pd.getFamilyByPerson(personId);
        }
        if (pp.size() != 0)
            fireTableDataChanged();
    }

    public void delete() {
        DgFamily dg;
        try {
            dg = new DgFamily();
            dg.setModal(true);

            int i = row;
            Family p;

            if (i >= 0) {
                p = pp.get(i);
                dg.setTitle("Удалить карточку: " + p.getName());
                dg.setFamily(p);
            }
            dg.setVisible(true);
            if (dg.ret.equals("Ok") && permission()) {
                pd.deleteFamily(dg.getFamily());
            }
            read();
            fireTableDataChanged();
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
        if (pp.size() == 0)
            return null;
        Family p = pp.get(rowIndex);
        Object ret = null;
        switch (columnIndex) {
            case 0:
                ret = p.getId();
                break;
            case 1:
                ret = p.getName();
                break;
            case 2:
                ret = p.getCognate();
                break;
            case 3:
                ret = p.getStatus();
                break;
        }
        return ret;
    }

    @Override
    public String getColumnName(int col) {
        String[] str = {"ИД", "ФИО", "Родственная связь", "Статус"};
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
            read();
        }
    }

    private class ActionAddToPerson implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            DgFamily dd;
            try {
                dd = new DgFamily();
                dd.setTitle("Добавить новый");
                dd.setModal(true);
                dd.setVisible(true);

                if (dd.ret.equals("Ok") && permission()) {
                    Person person = Factory.getPersonDAO().getPerson(personId);
                    Family family = dd.getFamily();
                    Factory.getFamilyDAO().addFamily(family);
                    person.getFamilyList().add(family);
                    Factory.getPersonDAO().updatePerson(person);
                }
                read();
                if (pp.size() != 0)
                    fireTableDataChanged();
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
            if (pp.size() != 0)
                fireTableDataChanged();
        }
    }

    private class ActionCreate implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            DgFamily dd;
            try {
                dd = new DgFamily();
                dd.setTitle("Содать новый элемент");
                dd.setModal(true);
                dd.setVisible(true);

                if (dd.ret.equals("Ok") && permission()) {
                    if (person == null) {
                        pd.addFamily(dd.getFamily());
                        pp = pd.getAllFamily();
                    } else {
//                        pd.getFamilyByPerson(dd.getFamily(), person);
//                        pp = pd.getFamilyByPerson(person);
//                        person = null;
                    }
                    read();
                    fireTableDataChanged();
                }
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
        }
    }

    private class ActionUpdate implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            update();
            read();
            fireTableDataChanged();
        }
    }

    private class ActionDelete implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            delete();
        }
    }

    private class ActionUpdateToPerson implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            updateToPerson();
        }
    }
}

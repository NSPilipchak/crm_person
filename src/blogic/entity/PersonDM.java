package blogic.entity;

import dal.Factory;
import dal.interfaces.PersonDAO;
import dal.implementations.PersonDAO_MySQL_Hibenate_2;
import view.dialogs.DgPerson;

import javax.swing.table.AbstractTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.ArrayList;

import static properties.Properies.permission;
import static view.MainFrame.statusBar;

/**
 * Created by hammer on 14.07.2017.
 */
public class PersonDM extends AbstractTableModel implements DataModel {

    private PersonDAO pd = null;
    ArrayList<Person> pp = null;
    public int row;

    public PersonDM() {
        statusBar.setStatus("Загрузка персон...");
        pd = new PersonDAO_MySQL_Hibenate_2();
        pp = pd.getAllPerson();
        statusBar.setStatusEnd();
    }

    public ActionRead aRead = new ActionRead();
    public ActionUpdate aUpdate = new ActionUpdate();
    public ActionCreate aCreate = new ActionCreate();
    public ActionDelete aDelete = new ActionDelete();

    public void update() {
        DgPerson dg = null;
        try {
            dg = new DgPerson();
            dg.setModal(true);

            int i = row;
            Person person = null;
            PersonData personData = null;

            if (i >= 0) {
                person = pp.get(i);
                personData = pd.findPersonDataById(person.getPersonDataId());
                dg.setTitle("Обновление карточки: " + person.getlName() + " " + person.getfName());
                dg.setPerson(person);
                dg.setPersonData(personData);
            }
            dg.setVisible(true);
            if (dg.ret.equals("Ok") && permission()) {
                Factory.getPersonDataDAO().updatePersonData(dg.getPersonData());
                Person personNew = dg.getPerson();
                personNew.setPersonData(personData);
                pd.updatePerson(personNew);
                pp = pd.getAllPerson();
                fireTableDataChanged();
            }
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
        return 5;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Person p = pp.get(rowIndex);
        Object ret = null;
        switch (columnIndex) {
            case 0:
                ret = p.getId();
                break;
            case 1:
                ret = p.getlName();
                break;
            case 2:
                ret = p.getfName();
                break;
            case 3:
                ret = p.getmName();
                break;
            case 4:
                ret = p.getStatus();
                break;
        }
        return ret;
    }

    @Override
    public String getColumnName(int col) {
        String[] str = {"ИД", "Фамилия", "Имя", "Отчество", "Status"};
        return str[col];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
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
            statusBar.setStatus("Вычитка из базы данных");
            pp = pd.getAllPerson();
            fireTableDataChanged();
        }
    }

    private class ActionCreate implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            DgPerson dd;
            try {
                dd = new DgPerson();
                dd.setTitle("Содать новый элемент");
                dd.setModal(true);
                dd.setVisible(true);

                if (dd.ret.equals("Ok") && permission()) {
                    PersonData personData = dd.getPersonData();
                    personData.setStatus(1); // у новой персоны всегда статус 1
                    Factory.getPersonDataDAO().addPersonData(personData);
                    Person person = dd.getPerson();
                    person.setStatus(1); // у новой персоны всегда статус 1
                    person.setPersonData(personData);
                    pd.addPerson(person);
                    pp = pd.getAllPerson();
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
        }
    }

    private class ActionDelete implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            DgPerson dd;
            try {
                dd = new DgPerson();
                dd.setModal(true);

                int i = row;
                Person person = null;
                PersonData personData = null;

                if (i >= 0) {
                    person = pp.get(i);
                    personData = pd.findPersonDataById(person.getPersonDataId());
                    dd.setTitle("Пометить на удаление элемент: " + person.getlName() + " " + person.getfName());
                    dd.setPerson(person);
                    dd.setPersonData(personData);
                }
                dd.setVisible(true);
                if (dd.ret.equals("Ok") && permission()) {
                    personData.setStatus(0); //из базы элемент не удаляется. статус = 0
                    Factory.getPersonDataDAO().updatePersonData(personData);
                    person.setStatus(0); //из базы элемент не удаляется. статус = 0
                    person.setPersonData(personData);
                    pd.updatePerson(person);
                    pp = pd.getAllPerson();
                    fireTableDataChanged();
                }
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
        }
    }
}

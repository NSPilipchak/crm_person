package blogic.entity;

import dal.Factory;
import dal.implementations.PersonDAO_MySQL_Hibenate_2;
import dal.interfaces.PersonDAO;
import view.mainPane.NavigateCommand;
import view.mainPane.dialog.DgGeneral;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.ArrayList;

import static properties.Properies.*;
import static view.MainFrame.statusBar;

/**
 * Created by hammer on 14.07.2017.
 */
public class PersonDMwithPersonData extends AbstractTableModel implements DataModel {

    private PersonDAO pd = null;
    ArrayList<Person> pp = new ArrayList<>();
    public int row;

    NavigateCommand cmd;

    private int maxRow;
    private int first;
    private int maxResult;

    public void selectPage(int first) {
        this.first = first;
    }

    public PersonDMwithPersonData(NavigateCommand cmd) {
        this.cmd = cmd;
        pd = new PersonDAO_MySQL_Hibenate_2();
        maxRow = MAX_ROW_BASE ? pd.countPerson() : Integer.parseInt(MAX_ROW);
        maxResult = Integer.parseInt(MAX_RESULT);
        statusBar.setStatusEnd();
    }

    public ActionRead aRead = new ActionRead();
    public ActionUpdate aUpdate = new ActionUpdate();
    public ActionCreate aCreate = new ActionCreate();
    public ActionDelete aDelete = new ActionDelete();

    public void changeStatus(int status) {
        Person person;
        int i = row;
        if (i >= 0 && permission()) {
            person = pp.get(i);
            person.setStatus(status);
            pd.updatePerson(person);
            Factory.getSmenaDAO().reCountSmena();
            UpdateTable up = new UpdateTable();
            up.start();
        }
    }

    public void update() {
        if (checkOpenDialog()) {
            DgGeneral dg = null;
            try {
                dg = new DgGeneral(true);
                dg.setModal(true);

                int i = row;
                Person person = null;
                PersonData personData = null;

                if (i >= 0) {
                    person = pp.get(i);
                    personData = person.getPersonData();
                    System.out.println(personData);
                    dg.setTitle("Редактирование карточки: " + person.getlName() + " " + person.getfName());
                    dg.setPerson(person);
                    dg.setPersonData(personData);
                    dg.setUpdateForm(0);
                    dg.addFamilyList(person.getId());
                    dg.addSmenaList(person.getId());
                }
                dg.setVisible(true);
                if (dg.ret.equals("Ok") && permission()) {
                    Factory.getPersonDataDAO().updatePersonData(dg.getPersonData());
                    Person personNew = dg.getPerson();
                    personNew.setPersonData(personData);
                    personNew.setFamilyList(person.getFamilyList());
                    personNew.setSmenasList(person.getSmenasList());
                    pd.updatePerson(personNew);
                }
                UpdateTable up = new UpdateTable();
                up.start();
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
        } else {
            int i = JOptionPane.showConfirmDialog(null,
                    "Данная запись имеет пометку на удаление.\n" +
                            "Если Вы хотите внести правки в данную персону ее нужно восстановить.\n" +
                            "Восстановить?", "Внимание удаленная запись", JOptionPane.YES_NO_OPTION);
            if (i == 0 && permission()) {
                System.out.println(Factory.getPersonDAO().getPerson(row + 1));
                Person p = Factory.getPersonDAO().getPerson(row + 1);
                p.setStatus(1);
                Factory.getPersonDAO().updatePerson(p);
                System.out.println(Factory.getPersonDAO().getPerson(row));
                UpdateTable up = new UpdateTable();
                up.start();
                update();
            }
        }
    }

    public void reRead() {
//        pp = pd.getAllPerson(first, maxResult);
        if (cmd.isFind())
            pp = pd.searchPerson(cmd.getSearch(), cmd.getSearchParam(), false);
        else
            pp = pd.getFiltredPerson(first, maxResult, cmd.getArrSmena(), cmd.getArrStatus(), cmd.getArrVerification());

        if (pp.size() != 0)
            fireTableDataChanged();
    }

    public void searchFromBase(String str) {
        pp = pd.searchPerson(str);
        fireTableDataChanged();
    }

    private boolean checkOpenDialog() {
        int i = row;
        if (i >= 0)
            if (pp.get(i).getStatus() != 0)
                return true;
        return false;
    }


    @Override
    public int getRowCount() {
        return pp == null ? 0 : pp.size();
    }

    @Override
    public int getColumnCount() {
        return 9;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (pp.size() == 0)
            return null;
        Person p = pp.get(rowIndex);
        Object ret = null;
        switch (columnIndex) {
            case 0:
                ret = p.getId();
                break;
            case 1:
                ret = p.getCode();
                break;
            case 2:
                ret = p.getlName();
                break;
            case 3:
                ret = p.getfName();
                break;
            case 4:
                ret = p.getmName();
                break;
            case 5:
                ret = p.getInn();
                break;
            case 6:
                ret = p.getPersonData().getsPassport();
                break;
            case 7:
                ret = p.getfPhone();
                break;
            case 8:
                ret = p.getStatus() + ", " + p.getPersonData().getVerificationDocum();
                break;
        }
        return ret;
    }

    @Override
    public String getColumnName(int col) {
        String[] str = {"ИД", "Идентификатор", "Фамилия", "Имя", "Отчество", "ИНН", "Паспорт", "Телефон", "Status"};
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
            DgGeneral dd;
            try {
                dd = new DgGeneral(true);
                dd.setTitle("Содать новую карточку");
                dd.setModal(true);
                dd.setVisible(true);

                if (dd.ret.equals("Ok") && permission()) {
                    PersonData personData = dd.getPersonData();
                    personData.setStatus(1); // у новой персоны всегда статус 1
                    Factory.getPersonDataDAO().addPersonData(personData);
                    Person person = dd.getPerson();
                    person.setStatus(1); // у новой персоны всегда статус 1
                    person.setPersonData(personData);
                    int id = pd.addPerson(person);

                    person = Factory.getPersonDAO().getPerson(id);
                    Smena smena = Factory.getSmenaDAO().getSmena(57);
                    person.getSmenasList().add(smena);
                    Factory.getPersonDAO().updatePerson(person);

                    UpdateTable up = new UpdateTable();
                    up.start();
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
            DgGeneral dd;
            try {
                dd = new DgGeneral(false);
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
                    dd.addFamilyList(person.getId());
                    dd.addSmenaList(person.getId());
                }
                dd.setVisible(true);
                if (dd.ret.equals("Ok") && permission()) {
                    personData.setStatus(0); //из базы элемент не удаляется. статус = 0
                    Factory.getPersonDataDAO().updatePersonData(personData);
                    person.setStatus(0); //из базы элемент не удаляется. статус = 0
                    person.setComment(dd.getPerson().getComment());
                    person.setPersonData(personData);
                    pd.updatePerson(person);

                    UpdateTable up = new UpdateTable();
                    up.start();
                }
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
        }
    }

    private class UpdateTable extends Thread {
        @Override
        public void run() {
            if (cmd.isFind())
                pp = pd.searchPerson(cmd.getSearch(), cmd.getSearchParam(), false);
            else
                pp = pd.getFiltredPerson(first, maxResult, cmd.getArrSmena(), cmd.getArrStatus(), cmd.getArrVerification());

            fireTableDataChanged();
        }
    }

    public int getMaxRow() {
        return maxRow;
    }

    public void setMaxResult(int maxResult) {
        if (maxResult > maxRow)
            this.maxResult = maxRow;
        else
            this.maxResult = maxResult;
    }

    public int getMaxResult() {
        return maxResult;
    }

    public int getFirst() {
        return first;
    }
}

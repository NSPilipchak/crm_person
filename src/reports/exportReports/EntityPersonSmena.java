package reports.exportReports;

import blogic.entity.City;
import blogic.entity.Person;
import blogic.entity.Smena;
import blogic.entity.SmenaDM;
import dal.Factory;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.AbstractTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import static properties.Properies.CROP_REPORT;
import static properties.Strings.STATUS_PERSON;

/**
 * Created by hammer on 17.08.2017.
 */
class EntityPersonSmena extends AbstractTableModel {

    /**
     * Модель данных для построения отчета в разрезе:
     * -  смена, множество смен
     * -  статус персоны, множество статусов
     * -
     * <p>
     * Необходимые данные:
     * -   Порядковый номер в списке
     * -   ИД+Код персоны
     * -   Прізвище, ім'я, по батькові
     * -   Дата народження
     * -   Ідентифікаційний код
     * -   Серія та номер паспорта
     * -   Адреса реєстрації
     */

    private ArrayList<Person> pp = new ArrayList<>();
    private ArrayList smena = new ArrayList();
    private ArrayList<Smena> smenaList = new ArrayList<>();
    private ArrayList status = new ArrayList();
    private SmenaDM dmSelectedSmena;
    private boolean boolBus = false;
    private String[] ITEM_CITY;

    ActionRead aRead = new ActionRead();
    ActionOption aOption = new ActionOption();
    ActionSave aSave = new ActionSave();

    EntityPersonSmena() {
        ITEM_CITY = getCity();
        getStatus().add(1);
        dmSelectedSmena = new SmenaDM(smenaList);
        fireTableDataChanged();
    }

    void updateModel() {
        if (CROP_REPORT)
            if (smenaList.size() > 2)
                cutSmenaList();
        Iterator it = smenaList.iterator();
        while (it.hasNext()) {
            Smena s = (Smena) it.next();
            getSmena().add(s.getId());
        }
        if (smena.size() != 0 && status.size() != 0) {
            pp = Factory.getPersonDAO().findPersonBySmenaAndStatus(getSmena(), status, false);
            fireTableDataChanged();
        }
    }

    private void options() {
        OptionReport or = new OptionReport(dmSelectedSmena);
        or.setTitle("Налаштування ");
        or.setCheckBoxStatus(status);
        or.setModal(true);
        or.setVisible(true);
        if (or.ret.equals("Ok")) {
            status = or.arrStatus;
            boolBus = or.boolBus;
        }

        updateModel();
    }

    @Override
    public int getRowCount() {
        return pp.size();
    }

    @Override
    public int getColumnCount() {
        return 7;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Person p = pp.get(rowIndex);
        Object ret = null;
        switch (columnIndex) {
            case 0:
                ret = p.getId() + "-" + p.getCode();
                break;
            case 1:
                ret = p.getlName() + " " + p.getfName() + " " + p.getmName();
                break;
            case 2:
                ret = p.getbDate();
                break;
            case 3:
                ret = p.getInn();
                break;
            case 4:
                ret = p.getPersonData().getsPassport();
                break;
            case 5:
                ret = ITEM_CITY[p.getPersonData().getCity()] + ", "
                        + p.getPersonData().getStreet() + ", буд. " + p.getPersonData().getHouse();
                if (p.getPersonData().getCorp() != null)
                    ret += ", корп. " + p.getPersonData().getCorp();

                if (p.getPersonData().getFlat() != null)
                    ret += ", кв. " + p.getPersonData().getFlat();
                break;
            case 6:
                ret = STATUS_PERSON[p.getStatus()];
                break;

        }
        return ret;
    }

    @Override
    public String getColumnName(int col) {
        String[] str = {"Код", "Прізвище, ім'я, по батькові",
                "Дата народження",
                "Ідентифікаційний код", "Серія та номер паспорта",
                "Адреса реєстрації", "Статус"};
        return str[col];
    }

    private class ActionRead implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            updateModel();
        }
    }

    private class ActionOption implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            options();
        }
    }

    private class ActionSave implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (pp.size() == 0)
                options();

            try {
                JFileChooser fd = new JFileChooser();
                fd.resetChoosableFileFilters();
                fd.setAcceptAllFileFilterUsed(false);
                fd.setFileFilter(new FileNameExtensionFilter("XLS files", "xls"));
                WriteIntoExcel wir = new WriteIntoExcel();
                if (fd.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                    File file = new File(fd.getSelectedFile() + ".xls");
                    wir.writeIntoExcel(file, pp, smenaList, status, boolBus);
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    private void cutSmenaList() {
        ArrayList<Smena> smenaListRet = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            smenaListRet.add(smenaList.get(i));
        }
        smenaList = smenaListRet;
    }

    ArrayList getSmena() {
        return smena;
    }

    void setSmena(ArrayList smena) {
        this.smena = smena;
    }

    ArrayList getStatus() {
        return status;
    }

    void setStatus(ArrayList status) {
        this.status = status;
    }

    private String[] getCity() {
        ArrayList<City> citiess = Factory.getLibraryDAO().getCityList();
        String[] ret = new String[citiess.size()];
        for (int i = 0; i < citiess.size(); i++) {
            ret[i] = citiess.get(i).getCity();
        }
        return ret;
    }
}

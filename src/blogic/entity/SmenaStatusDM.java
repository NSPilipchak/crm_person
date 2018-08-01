package blogic.entity;

import dal.Factory;
import dal.implementations.SmenaDAO_MySQL_Hibernate;
import dal.interfaces.SmenaDAO;
import view.mainPane.NavigateCommand;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

import static properties.Properies.STATUS_BASE_UPDATE;
import static properties.Strings.ITEM_YEAR;

/**
 * Created by hammer on 20.07.2017.
 */
public class SmenaStatusDM extends AbstractTableModel {

    private SmenaDAO pd = null;
    ArrayList<Smena> pp = null;
    NavigateCommand cmd;

    private String[] columnName = createColumnName();

    private String[] createColumnName() {
        String[] name = {"Наименование", "Кол-во мест", "Занято мест",
                "Свободно мест"};

        int size = name.length;// + districts.length;
        String[] columnName = new String[size];
        for (int i = 0; i < columnName.length; i++) {
            if (i < name.length)
                columnName[i] = name[i];
        }
        return columnName;
    }

    private String[] getDistricts() {
        ArrayList<District> districts = Factory.getLibraryDAO().getDistrictList();
        String[] ret = new String[districts.size()];
        for (int i = 0; i < districts.size(); i++) {
            ret[i] = districts.get(i).getDistrict();
        }
        return ret;
    }

    public SmenaStatusDM(NavigateCommand cmd) {
        this.cmd = cmd;
        pd = new SmenaDAO_MySQL_Hibernate();
        pp = pd.getSmenaById(cmd.getArrSmena());
        UpdateTable up = new UpdateTable();
        up.setDaemon(true);
        up.start();
    }

    @Override
    public int getRowCount() {
        if (pp == null)
            return 0;
        return pp.size();
    }

    @Override
    public int getColumnCount() {
        return columnName.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (pp.size() == 0)
            return null;

        Smena p = pp.get(rowIndex);
        Object ret = null;
        switch (columnIndex) {
            case 0:
                ret = ITEM_YEAR[p.getYear()] + "г. " + p.getNumber() + "нед. " + p.getName() + " c " + p.getDateStart();
                break;
            case 1:
                ret = p.getPlaces();
                break;
            case 2:
                ret = p.getBusyPlaces();
                break;
            case 3:
                ret = (p.getPlaces() - p.getBusyPlaces());
                break;
        }
        return ret;
    }


    @Override
    public String getColumnName(int col) {
        return columnName[col];
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

    private class UpdateTable extends Thread {
        @Override
        public void run() {
            while (true) {
                try {
                    pp = pd.getSmenaById(cmd.getArrSmena());
                    fireTableDataChanged();
                    Thread.sleep(STATUS_BASE_UPDATE);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

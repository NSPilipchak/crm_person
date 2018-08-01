package blogic.entity;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by hammer on 22.07.2017.
 */
public interface DataModel {

    public abstract void update();

    public abstract int getRowCount();

    public abstract int getColumnCount();

    public abstract Object getValueAt(int rowIndex, int columnIndex);

    public abstract String getColumnName(int col);

    public abstract Class<?> getColumnClass(int columnIndex);

    public static class ActionRead implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    public static class ActionUpdate implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    public static class ActionCreate implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    public static class ActionDelete implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }
}

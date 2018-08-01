package view.mainPane.baseStatus;

import blogic.entity.SmenaStatusDM;
import view.mainPane.*;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

/**
 * Created by hammer on 11.09.2017.
 */
public class BaseStatusPanel extends JPanel {
    private SmenaStatusDM dm = null;
    NavigateCommand cmd;

    public BaseStatusPanel(NavigateCommand cmd) {
        this.cmd = cmd;
        setLayout(null);
        setLayout(new BorderLayout());
        dm = new SmenaStatusDM(cmd);

        JTable tbl = new JTable(dm);
        tbl.getTableHeader().setReorderingAllowed(false);
        setColumnSize(tbl);
        tbl.setEnabled(false);
        TableRenderers tableRenderer = new TableRenderers();
        for (int i = 0; i < tbl.getColumnCount(); i++)
            tbl.getColumnModel().getColumn(i).setCellRenderer(tableRenderer);

        JScrollPane scr = new JScrollPane(tbl);
        scr.setBorder(new TitledBorder("Наполненность смен:"));

        JPanel mainTable = new JPanel();
        mainTable.setLayout(new BorderLayout());
        mainTable.setBorder(new TitledBorder("Аналитические данные:"));

        mainTable.add(scr, BorderLayout.CENTER);
        add(mainTable, BorderLayout.CENTER);
    }

    private void setColumnSize(JTable tbl) {
        tbl.setRowHeight(25);
        for (int i = 0; i < tbl.getColumnCount(); i++) {
            String str = tbl.getColumnName(i);

            switch (str) {
                case "Наименование":
                    tbl.getColumnModel().getColumn(i).setMaxWidth(400);
                    tbl.getColumnModel().getColumn(i).setMinWidth(100);
                    break;
                case "Кол-во мест":
                    tbl.getColumnModel().getColumn(i).setMaxWidth(100);
                    tbl.getColumnModel().getColumn(i).setMinWidth(50);
                    break;
                case "Занято мест":
                    tbl.getColumnModel().getColumn(i).setMaxWidth(100);
                    tbl.getColumnModel().getColumn(i).setMinWidth(50);
                    break;
                case "Свободно мест":
                    tbl.getColumnModel().getColumn(i).setMaxWidth(100);
                    tbl.getColumnModel().getColumn(i).setMinWidth(50);
                    break;
                case "По районам":
                    tbl.getColumnModel().getColumn(i).setMaxWidth(100);
                    tbl.getColumnModel().getColumn(i).setMinWidth(50);
                    break;
            }
        }
    }
}

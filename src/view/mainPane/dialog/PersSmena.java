package view.mainPane.dialog;

import blogic.entity.SmenaDM;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by hammer on 19.07.2017.
 */
public class PersSmena extends JPanel {

    SmenaDM dm = null;
    int realRow;

    public PersSmena(int personId, boolean editStatus) {
        setLayout(null);
        dm = new SmenaDM(personId);
        setBounds(0,0,300,280);

        JLabel text = new JLabel("Посещения пансионата:");
        text.setBounds(10,2,160,30);
        add(text);

        ListButtons listButtons = new ListButtons(dm, editStatus);
        listButtons.setBounds(160,4,136,30);
        add(listButtons);

        JTable tbl = new JTable(dm);
        tbl.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getClickCount() == 1){
                    int row = tbl.rowAtPoint(e.getPoint());
                    if (row > -1) {
                        realRow = tbl.convertRowIndexToModel(row);
                        dm.row = realRow;
                        dm.selectedId = (int) tbl.getModel().getValueAt(realRow,0);
                    }
                }
            }
        });
        tbl.getColumnModel().getColumn(0).setMaxWidth(30);
        tbl.getColumnModel().getColumn(0).setMinWidth(30);
        tbl.getColumnModel().getColumn(1).setMaxWidth(50);
        tbl.getColumnModel().getColumn(1).setMinWidth(50);
        tbl.getColumnModel().getColumn(2).setMaxWidth(50);
        tbl.getColumnModel().getColumn(2).setMinWidth(50);
        tbl.getColumnModel().getColumn(3).setMaxWidth(170);
        tbl.getColumnModel().getColumn(3).setMinWidth(170);

        tbl.getTableHeader().setReorderingAllowed(false);
        tbl.setAutoCreateRowSorter(true);
        JScrollPane scr = new JScrollPane(tbl);
        scr.setBounds(2,34,296,244);
        add(scr);
    }
}

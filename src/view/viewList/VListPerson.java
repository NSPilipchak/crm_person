package view.viewList;

import blogic.entity.PersonDM;
import view.LineFind;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static properties.Strings.*;

/**
 * Created by hammer on 19.07.2017.
 */
public class VListPerson extends JDialog {

    PersonDM dm = null;
    public int realRow;

    public VListPerson() {
        setLayout(null);

        setBounds((SCREEN_SIZE.width - DG_WIDH) / 2, (SCREEN_SIZE.height - DG_HEIGH) / 2, DG_WIDH, DG_HEIGH);
        setTitle("Справочник: Персоны");


        dm = new PersonDM();

        JTable tbl = new JTable(dm);
        tbl.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getClickCount() == 2){
                    int row = tbl.rowAtPoint(e.getPoint());
                    if (row > -1) {
                        realRow = tbl.convertRowIndexToModel(row);
                        dm.row = realRow;
                    }
                    dm.update();
                }else if (e.getClickCount() == 1) {
                    int row = tbl.rowAtPoint(e.getPoint());
                    if (row > -1) {
                        realRow = tbl.convertRowIndexToModel(row);
                        dm.row = realRow;
                    }
                }
            }
        });
        tbl.getTableHeader().setReorderingAllowed(false);
        tbl.setAutoCreateRowSorter(true);
        JScrollPane scr = new JScrollPane(tbl);
        setLayout(new BorderLayout());
        add(scr, BorderLayout.CENTER);

        add(new LineFind(dm, tbl), BorderLayout.NORTH);
        add(new VListButtons(dm), BorderLayout.SOUTH);
    }
}

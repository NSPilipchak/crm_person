package view.viewList;

import blogic.entity.SmenaDM;
import view.LineFind;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static properties.Strings.*;

/**
 * Created by hammer on 19.07.2017.
 */
public class VListSmena extends JDialog {

    SmenaDM dm = null;
    public int realRow;

    public VListSmena() {
        setLayout(null);

        setBounds((SCREEN_SIZE.width - DG_WIDH) / 2, (SCREEN_SIZE.height - DG_HEIGH) / 2, DG_WIDH, DG_HEIGH);
        setTitle("Справочник: Смены");


        dm = new SmenaDM();

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

        tbl.getColumnModel().getColumn(0).setMaxWidth(50);
        tbl.getColumnModel().getColumn(0).setMinWidth(50);
        tbl.getColumnModel().getColumn(1).setMaxWidth(50);
        tbl.getColumnModel().getColumn(1).setMinWidth(50);
        tbl.getColumnModel().getColumn(2).setMaxWidth(100);
        tbl.getColumnModel().getColumn(2).setMinWidth(100);
        tbl.getColumnModel().getColumn(4).setMaxWidth(80);
        tbl.getColumnModel().getColumn(4).setMinWidth(80);

        tbl.getTableHeader().setReorderingAllowed(false);
        tbl.setAutoCreateRowSorter(true);
        JScrollPane scr = new JScrollPane(tbl);
        setLayout(new BorderLayout());
        add(scr, BorderLayout.CENTER);

        add(new LineFind(dm, tbl), BorderLayout.NORTH);
        add(new VListButtons(dm), BorderLayout.SOUTH);
    }
    private void configPane(){
        setLayout(null);
        Dimension sSize = Toolkit.getDefaultToolkit().getScreenSize();
        int w = 525;
        int h = 600;
        int x = ((sSize.width - w) / 2);
        int y = ((sSize.height - h) / 2);
        setBounds(x, y, w, h);
        setTitle("Справочник: Визиты");
    }
}

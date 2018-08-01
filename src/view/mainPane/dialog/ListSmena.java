package view.mainPane.dialog;

import blogic.entity.SmenaDM;
import view.LineFind;
import view.mainPane.baseStatus.TableRenderers;
import view.viewList.VListButtons;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.*;
import java.awt.font.TextAttribute;
import java.util.HashMap;
import java.util.Map;

import static properties.Strings.*;

/**
 * Created by hammer on 19.07.2017.
 */
public class ListSmena extends JDialog {

    SmenaDM dm = null;
    public int realRow;
    public String ret = "Cancel";
    JButton btnCancel = null;
    JButton btnOk = null;
    public int personId = 0;

    private PressBtnTrue aPressBtnTrue = new PressBtnTrue();
    private PressBtnFalse aPressBtnFalse = new PressBtnFalse();

    public ListSmena(int personId) {
        this.personId = personId;
        configPane();

        dm = new SmenaDM();

        JTable tbl = new JTable(dm);
        tbl.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = tbl.rowAtPoint(e.getPoint());
                    if (row > -1) {
                        realRow = tbl.convertRowIndexToModel(row) + 1;
                        dm.row = realRow;
                    }
                } else if (e.getClickCount() == 1) {
                    int row = tbl.rowAtPoint(e.getPoint());
                    if (row > -1) {
                        realRow = tbl.convertRowIndexToModel(row) + 1;
                        dm.row = realRow;
                    }
                }
            }
        });

        tbl.getColumnModel().getColumn(0).setMaxWidth(50);
        tbl.getColumnModel().getColumn(0).setMinWidth(50);
        tbl.getColumnModel().getColumn(1).setMaxWidth(50);
        tbl.getColumnModel().getColumn(1).setMinWidth(50);
        tbl.getColumnModel().getColumn(2).setMaxWidth(60);
        tbl.getColumnModel().getColumn(2).setMinWidth(40);
        tbl.getColumnModel().getColumn(4).setMaxWidth(80);
        tbl.getColumnModel().getColumn(4).setMinWidth(80);
        tbl.getColumnModel().getColumn(5).setMaxWidth(60);
        tbl.getColumnModel().getColumn(5).setMinWidth(40);

        tbl.getTableHeader().setReorderingAllowed(false);
        tbl.setAutoCreateRowSorter(true);

        TableRenderers tableRenderer = new TableRenderers();
        for (int i = 0; i < tbl.getColumnCount(); i++)
            tbl.getColumnModel().getColumn(i).setCellRenderer(tableRenderer);

        JScrollPane scr = new JScrollPane(tbl);
        setLayout(new BorderLayout());
        add(scr, BorderLayout.CENTER);

        add(new LineFind(dm, tbl), BorderLayout.NORTH);
        add(initButton(), BorderLayout.SOUTH);
    }

    private void configPane() {
        setLayout(null);
        Dimension sSize = Toolkit.getDefaultToolkit().getScreenSize();
        int w = 500;
        int h = 500;
        int x = ((sSize.width - w) / 2);
        int y = ((sSize.height - h) / 2);
        setBounds(x, y, w, h);

    }

    private JPanel initButton() {
        JPanel jPanelButton = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        btnOk = new JButton("Ok");
        jPanelButton.add(btnOk);
        btnOk.addActionListener(aPressBtnTrue);

        btnCancel = new JButton("Cancel");
        jPanelButton.add(btnCancel);
        btnCancel.addActionListener(aPressBtnFalse);
        return jPanelButton;
    }

    private class PressBtnTrue implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            ret = "Ok";
            setVisible(false);
        }
    }

    private class PressBtnFalse implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            ret = "Cancel";
            setVisible(false);
        }
    }

    class TableRenderers extends DefaultTableCellRenderer implements TableCellRenderer {
        TableRenderers() {
            super();
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            final Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            setHorizontalAlignment(SwingConstants.CENTER);
            return cell;
        }
    }
}

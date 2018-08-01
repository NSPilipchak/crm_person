package view.mainPane;

import blogic.entity.PersonDMwithPersonData;
import org.oxbow.swingbits.table.filter.TableRowFilterSupport;
import view.ContextList;
import view.LineFind;
import view.mainPane.baseStatus.BaseStatusPanel;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static view.MainFrame.statusBar;

public class PersPanel extends JPanel {
    PersonDMwithPersonData dm = null;
    public int realRow;
    private int first = 0;


    public PersPanel() {
        setLayout(null);
        NavigateCommand cmd = new NavigateCommand();
        dm = new PersonDMwithPersonData(cmd);
        dm.selectPage(first);
        cmd.setDm(dm);

        JTable tbl = new JTable(dm);
        tbl.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = tbl.rowAtPoint(e.getPoint());
                    if (row > -1) {
                        realRow = tbl.convertRowIndexToModel(row);
                        dm.row = realRow;
                    }
                    dm.update();
                } else if (e.getClickCount() == 1) {
                    int row = tbl.rowAtPoint(e.getPoint());
                    if (row > -1) {
                        realRow = tbl.convertRowIndexToModel(row);
                        dm.row = realRow;
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    JTable source = (JTable) e.getSource();
                    int row = source.rowAtPoint(e.getPoint());
                    if (!source.isRowSelected(row))
                        source.changeSelection(row, 0, false, false);
                    ContextList popup = new ContextList(dm);
                    popup.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });

        tbl.getTableHeader().setReorderingAllowed(false);

        setColumnSize(tbl);

        TableRowFilterSupport
                .forTable(tbl)
                .searchable(true)
                .apply();

        TableRenderers tableRenderer = new TableRenderers();
        for (int i = 0; i < tbl.getColumnCount(); i++)
            tbl.getColumnModel().getColumn(i).setCellRenderer(tableRenderer);

        JScrollPane scr = new JScrollPane(tbl);
        scr.setBorder(new TitledBorder("Отобранный список карточек"));

        setLayout(new BorderLayout());

        JPanel mainTable = new JPanel();
        mainTable.setLayout(new BorderLayout());
        mainTable.setBorder(new TitledBorder("Данные"));

        LineFind lf = new LineFind(dm, tbl);
        lf.jLabel.setText("Поиск в подгруженных данных:");

//        mainTable.add(lf, BorderLayout.SOUTH);
        mainTable.add(scr, BorderLayout.CENTER);
        mainTable.add(new FindLine(cmd), BorderLayout.NORTH);

        BaseStatusPanel baseStatusPanel = new BaseStatusPanel(cmd);
        baseStatusPanel.setPreferredSize(new Dimension(100, 250));
        mainTable.add(baseStatusPanel, BorderLayout.SOUTH);

        add(mainTable, BorderLayout.CENTER);

        add(new PersPanelButtons(dm), BorderLayout.WEST);

        add(new NavigateButtons(cmd), BorderLayout.EAST);
        dm.reRead();
        setBorder(BorderFactory.createEtchedBorder());
        statusBar.stopProgressBar();
    }

    private void setColumnSize(JTable tbl) {
        tbl.setRowHeight(25);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < tbl.getColumnCount(); i++) {
            String str = tbl.getColumnName(i);
            switch (str) {
                case "ИД":
                    tbl.getColumnModel().getColumn(i).setMaxWidth(80);
                    tbl.getColumnModel().getColumn(i).setMinWidth(10);
                    break;
                case "Идентификатор":
                    tbl.getColumnModel().getColumn(i).setMaxWidth(120);
                    tbl.getColumnModel().getColumn(i).setMinWidth(50);
                    break;
                case "Фамилия":
                    tbl.getColumnModel().getColumn(i).setMaxWidth(200);
                    tbl.getColumnModel().getColumn(i).setMinWidth(100);
                    break;
                case "Имя":
                    tbl.getColumnModel().getColumn(i).setMaxWidth(200);
                    tbl.getColumnModel().getColumn(i).setMinWidth(100);
                    break;
                case "Отчество":
                    tbl.getColumnModel().getColumn(i).setMaxWidth(200);
                    tbl.getColumnModel().getColumn(i).setMinWidth(100);
                    break;
                case "ИНН":
                    tbl.getColumnModel().getColumn(i).setMaxWidth(150);
                    tbl.getColumnModel().getColumn(i).setMinWidth(100);
                    break;
                case "Паспорт":
                    tbl.getColumnModel().getColumn(i).setMaxWidth(150);
                    tbl.getColumnModel().getColumn(i).setMinWidth(80);
                    break;
                case "Телефон":
                    tbl.getColumnModel().getColumn(i).setMaxWidth(200);
                    tbl.getColumnModel().getColumn(i).setMinWidth(120);
                    break;
                case "Status":
                    tbl.getColumnModel().getColumn(i).setMaxWidth(50);
                    tbl.getColumnModel().getColumn(i).setMinWidth(50);
                    break;
            }
        }
    }
}

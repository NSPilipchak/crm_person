package reports.exportReports;

import blogic.entity.Smena;
import blogic.entity.SmenaDM;
import dal.Factory;
import view.mainPane.dialog.ListSmena;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * Created by hammer on 17.08.2017.
 */
class OptionReport extends JDialog {

    public String ret = "Cancel";
    private JButton btnOk = null;
    private JButton btnCancel = null;
    private SmenaDM dmSelectedSmena;
    ArrayList arrStatus = new ArrayList();
    private int realRow;
    boolean boolBus;

    private PressBtnTrue aPressBtnTrue = new PressBtnTrue();
    private PressBtnFalse aPressBtnFalse = new PressBtnFalse();
    private PressBtnAddSmena aPressBtnAddSmena = new PressBtnAddSmena();
    private PressBtnDelSmena aPressBtnDelSmena = new PressBtnDelSmena();
    private PressCheckBox aPressCheckBox = new PressCheckBox();

    private JCheckBox rip, activ, blackList, deleted, vip, checkBus;

    OptionReport(SmenaDM dmSelectedSmena) {
        this.dmSelectedSmena = dmSelectedSmena;

        setLayout(new BorderLayout());
        Dimension sSize = Toolkit.getDefaultToolkit().getScreenSize();
        int w = 550;
        int h = 350;
        int x = ((sSize.width - w) / 2);
        int y = ((sSize.height - h) / 2);
        setBounds(x, y, w, h);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(null);
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.X_AXIS));
        centerPanel.add(initSmenaList());
        centerPanel.add(initStatuslist());
        add(centerPanel, BorderLayout.CENTER);

        JPanel buttons = new JPanel();
        btnOk = new JButton("Ок");
        buttons.add(btnOk);
        btnOk.addActionListener(aPressBtnTrue);
        btnCancel = new JButton("Вiдмiна");
        buttons.add(btnCancel);
        btnCancel.addActionListener(aPressBtnFalse);
        add(buttons, BorderLayout.SOUTH);
    }

    private class PressBtnTrue implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            boolBus = checkBus.isSelected();
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

    private JPanel initSmenaList() {

        JPanel smenaList = new JPanel();
        smenaList.setLayout(null);
        smenaList.setLayout(new BoxLayout(smenaList, BoxLayout.PAGE_AXIS));

        smenaList.setBorder(new TitledBorder("Перелiк змiн для звiту:"));

        JPanel listButtons = new JPanel();
        JButton addSmena = new JButton("Додати змiну");
        addSmena.addActionListener(aPressBtnAddSmena);
        listButtons.add(addSmena);
        JButton delSmena = new JButton("Видалити змiну");
        delSmena.addActionListener(aPressBtnDelSmena);
        listButtons.add(delSmena);
        smenaList.add(listButtons);

        JTable tbl = new JTable(dmSelectedSmena);
        tbl.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    int row = tbl.rowAtPoint(e.getPoint());
                    if (row > -1) {
                        realRow = tbl.convertRowIndexToModel(row);
                        dmSelectedSmena.row = realRow;
                        dmSelectedSmena.selectedId = (int) tbl.getModel().getValueAt(realRow, 0);
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
        tbl.getColumnModel().getColumn(3).setMaxWidth(160);
        tbl.getColumnModel().getColumn(3).setMinWidth(160);
        tbl.getColumnModel().getColumn(4).setMaxWidth(60);
        tbl.getColumnModel().getColumn(4).setMinWidth(60);

        tbl.getTableHeader().setReorderingAllowed(false);
        tbl.setAutoCreateRowSorter(true);

        JScrollPane scr = new JScrollPane(tbl);
        scr.setPreferredSize(new Dimension(350, 200));
        smenaList.add(scr);

        return smenaList;
    }

    private JPanel initStatuslist() {

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setLayout(new GridLayout(2, 1, 2, 2));

        JPanel chekBoxPanel = new JPanel();
        chekBoxPanel.setLayout(null);
        chekBoxPanel.setLayout(new BoxLayout(chekBoxPanel, BoxLayout.Y_AXIS));
        chekBoxPanel.setBorder(new TitledBorder("Статус вiдвiдувача:"));

        deleted = new JCheckBox("Видаленнi");
        deleted.setSelected(false);
        deleted.setActionCommand("0");
        deleted.addActionListener(aPressCheckBox);
        chekBoxPanel.add(deleted);

        activ = new JCheckBox("Активнi");
        activ.setSelected(false);
        activ.setActionCommand("1");
        activ.addActionListener(aPressCheckBox);
        chekBoxPanel.add(activ);

        blackList = new JCheckBox("Чорний список");
        blackList.setSelected(false);
        blackList.setActionCommand("2");
        blackList.addActionListener(aPressCheckBox);
        chekBoxPanel.add(blackList);

        vip = new JCheckBox("VIP");
        vip.setSelected(false);
        vip.setActionCommand("3");
        vip.addActionListener(aPressCheckBox);
        chekBoxPanel.add(vip);

        rip = new JCheckBox("Померлi");
        rip.setSelected(false);
        rip.setActionCommand("4");
        rip.addActionListener(aPressCheckBox);
        chekBoxPanel.add(rip);

        panel.add(chekBoxPanel);

        JPanel panelAdd = new JPanel();
        panelAdd.setBorder(new TitledBorder("Доп.налаштування:"));
        checkBus = new JCheckBox("Автобусные списки");
        panelAdd.add(checkBus);

        panel.add(panelAdd);

        return panel;
    }

    void setCheckBoxStatus(ArrayList arrStatus) {
        this.arrStatus = arrStatus;
        for (int i = 0; i < arrStatus.size(); i++) {
            switch ((int) arrStatus.get(i)) {
                case 0:
                    deleted.setSelected(true);
                    break;
                case 1:
                    activ.setSelected(true);
                    break;
                case 2:
                    blackList.setSelected(true);
                    break;
                case 3:
                    vip.setSelected(true);
                    break;
                case 4:
                    rip.setSelected(true);
                    break;
            }
        }
    }

    private void addStatus(int status) {
        if (arrStatus.size() == 0) {
            arrStatus.add(status);
        } else if (arrStatus.size() > 0) {
            boolean deleted = false;
            for (int i = 0; i < arrStatus.size(); i++) {
                if ((int) arrStatus.get(i) == status) {
                    arrStatus.remove(i);
                    deleted = true;
                    break;
                }
            }
            if (!deleted) {
                arrStatus.add(status);
            }
        }
    }

    private class PressBtnAddSmena implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
//            System.out.println("PressBtnAddSmena");
            ListSmena ls = new ListSmena(0);
            ls.setTitle("Доступнi змiни");
            ls.setModal(true);
            ls.setVisible(true);
            if (ls.ret.equals("Ok")) {
                Smena smena = Factory.getSmenaDAO().getSmena(ls.realRow);
//                System.out.println(smena);
                dmSelectedSmena.reportAddSemena(smena);
            }
        }
    }

    private class PressBtnDelSmena implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
//            System.out.println("PressBtnDelSmena" + realRow);
            if (realRow > -1) {
                dmSelectedSmena.reportDeleteSemena(realRow);
            }
        }
    }

    private class PressCheckBox implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String str = e.getActionCommand();
            switch (str) {
                case "0":
                    addStatus(0);
                    break;
                case "1":
                    addStatus(1);
                    break;
                case "2":
                    addStatus(2);
                    break;
                case "3":
                    addStatus(3);
                    break;
                case "4":
                    addStatus(4);
                    break;
            }
        }
    }
}

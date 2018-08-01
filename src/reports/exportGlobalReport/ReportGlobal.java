package reports.exportGlobalReport;

import blogic.entity.Person;
import blogic.entity.Smena;
import dal.Factory;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static properties.Properies.CROP_REPORT;
import static properties.Properies.VIEW_YEAR_IN_FILTER;
import static properties.Strings.ITEM_YEAR;

public class ReportGlobal extends JDialog {

    private JCheckBox rip, activ, blackList, deleted, vip;
    ArrayList arrStatus = new ArrayList();
    ArrayList arrSeason = new ArrayList();
    private ArrayList checkStatus = new ArrayList();
    private ArrayList checkSeason = new ArrayList();

    private PressCheckBox aPressCheckBox = new PressCheckBox();
    private ActionSeason actionSeason = new ActionSeason();

    public ReportGlobal() {
        setTitle("Глобальный отчет. Выгрузка всех данных из базы.");
        Dimension sSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (sSize.width / 2 - 220);
        int y = (sSize.height / 2 - 100);
        setBounds(x, y, 440, 200);
        setLayout(null);
        setLayout(new BorderLayout());

        JPanel txt = new JPanel();
        JLabel msg = new JLabel("Внимание! \n" +
                "Формирование отчета может занять значительное время.\n" +
                "");
        txt.add(msg);
        add(txt, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(null);
        centerPanel.setLayout(new GridLayout(1, 2));
        centerPanel.add(initSeasonList());
        centerPanel.add(initStatuslist());
        add(centerPanel, BorderLayout.CENTER);

        JPanel buttons = new JPanel();
        JButton btnOk = new JButton("Сформировать и сохранить");
        buttons.add(btnOk);
        btnOk.addActionListener(new PressBtnTrue());
        JButton btnCancel = new JButton("Отмена");
        buttons.add(btnCancel);
        btnCancel.addActionListener(new PressBtnFalse());
        add(buttons, BorderLayout.SOUTH);

    }

    private JPanel initSeasonList() {

        JPanel seasonList = new JPanel();
        seasonList.setLayout(null);
        seasonList.setLayout(new GridLayout(4, 2, 0, 0));
        seasonList.setBorder(new TitledBorder("Сезон:"));

        for (int i = 0; i < ITEM_YEAR.length; i++) {
            JCheckBox jCheckBox = new JCheckBox();
            jCheckBox.setText(ITEM_YEAR[i]);
            if (jCheckBox.getText().equals(ITEM_YEAR[VIEW_YEAR_IN_FILTER])) {
                jCheckBox.setSelected(true);
                arrSeason.add(i);
            }
            checkSeason.add(jCheckBox);
            jCheckBox.setActionCommand(i + "");
            jCheckBox.addActionListener(actionSeason);
            seasonList.add(jCheckBox);
        }
        return seasonList;
    }

    private JPanel initStatuslist() {

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setLayout(new GridLayout(5, 1, 0, 0));
        panel.setBorder(new TitledBorder("Статус персоны:"));

        deleted = new JCheckBox("Удаленные");
        deleted.setSelected(false);
        deleted.setActionCommand("0");
        deleted.addActionListener(aPressCheckBox);
        checkStatus.add(deleted);
        panel.add(deleted);

        activ = new JCheckBox("Активные");
        activ.setSelected(true);
        activ.setActionCommand("1");
        activ.addActionListener(aPressCheckBox);
        checkStatus.add(activ);
        panel.add(activ);
        addStatusOrSeason(1, arrStatus);

        blackList = new JCheckBox("Черный список");
        blackList.setSelected(false);
        blackList.setActionCommand("2");
        blackList.addActionListener(aPressCheckBox);
        checkStatus.add(blackList);
        panel.add(blackList);

        vip = new JCheckBox("VIP");
        vip.setSelected(false);
        vip.setActionCommand("3");
        vip.addActionListener(aPressCheckBox);
        checkStatus.add(vip);
        panel.add(vip);

        rip = new JCheckBox("Умершие");
        rip.setSelected(false);
        rip.setActionCommand("4");
        rip.addActionListener(aPressCheckBox);
        checkStatus.add(rip);
        panel.add(rip);

        return panel;
    }

    private ArrayList addStatusOrSeason(int status, ArrayList arrayList) {
        if (arrayList.size() == 0) {
            arrayList.add(status);
        } else if (arrayList.size() > 0) {
            boolean deleted = false;
            for (int i = 0; i < arrayList.size(); i++) {
                if ((int) arrayList.get(i) == status) {
                    if (arrayList.size() != 1)
                        arrayList.remove(i);
                    deleted = true;
                    break;
                }
            }
            if (!deleted) {
                arrayList.add(status);
            }
        }
        System.out.println(arrayList);
        return arrayList;
    }

    private class PressBtnTrue implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {

                ArrayList<Smena> smenaList = Factory.getSmenaDAO().getSmenaBySeason(arrSeason);
                if (CROP_REPORT) {
                    smenaList = cutSmenaList(smenaList);
                }

                ArrayList arrSmenaId = new ArrayList();
                for (Smena s : smenaList) {
                    arrSmenaId.add(s.getId());
                }
                ArrayList<Person> pp = Factory.getPersonDAO().findPersonBySmenaAndStatus(arrSmenaId, arrStatus, true);

                System.out.println(" pp = " + pp);
                System.out.println(" smenaList = " + smenaList);
                System.out.println(" arrStatus = " + arrStatus);
                System.out.println(" arrSeason = " + arrSeason);
                System.out.println(" arrSmenaId = " + arrSmenaId);

                JFileChooser fd = new JFileChooser();
                fd.resetChoosableFileFilters();
                fd.setAcceptAllFileFilterUsed(false);
                fd.setFileFilter(new FileNameExtensionFilter("XLS files", "xls"));
                WriteIntoExcel wir = new WriteIntoExcel();
                if (fd.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                    File file = new File(fd.getSelectedFile() + ".xls");
                    wir.writeIntoExcel(file, pp, smenaList, arrStatus);
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            setVisible(false);
        }
    }

    private class PressBtnFalse implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            setVisible(false);
        }
    }

    private class PressCheckBox implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            arrStatus = addStatusOrSeason(Integer.parseInt(e.getActionCommand()), arrStatus);

            for (Object check : checkStatus) {
                for (int x = 0; x < arrStatus.size(); x++) {
                    if (Integer.parseInt(((JCheckBox) check).getActionCommand()) == (int) arrStatus.get(x)) {
                        ((JCheckBox) check).setSelected(true);
                    }
                }
            }
        }
    }

    private class ActionSeason implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            arrSeason = addStatusOrSeason(Integer.parseInt(e.getActionCommand()), arrSeason);

            for (Object check : checkSeason) {
                for (int x = 0; x < arrSeason.size(); x++) {
                    if (Integer.parseInt(((JCheckBox) check).getActionCommand()) == (int) arrSeason.get(x)) {
                        ((JCheckBox) check).setSelected(true);
                    }
                }
            }
        }
    }

    private ArrayList<Smena> cutSmenaList(ArrayList<Smena> smenaList) {
        if (smenaList.size() < 2)
            return smenaList;

        ArrayList<Smena> smenaListRet = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            smenaListRet.add(smenaList.get(i));
        }
        return smenaListRet;
    }
}

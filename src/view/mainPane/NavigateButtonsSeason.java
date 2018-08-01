package view.mainPane;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import static properties.Properies.VIEW_YEAR_IN_FILTER;
import static properties.Strings.ITEM_YEAR;

class NavigateButtonsSeason extends JPanel {
    private NavigateCommand cmd;
    private String[] listSeason;
    private ArrayList checkSeason = new ArrayList();

    private ActionSeason actionSeason = new ActionSeason();


    NavigateButtonsSeason(NavigateCommand cmd) {
        this.cmd = cmd;
        setLayout(null);
        setLayout(new GridLayout(4, 2, 0, 0));
        setMinimumSize(new Dimension(180, 100));
        setMaximumSize(new Dimension(180, 100));
        setBorder(new TitledBorder("Сезон"));

        listSeason = ITEM_YEAR;
        cmd.arrSeason = new ArrayList();

        for (int i = 0; i < listSeason.length; i++) {
            JCheckBox jCheckBox = new JCheckBox();
            jCheckBox.setText(listSeason[i]);
            if (jCheckBox.getText().equals(ITEM_YEAR[VIEW_YEAR_IN_FILTER])) {
                jCheckBox.setSelected(true);
                cmd.arrSeason.add(i);
            }

            jCheckBox.setActionCommand(i + "");
            jCheckBox.addActionListener(actionSeason);
            add(jCheckBox);
            checkSeason.add(jCheckBox);
        }
    }


    private class ActionSeason implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int i = Integer.parseInt(e.getActionCommand());
            System.out.println("SEASON press checkBox " + i + " current array " + cmd.arrSeason);
            cmd.arrSeason = cmd.addStatusToArray(i, cmd.arrSeason);
            System.out.println("SEASON array " + cmd.arrSeason);
            cmd.updateSmena();
            for (Object check : checkSeason) {
                for (int x = 0; x < cmd.arrSeason.size(); x++) {
                    if (Integer.parseInt(((JCheckBox) check).getActionCommand()) == (int) cmd.arrSeason.get(x)) {
                        ((JCheckBox) check).setSelected(true);
                    }
                }
            }
        }
    }
}

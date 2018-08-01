package view.mainPane;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import static properties.Strings.STATUS_PERSON;

class NavigateButtonsStatus extends JPanel {
    private NavigateCommand cmd;
    private String[] listStatus;
    private ArrayList checkStatus = new ArrayList();

    private ActionStatus actionStatus = new ActionStatus();

    NavigateButtonsStatus(NavigateCommand cmd) {
        this.cmd = cmd;
        setLayout(null);
        setLayout(new GridLayout(5, 1, 0, 0));
        setBorder(new TitledBorder("Статус"));
        setMinimumSize(new Dimension(180, 130));
        setMaximumSize(new Dimension(180, 130));

        listStatus = STATUS_PERSON;
        cmd.arrStatus = new ArrayList();

        for (int i = 0; i < listStatus.length; i++) {
            JCheckBox jCheckBox = new JCheckBox();
            jCheckBox.setText(listStatus[i]);
            if (jCheckBox.getText().equals(STATUS_PERSON[1]) | jCheckBox.getText().equals(STATUS_PERSON[3])) {
                jCheckBox.setSelected(true);
                cmd.arrStatus.add(i);
            }
            jCheckBox.setActionCommand(i + "");
            jCheckBox.addActionListener(actionStatus);
            add(jCheckBox);
            checkStatus.add(jCheckBox);

            colorStatus();
        }
    }

    private void colorStatus() {
        for (Object check : checkStatus){
            String str = ((JCheckBox) check).getText();
            switch (str){
                case "Удаленная":
                    ((JCheckBox) check).setForeground(Color.RED);
                    break;
                case "Активная":
                    ((JCheckBox) check).setForeground(Color.BLACK);
                    break;
                case "Черный список":
                    ((JCheckBox) check).setForeground(Color.MAGENTA);
                    break;
                case "VIP персона":
                    ((JCheckBox) check).setForeground(Color.GREEN);
                    break;
                case "RIP":
                    ((JCheckBox) check).setForeground(Color.GRAY);
                    break;
            }
        }
    }

    private class ActionStatus implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int i = Integer.parseInt(e.getActionCommand());
            System.out.println("STATUS press checkBox " + i + " current array " + cmd.arrStatus);
            cmd.arrStatus = cmd.addStatusToArray(i, cmd.arrStatus);
            System.out.println("STATUS array " + cmd.arrStatus);

            for (Object check : checkStatus) {
                for (int x = 0; x < cmd.arrStatus.size(); x++) {
                    if (Integer.parseInt(((JCheckBox) check).getActionCommand()) == (int) cmd.arrStatus.get(x)) {
                        ((JCheckBox) check).setSelected(true);
                    }
                }
            }
        }
    }
}

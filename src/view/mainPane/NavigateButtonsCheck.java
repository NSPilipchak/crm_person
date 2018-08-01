package view.mainPane;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.TextAttribute;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class NavigateButtonsCheck extends JPanel {
    private NavigateCommand cmd;
    private String[] listVerification;
    private ArrayList checkVerification = new ArrayList();

    private ActionVerification actionVerification = new ActionVerification();

    NavigateButtonsCheck(NavigateCommand cmd) {
        this.cmd = cmd;
        setLayout(null);
        setLayout(new GridLayout(2, 1, 0, 0));
        setBorder(new TitledBorder("Документы:"));
        setMinimumSize(new Dimension(180, 130));
        setMaximumSize(new Dimension(180, 130));

        listVerification = new String[]{"Не проверенны", "Проверенны"};
        cmd.arrVerification = new ArrayList();

        for (int i = 0; i < listVerification.length; i++) {
            JCheckBox jCheckBox = new JCheckBox();
            jCheckBox.setText(listVerification[i]);
            jCheckBox.setSelected(true);
            cmd.arrVerification.add(i);

            jCheckBox.setActionCommand(i + "");
            jCheckBox.addActionListener(actionVerification);
            add(jCheckBox);
            checkVerification.add(jCheckBox);
            setFont();
        }
    }

    private void setFont() {
        Map verif0 = new HashMap();
        verif0.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        verif0.put(TextAttribute.FAMILY, "Dialog");
        verif0.put(TextAttribute.SIZE, 14);
        verif0.put(Font.BOLD, "");

        Map verif1 = new HashMap();
        verif1.put(TextAttribute.FAMILY, "Dialog");
        verif1.put(TextAttribute.SIZE, 12);
        verif1.put(Font.PLAIN, "");

        for (Object check : checkVerification) {
            String str = ((JCheckBox) check).getText();
            switch (str) {
                case "Проверенны":
                    ((JCheckBox) check).setFont(getFont().deriveFont(verif1));
                    break;
                case "Не проверенны":
                    ((JCheckBox) check).setFont(getFont().deriveFont(verif0));
                    break;
            }
        }
    }

    private class ActionVerification implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int i = Integer.parseInt(e.getActionCommand());
            System.out.println("STATUS press checkBox " + i + " current array " + cmd.arrVerification);
            cmd.arrVerification = cmd.addStatusToArray(i, cmd.arrVerification);
            System.out.println("STATUS array " + cmd.arrVerification);

            for (Object check : checkVerification) {
                for (int x = 0; x < cmd.arrVerification.size(); x++) {
                    if (Integer.parseInt(((JCheckBox) check).getActionCommand()) == (int) cmd.arrVerification.get(x)) {
                        ((JCheckBox) check).setSelected(true);
                    }
                }
            }
        }
    }
}

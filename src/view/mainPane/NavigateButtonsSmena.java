package view.mainPane;

import blogic.entity.Smena;
import dal.Factory;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import static properties.Properies.VIEW_SMENA_IN_FILTER;

class NavigateButtonsSmena extends JPanel {
    private NavigateCommand cmd;
    private ArrayList<Smena> listSmena;
    private ArrayList checkSmena = new ArrayList();

    private ActionSmena actionSmena = new ActionSmena();

    private JPanel panelCheckBox;
    private JScrollPane scr;

    private JCheckBox selection;
    private ActionSelection actionSelection = new ActionSelection();

    NavigateButtonsSmena(NavigateCommand cmd) {
        this.cmd = cmd;
        cmd.navigateButtonsSmena = this;

        setLayout(null);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(new TitledBorder("Смена"));
        setPreferredSize(new Dimension(180, 220));

        update();
        scr = new JScrollPane(panelCheckBox);
        scr.setBorder(null);
        scr.setMinimumSize(new Dimension(180, 150));
        scr.setMaximumSize(new Dimension(180, 200));
        add(scr);
    }

    public void update() {
        panelCheckBox = new JPanel();
        panelCheckBox.setLayout(new BoxLayout(panelCheckBox, BoxLayout.Y_AXIS));
        panelCheckBox = init();
        repaint();
        scr = new JScrollPane(panelCheckBox);
        scr.setBorder(null);
        scr.setMinimumSize(new Dimension(180, 150));
        scr.setMaximumSize(new Dimension(180, 150));
        add(scr);
        remove(0);
    }


    private JPanel init() {
        listSmena = Factory.getSmenaDAO().getSmenaBySeason(cmd.arrSeason);
        cmd.arrSmena = new ArrayList();

        selection = new JCheckBox();
        selection.setText("Выделить все");
        selection.setSelected(true);
        selection.setForeground(Color.BLUE);
        selection.addActionListener(actionSelection);
        panelCheckBox.add(selection);

        for (int i = 0; i < listSmena.size(); i++) {
            JCheckBox jCheckBox = new JCheckBox();

            if (VIEW_SMENA_IN_FILTER)
                jCheckBox.setText(listSmena.get(i).getNumber() + "нед. с " + listSmena.get(i).getDateStart());
            else
                jCheckBox.setText(listSmena.get(i).getName());

            jCheckBox.setActionCommand(listSmena.get(i).getId() + "");
            jCheckBox.setSelected(true);
            cmd.arrSmena.add(listSmena.get(i).getId());
            jCheckBox.addActionListener(actionSmena);
            panelCheckBox.add(jCheckBox);
            checkSmena.add(jCheckBox);
        }
        return panelCheckBox;
    }

    private void updateIsSelected() {
        for (Object check : checkSmena) {
            ((JCheckBox) check).setSelected(false);
            for (int x = 0; x < cmd.arrSmena.size(); x++) {
                if (Integer.parseInt(((JCheckBox) check).getActionCommand()) == (int) cmd.arrSmena.get(x)) {
                    ((JCheckBox) check).setSelected(true);
                }
            }
        }
    }

    private class ActionSmena implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int i = Integer.parseInt(e.getActionCommand());
            System.out.println("SMENA press checkBox " + i + " current array " + cmd.arrSmena);
            cmd.arrSmena = cmd.addStatusToArray(i, cmd.arrSmena);
            System.out.println("SMENA array " + cmd.arrSmena);
            updateIsSelected();
        }
    }

    private class ActionSelection implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (selection.isSelected()) {
                System.out.println("Выделить все current array " + cmd.arrSmena);

                for (Smena smena : listSmena)
                    cmd.arrSmena = cmd.addStatusToArray(smena.getId(), cmd.arrSmena);

                System.out.println("Выделить все new array " + cmd.arrSmena);
                updateIsSelected();

            } else {
                System.out.println("Снять выделения current array " + cmd.arrSmena);
                cmd.arrSmena = cmd.clearStatusArray(cmd.arrSmena);
                System.out.println("Снять выделения new array " + cmd.arrSmena);
                updateIsSelected();
            }
        }
    }
}

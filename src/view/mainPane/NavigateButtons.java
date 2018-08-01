package view.mainPane;

import javax.swing.*;
import javax.swing.border.TitledBorder;

class NavigateButtons extends JPanel {

    NavigateButtons(NavigateCommand cmd) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(new TitledBorder("Настройка списка"));
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        mainPanel.add(new NavigateButtonsPrevNext(cmd));
        mainPanel.add(new NavigateButtonsStatus(cmd));
        mainPanel.add(new NavigateButtonsSeason(cmd));
        mainPanel.add(new NavigateButtonsSmena(cmd));
        mainPanel.add(new NavigateButtonsCheck(cmd));

        JScrollPane scr = new JScrollPane(mainPanel);
        scr.setBorder(null);
        add(scr);
    }
}

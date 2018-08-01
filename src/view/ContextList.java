package view;

import blogic.entity.PersonDMwithPersonData;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by hammer on 16.08.2017.
 */
public class ContextList extends JPopupMenu {

    public ActionStatus actionStatus = new ActionStatus();
    PersonDMwithPersonData dm;
    public ContextList(PersonDMwithPersonData dm) {
        this.dm = dm;
        System.out.println("nenf");
        JMenu status = new JMenu("Статус");

        JMenuItem stausActiv = new JMenuItem("Активная персона");
        JMenuItem stausBlackList = new JMenuItem("Черный список");
        JMenuItem stausVIP = new JMenuItem("VIP персона");
        JMenuItem stausDeleted = new JMenuItem("Удаленный");
        JMenuItem stausRip = new JMenuItem("Померла");

        stausActiv.setActionCommand("1");
        stausBlackList.setActionCommand("2");
        stausVIP.setActionCommand("3");
        stausDeleted.setActionCommand("0");
        stausRip.setActionCommand("4");

        stausActiv.addActionListener(actionStatus);
        stausBlackList.addActionListener(actionStatus);
        stausVIP.addActionListener(actionStatus);
        stausDeleted.addActionListener(actionStatus);
        stausRip.addActionListener(actionStatus);

        status.add(stausActiv);
        status.add(stausBlackList);
        status.add(stausVIP);
        status.addSeparator();
        status.add(stausDeleted);
        status.add(stausRip);

        add(status);

    }
    public class ActionStatus implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int str = Integer.parseInt(e.getActionCommand());
            switch (str) {
                case 0:
                    System.out.println("Status 0");
                    dm.changeStatus(0);
                    break;
                case 1:
                    System.out.println("Status 1");
                    dm.changeStatus(1);
                    break;
                case 2:
                    System.out.println("Status 2");
                    dm.changeStatus(2);
                    break;
                case 3:
                    System.out.println("Status 3");
                    dm.changeStatus(3);
                    break;
                case 4:
                    System.out.println("Status 4");
                    dm.changeStatus(4);
                    break;
            }
        }
    }
}

package blogic;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by hammer on 16.08.2017.
 */
public class ActionStatus implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        int str = Integer.parseInt(e.getActionCommand());
        switch (str) {
            case 0:
                System.out.println("Status 0");
                break;
            case 1:
                System.out.println("Status 1");
                break;
            case 2:
                System.out.println("Status 2");
                break;
            case 3:
                System.out.println("Status 3");
                break;
        }
    }
}

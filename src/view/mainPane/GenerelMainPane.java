package view.mainPane;

import javax.swing.*;
import java.awt.*;

/**
 * Created by hammer on 02.08.2017.
 */
public class GenerelMainPane extends JPanel {
    public GenerelMainPane() {

        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        add(new PersPanel(), BorderLayout.CENTER);

    }
}

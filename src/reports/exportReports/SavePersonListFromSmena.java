package reports.exportReports;


import org.oxbow.swingbits.table.filter.TableRowFilterSupport;

import javax.swing.*;
import java.awt.*;

/**
 * Created by hammer on 16.08.2017.
 */
public class SavePersonListFromSmena extends JDialog {

    public JTable tbl;

    public SavePersonListFromSmena() {
        setTitle("РЕЄСТР УЧАСНИКІВ");

        EntityPersonSmena dm = new EntityPersonSmena();
        Dimension sSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(50, 50, sSize.width - 200, sSize.height - 200);

        setLayout(null);

        tbl = new JTable(dm);
        tbl.getTableHeader().setReorderingAllowed(false);

        TableRowFilterSupport
                .forTable(tbl)
                .searchable(true)
                .apply();

        JScrollPane scr = new JScrollPane(tbl);
        setLayout(new BorderLayout());
        add(scr, BorderLayout.CENTER);

        add(new Buttons(dm), BorderLayout.NORTH);
    }
}

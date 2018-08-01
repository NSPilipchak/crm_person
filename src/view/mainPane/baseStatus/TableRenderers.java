package view.mainPane.baseStatus;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hammer on 16.08.2017.
 */
public class TableRenderers extends DefaultTableCellRenderer implements TableCellRenderer {
    TableRenderers() {
        super();
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        final Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        Map font = new HashMap();
        font.put(TextAttribute.FAMILY, "Status");
        font.put(TextAttribute.SIZE, 17);
        font.put(Font.PLAIN, "");

        setHorizontalAlignment(SwingConstants.CENTER);
        setFont(getFont().deriveFont(font));
        return cell;
    }
}

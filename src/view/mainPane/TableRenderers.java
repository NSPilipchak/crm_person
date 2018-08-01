package view.mainPane;

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
    public TableRenderers() {
        super();
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        final Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        Map verif0 = new HashMap();
        verif0.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        verif0.put(TextAttribute.FAMILY, "Dialog");
        verif0.put(TextAttribute.SIZE, 17);
        verif0.put(Font.BOLD, "");

        Map verif1 = new HashMap();
        verif1.put(TextAttribute.FAMILY, "Dialog");
        verif1.put(TextAttribute.SIZE, 17);
        verif1.put(Font.PLAIN, "");

        int lastColumn = table.getColumnCount() - 1;
        String str = table.getValueAt(row, lastColumn) != null ? (String) table.getValueAt(row, lastColumn) : "1, 0";

        int status = Integer.parseInt(str.substring(0, 1));
        int verif = Integer.parseInt(str.substring(3, 4));
//        System.out.print("||==>" + str + "=>" + status + "/" + verif + "\n");
        switch (status) {
            case 0:
                setForeground(Color.RED); // Удаленный
                break;
            case 1:
                setForeground(Color.BLACK); // активный
                break;
            case 2:
                setForeground(Color.MAGENTA); // ч/с
                break;
            case 3:
                setForeground(Color.GREEN); // ВИП Статус
                break;
            case 4:
                setForeground(Color.GRAY); // RIP
                break;
        }

        switch (verif) {
            case 0:
                setFont(getFont().deriveFont(verif0));
                break;
            case 1:
                setFont(getFont().deriveFont(verif1));
                break;
        }
        return cell;
    }
}

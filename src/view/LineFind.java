package view;

import blogic.entity.FamilyDM;
import blogic.entity.PersonDM;
import blogic.entity.PersonDMwithPersonData;
import blogic.entity.SmenaDM;
import dal.Factory;

import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.regex.PatternSyntaxException;

import static properties.Properies.SEARCH_INTO_BASE;
import static view.MainFrame.statusBar;

/**
 * Created by hammer on 19.07.2017.
 */
public class LineFind extends JPanel {
    private JTextField findTextField;
    final TableRowSorter<?> sorter;
    private JButton btnFind;
    private KeyListenerESC keyESC = new KeyListenerESC();
    private PersonDMwithPersonData dm;
    public JLabel jLabel;

    public LineFind(SmenaDM dm, JTable tbl) {
        sorter = new TableRowSorter<>(dm);
        tbl.setRowSorter(sorter);
        init();
    }

    public LineFind(PersonDMwithPersonData dm, JTable tbl) {
        if (SEARCH_INTO_BASE)
            this.dm = dm;
        sorter = new TableRowSorter<>(dm);
        tbl.setRowSorter(sorter);
        init();
    }

    public LineFind(FamilyDM dm, JTable tbl) {
        sorter = new TableRowSorter<>(dm);
        tbl.setRowSorter(sorter);
        init();
    }

    public LineFind(PersonDM dm, JTable tbl) {
        sorter = new TableRowSorter<>(dm);
        tbl.setRowSorter(sorter);
        init();
    }

    private void init() {
        jLabel = new JLabel("Поиск: ");
        add(jLabel);

        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        findTextField = new JTextField();
        findTextField.addKeyListener(keyESC);
        add(findTextField);

        btnFind = new JButton("Поиск");
        btnFind.addKeyListener(keyESC);
        btnFind.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (dm == null) {
                    statusBar.setStatus("Поиск совпадений...");
                    findText();
                } else {
                    statusBar.setStatus("Поиск совпадений...");
                    findTextFromBase();
                }
                statusBar.setStatus("Поиск завершен.");
            }
        });
        add(btnFind);

        JButton btnFindClear = new JButton("Отмена");
        btnFindClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                findClear();
            }
        });
//        add(btnFindClear);
    }

    private void findTextFromBase() {
        String text = findTextField.getText();
        if (text.length() == 0) {
            dm.reRead();
        } else {
            dm.searchFromBase(text);
        }
    }

    private void findText() {
        String text = findTextField.getText();
        if (text.length() == 0) {
            sorter.setRowFilter(null);
        } else {
            try {
                sorter.setRowFilter(RowFilter.regexFilter(text));
            } catch (PatternSyntaxException pse) {
                System.err.println("Bad regex pattern");
            }
        }
    }

    private void findClear() {
        if (dm == null) {
            findTextField.setText(null);
            sorter.setRowFilter(null);
        } else {
            findTextField.setText(null);
            dm.reRead();
        }
    }

    private class KeyListenerESC implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                if (dm == null)
                    findText();
                else
                    findTextFromBase();
            }
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                findClear();
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }
}

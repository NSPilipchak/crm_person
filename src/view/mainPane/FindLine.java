package view.mainPane;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.font.TextAttribute;
import java.util.HashMap;
import java.util.Map;

public class FindLine extends JPanel {
    private JTextField findLName, findINN, findSPassport, findBdate;
    private JLabel txtLName, txtINN, txtSPassport, txtBdate;

    NavigateCommand cmd;
    private ActionFindFromBase actionFindFromBase = new ActionFindFromBase();
    private ActionCancel actionCancel = new ActionCancel();
    private KeyPressListener keyPress = new KeyPressListener();

    public FindLine(NavigateCommand cmd) {
        this.cmd = cmd;
        JButton btnFind, btnCancel;

        Map font = new HashMap();
        font.put(TextAttribute.FAMILY, "FindLine");
        font.put(TextAttribute.SIZE, 16);
        font.put(Font.PLAIN, "");

        JPanel main = new JPanel();
        main.setLayout(null);
        main.setBorder(new TitledBorder("Поиск по ключевым полям:"));
        main.setLayout(new BoxLayout(main, BoxLayout.X_AXIS));

        txtLName = new JLabel("Фамилия:");
        txtLName.setFont(getFont().deriveFont(font));
        findLName = new JTextField();
        findLName.setFont(getFont().deriveFont(font));
        findLName.setPreferredSize(new Dimension(200, 30));
        findLName.addKeyListener(keyPress);
        findLName.getDocument().addDocumentListener(documentListener);

        txtBdate = new JLabel("ДатаРождения:");
        txtBdate.setFont(getFont().deriveFont(font));
        findBdate = new JTextField();
        findBdate.setFont(getFont().deriveFont(font));
        findBdate.setPreferredSize(new Dimension(200, 30));
        findBdate.addKeyListener(keyPress);
        findBdate.getDocument().addDocumentListener(documentListener);

        txtINN = new JLabel("Код ИНН:");
        txtINN.setFont(getFont().deriveFont(font));
        findINN = new JTextField();
        findINN.setFont(getFont().deriveFont(font));
        findINN.setPreferredSize(new Dimension(200, 30));
        findINN.addKeyListener(keyPress);
        findINN.getDocument().addDocumentListener(documentListener);

        txtSPassport = new JLabel("Паспорт:");
        txtSPassport.setFont(getFont().deriveFont(font));
        findSPassport = new JTextField();
        findSPassport.setFont(getFont().deriveFont(font));
        findSPassport.setPreferredSize(new Dimension(200, 30));
        findSPassport.addKeyListener(keyPress);
        findSPassport.getDocument().addDocumentListener(documentListener);

        JPanel panelName = new JPanel();
        panelName.add(groupLableAndField(txtLName, findLName));

        JPanel panelBdate = new JPanel();
        panelBdate.add(groupLableAndField(txtBdate, findBdate));

        JPanel panelInn = new JPanel();
        panelInn.add(groupLableAndField(txtINN, findINN));

        JPanel panelSPassport = new JPanel();
        panelSPassport.add(groupLableAndField(txtSPassport, findSPassport));

        main.add(panelName);
        main.add(panelBdate);
        main.add(panelInn);
        main.add(panelSPassport);

        btnFind = new JButton("Поиск");
        btnFind.setFont(getFont().deriveFont(font));
        btnFind.setPreferredSize(new Dimension(120, 30));
        btnFind.setMinimumSize(new Dimension(120, 30));
        btnFind.setMaximumSize(new Dimension(150, 30));
        btnFind.addActionListener(actionFindFromBase);

        btnCancel = new JButton("Отмена");
        btnCancel.setFont(getFont().deriveFont(font));
        btnCancel.setPreferredSize(new Dimension(120, 30));
        btnCancel.setMinimumSize(new Dimension(120, 30));
        btnCancel.setMaximumSize(new Dimension(150, 30));
        btnCancel.addActionListener(actionCancel);

        JPanel panelButtons = new JPanel();
        panelButtons.setLayout(null);
        panelButtons.setLayout(new BoxLayout(panelButtons, BoxLayout.Y_AXIS));
        panelButtons.add(groupLableAndField(btnFind, btnCancel));
        main.add(panelButtons);

        add(main);
    }

    private JPanel groupLableAndField(Component first, Component second) {
        JPanel ret = new JPanel();
        ret.setLayout(new BoxLayout(ret, BoxLayout.Y_AXIS));
        ret.add(first);
        ret.add(second);
        return ret;
    }

    private void clearFind() {
        findLName.setText("");
        findINN.setText("");
        findSPassport.setText("");
        findBdate.setText("");
        cmd.setSearch(false, null, 0);
        cmd.dm.reRead();
        findLName.setEnabled(true);
        findINN.setEnabled(true);
        findBdate.setEnabled(true);
        findSPassport.setEnabled(true);

    }

    private void findTextFromBase() {
        String str = "";
        if (!findLName.getText().equals("")) {
            str = findLName.getText();
            cmd.setSearch(true, str, 1);
        } else if (!findINN.getText().equals("")) {
            str = findINN.getText();
            cmd.setSearch(true, str, 2);
        } else if (!findSPassport.getText().equals("")) {
            str = findSPassport.getText();
            cmd.setSearch(true, str, 3);
        } else if (!findBdate.getText().equals("")) {
            str = findBdate.getText();
            cmd.setSearch(true, str, 5);
        }
        cmd.dm.reRead();
    }

    private class ActionFindFromBase implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            findTextFromBase();
        }
    }

    private class ActionCancel implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            clearFind();
        }
    }

    private class KeyPressListener implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                findTextFromBase();
            }
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                clearFind();
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }

    private DocumentListener documentListener = new DocumentListener() {

        void check() {
            if (findLName.getText().length() >= 1) {
                findINN.setEnabled(false);
                findINN.setBackground(null);
                findSPassport.setEnabled(false);
                findSPassport.setBackground(null);
                findBdate.setEnabled(false);
                findBdate.setBackground(null);
            } else if (findINN.getText().length() >= 1) {
                findLName.setEnabled(false);
                findLName.setBackground(null);
                findSPassport.setEnabled(false);
                findSPassport.setBackground(null);
                findBdate.setEnabled(false);
                findBdate.setBackground(null);
            } else if (findSPassport.getText().length() >= 1) {
                findBdate.setEnabled(false);
                findBdate.setBackground(null);
                findINN.setEnabled(false);
                findINN.setBackground(null);
                findLName.setEnabled(false);
                findLName.setBackground(null);
            } else if (findBdate.getText().length() >= 1) {
                findINN.setEnabled(false);
                findINN.setBackground(null);
                findLName.setEnabled(false);
                findLName.setBackground(null);
                findSPassport.setEnabled(false);
                findSPassport.setBackground(null);
            } else if (findSPassport.getText().length() == 0
                    && findINN.getText().length() == 0
                    && findLName.getText().length() == 0
                    && findBdate.getText().length() == 0) {
                findINN.setEnabled(true);
                findINN.setBackground(Color.WHITE);
                findLName.setEnabled(true);
                findLName.setBackground(Color.WHITE);
                findSPassport.setEnabled(true);
                findSPassport.setBackground(Color.WHITE);
                findBdate.setEnabled(true);
                findBdate.setBackground(Color.WHITE);
            }
        }

        @Override
        public void insertUpdate(DocumentEvent e) {
            check();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            check();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            check();
        }
    };
}

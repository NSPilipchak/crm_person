package view.dialogs;

import blogic.entity.App;
import blogic.entity.Cognate;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

/**
 * Created by hammer on 17.07.2017.
 */
public class DgApp extends JDialog {

    public String ret = "Cancel";
    JButton btnOk = null;
    JButton btnCancel = null;
    JLabel id;
    JLabel key;
    JTextField value;

    private PressBtnTrue aPressBtnTrue = new PressBtnTrue();
    private PressBtnFalse aPressBtnFalse = new PressBtnFalse();

    public DgApp() throws ParseException {

        setLayout(new BorderLayout());
        Dimension sSize = Toolkit.getDefaultToolkit().getScreenSize();
        int w = 250;
        int h = 180;
        int x = ((sSize.width - w) / 2);
        int y = ((sSize.height - h) / 2);
        setBounds(x, y, w, h);


        add(txtField(), BorderLayout.CENTER);

        JPanel buttons = new JPanel();

        btnOk = new JButton("Ok");
        btnOk.setBounds(130, 330, 120, 25);
        btnOk.setEnabled(false);
        buttons.add(btnOk);
        btnOk.addActionListener(aPressBtnTrue);

        btnCancel = new JButton("Cancel");
        btnCancel.setBounds(260, 330, 150, 25);
        buttons.add(btnCancel);
        btnCancel.addActionListener(aPressBtnFalse);
        add(buttons, BorderLayout.SOUTH);

        value.getDocument().addDocumentListener(documentListener);
    }

    private JPanel txtField() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(5, 5, 5, 5));
        id = new JLabel();
        key = new JLabel();
        value = new JTextField();
        panel.add(id);
        panel.add(key);
        panel.add(value);
        return panel;
    }

    private DocumentListener documentListener = new DgDocumentListiner(this);

    public void setApp(App u) {
        id.setText("" + u.getId());
        key.setText("" + u.getKeyStr());
        value.setText("" + u.getValueStr());
    }

    public App getApp() {
        App u = new App();
        u.setId(Integer.parseInt(id.getText()));
        u.setKeyStr(key.getText());
        u.setValueStr(value.getText());
        return u;
    }

    private class PressBtnTrue implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            ret = "Ok";
            setVisible(false);
        }
    }

    private class PressBtnFalse implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            ret = "Cancel";
            setVisible(false);
        }
    }
}

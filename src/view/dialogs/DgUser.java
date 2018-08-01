package view.dialogs;

import blogic.entity.User;
import org.apache.commons.codec.digest.DigestUtils;

import javax.swing.*;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

/**
 * Created by hammer on 17.07.2017.
 */
public class DgUser extends JDialog {

    public String ret = "Cancel";
    JButton btnOk = null;
    JButton btnCancel = null;
    DgUserTextField textField;

    private PressBtnTrue aPressBtnTrue = new PressBtnTrue();
    private PressBtnFalse aPressBtnFalse = new PressBtnFalse();

    public DgUser() throws ParseException {

        setLayout(new BorderLayout());
        Dimension sSize = Toolkit.getDefaultToolkit().getScreenSize();
        int w = 450;
        int h = 450;
        int x = ((sSize.width - w) / 2);
        int y = ((sSize.height - h) / 2);
        setBounds(x, y, w, h);

        textField = new DgUserTextField(true);
        add(textField, BorderLayout.CENTER);

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

        textField.user.getDocument().addDocumentListener(documentListener);
        textField.tfPasswordFirst.getDocument().addDocumentListener(documentListener);
        textField.tfPasswordSecond.getDocument().addDocumentListener(documentListener);
        textField.pass.getDocument().addDocumentListener(documentListener);
    }

    private DocumentListener documentListener = new DgDocumentListiner(this);

    public void setUser(User u) {
        textField.id.setText("" + u.getId());
        textField.user.setText("" + u.getUser());
        textField.rolle.setSelectedIndex(u.getPermission());
        textField.status.setSelectedIndex(u.getStatus());
        textField.pass.setText("" + u.getPassword());
    }

    public User getUser() {
        User u = new User();
        u.setId(Integer.parseInt(textField.id.getText()));
        u.setUser(textField.user.getText());
        u.setPermission(textField.rolle.getSelectedIndex());
        u.setStatus(textField.status.getSelectedIndex());

        if (textField.tfPasswordSecond.getPassword().length != 0) {
            u.setPassword(
                    DigestUtils.md5Hex(
                            String.valueOf(
                                    textField.tfPasswordSecond.getPassword())));
        } else {
            u.setPassword(textField.pass.getText());
        }
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

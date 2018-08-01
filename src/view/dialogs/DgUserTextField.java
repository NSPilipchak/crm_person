package view.dialogs;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;

import static properties.Strings.USER_ROLES;
import static properties.Strings.USER_STATUS;

/**
 * Created by hammer on 17.07.2017.
 */
public class DgUserTextField extends JPanel {

    JLabel id;
    JTextField user;
    JComboBox rolle;
    JComboBox status;
    JPasswordField tfPasswordFirst;
    JPasswordField tfPasswordSecond;
    JTextField pass = new JTextField();

    void setColorTextField(JTextField comp) {
        comp.setBackground(Color.LIGHT_GRAY);
    }

    public DgUserTextField(boolean editStatus) throws ParseException {

        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JLabel idText = new JLabel("Ид:");
        id = new JLabel();
        id.setText("0");
        JLabel firstnameText = new JLabel("Имя:");
        user = new JTextField();
        user.setEditable(editStatus);

        JLabel passText1 = new JLabel("Введите пароль:");
        tfPasswordFirst = new JPasswordField(10);
        tfPasswordFirst.setEditable(editStatus);
        JLabel passText2 = new JLabel("Подтвердите пароль:");
        tfPasswordSecond = new JPasswordField(10);
        tfPasswordSecond.setEditable(editStatus);

        JLabel middlenameText = new JLabel("Роль:");
        rolle = new JComboBox(USER_ROLES);
        rolle.setEnabled(editStatus);

        JLabel statusText = new JLabel("Статус:");
        status = new JComboBox(USER_STATUS);
        status.setSelectedIndex(0);
        statusText.setEnabled(editStatus);

        setColorTextField(user);

        JPanel containerTextField = new JPanel();
        containerTextField.setLayout(new GridLayout(11, 4));

        containerTextField.add(idText);
        containerTextField.add(id);
        containerTextField.add(firstnameText);
        containerTextField.add(user);
        containerTextField.add(passText1);
        containerTextField.add(tfPasswordFirst);
        containerTextField.add(passText2);
        containerTextField.add(tfPasswordSecond);

        containerTextField.add(middlenameText);
        containerTextField.add(rolle);
        containerTextField.add(statusText);
        containerTextField.add(status);

        add(containerTextField, BorderLayout.CENTER);
    }
}

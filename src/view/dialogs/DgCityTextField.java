package view.dialogs;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;

/**
 * Created by hammer on 17.07.2017.
 */
public class DgCityTextField extends JPanel {

    JLabel id;
    JTextField city;
    JLabel status;

    void setColorTextField(JTextField comp) {
        comp.setBackground(Color.LIGHT_GRAY);
    }

    public DgCityTextField(boolean editStatus) throws ParseException {

        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JLabel idText = new JLabel("Ид:");
        id = new JLabel();
        id.setText("0");
        JLabel firstnameText = new JLabel("Наименование:");
        city = new JTextField();
        city.setEditable(editStatus);
        JLabel statusText = new JLabel("Статус:");
        status = new JLabel();
        status.setText("1");

        setColorTextField(city);

        JPanel containerTextField = new JPanel();
        containerTextField.setLayout(new GridLayout(11, 4));

        containerTextField.add(idText);
        containerTextField.add(id);
        containerTextField.add(firstnameText);
        containerTextField.add(city);
        containerTextField.add(statusText);
        containerTextField.add(status);

        add(containerTextField, BorderLayout.CENTER);
    }
}

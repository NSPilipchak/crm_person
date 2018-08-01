package view.dialogs;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;

/**
 * Created by hammer on 17.07.2017.
 */
public class DgDistrictTextField extends JPanel {

    JLabel id;
    JTextField district;
    JTextField quota;
    JLabel status;

    void setColorTextField(JTextField comp) {
        comp.setBackground(Color.LIGHT_GRAY);
    }

    public DgDistrictTextField(boolean editStatus) throws ParseException {

        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JLabel idText = new JLabel("Ид:");
        id = new JLabel();
        id.setText("0");
        JLabel firstnameText = new JLabel("Наименование:");
        district = new JTextField();
        district.setEditable(editStatus);
        JLabel quotaText = new JLabel("Квота:");
        quota = new JTextField();
        quota.setEditable(editStatus);
        JLabel statusText = new JLabel("Статус:");
        status = new JLabel();
        status.setText("1");

        setColorTextField(district);

        JPanel containerTextField = new JPanel();
        containerTextField.setLayout(new GridLayout(11, 4));

        containerTextField.add(idText);
        containerTextField.add(id);
        containerTextField.add(firstnameText);
        containerTextField.add(district);
        containerTextField.add(quotaText);
        containerTextField.add(quota);
        containerTextField.add(statusText);
        containerTextField.add(status);

        add(containerTextField, BorderLayout.CENTER);
    }
}

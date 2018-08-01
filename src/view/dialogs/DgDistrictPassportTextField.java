package view.dialogs;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;

/**
 * Created by hammer on 17.07.2017.
 */
public class DgDistrictPassportTextField extends JPanel {

    JLabel id;
    JTextField districtPassport;

    void setColorTextField(JTextField comp) {
        comp.setBackground(Color.LIGHT_GRAY);
    }

    public DgDistrictPassportTextField(boolean editStatus) throws ParseException {

        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JLabel idText = new JLabel("Ид:");
        id = new JLabel();
        id.setText("0");
        JLabel firstnameText = new JLabel("Наименование:");
        districtPassport = new JTextField();
        districtPassport.setEditable(editStatus);

        setColorTextField(districtPassport);

        JPanel containerTextField = new JPanel();
        containerTextField.setLayout(new GridLayout(11, 4));

        containerTextField.add(idText);
        containerTextField.add(id);
        containerTextField.add(firstnameText);
        containerTextField.add(districtPassport);

        add(containerTextField, BorderLayout.CENTER);
    }
}

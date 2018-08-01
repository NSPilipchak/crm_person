package view.dialogs;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;

/**
 * Created by hammer on 17.07.2017.
 */
public class DgCognateTextField extends JPanel {

    JLabel id;
    JTextField cognate;

    void setColorTextField(JTextField comp) {
        comp.setBackground(Color.LIGHT_GRAY);
    }

    public DgCognateTextField(boolean editStatus) throws ParseException {

        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JLabel idText = new JLabel("Ид:");
        id = new JLabel();
        id.setText("0");
        JLabel firstnameText = new JLabel("Наименование:");
        cognate = new JTextField();
        cognate.setEditable(editStatus);

        setColorTextField(cognate);

        JPanel containerTextField = new JPanel();
        containerTextField.setLayout(new GridLayout(11, 4));

        containerTextField.add(idText);
        containerTextField.add(id);
        containerTextField.add(firstnameText);
        containerTextField.add(cognate);

        add(containerTextField, BorderLayout.CENTER);
    }
}

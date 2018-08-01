package view.dialogs;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.text.ParseException;

import static properties.Strings.ITEM_YEAR;

/**
 * Created by hammer on 17.07.2017.
 */
public class DgSmenaTextField extends JPanel {

    JLabel id;
    JTextField name;
    JTextField number;
    JTextField places;
    JTextField dateStart;
    JTextField dateEnd;
    JComboBox year;
    JLabel status;
    JTextArea comment;
    JLabel busyPlaces;
    JLabel busyPlacesByDistrict;

    void setColorTextField(JTextField comp) {
        comp.setBackground(Color.LIGHT_GRAY);
    }

    public DgSmenaTextField(boolean editStatus) throws ParseException {

        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        MaskFormatter dateFormat = new MaskFormatter("##.##.####");
        MaskFormatter dateFormat2 = new MaskFormatter("##.##.####");
        MaskFormatter placesFormat = new MaskFormatter("***");
        placesFormat.setValidCharacters("0123456789");
        MaskFormatter numberFormat = new MaskFormatter("**");
        numberFormat.setValidCharacters("0123456789");

        JLabel idText = new JLabel("Ид:");
        id = new JLabel();
        id.setText("0");

        JLabel firstnameText = new JLabel("Название:");
        name = new JTextField();
        name.setEditable(editStatus);

        JLabel numberText = new JLabel("Номер недели:");
        number = new JTextField();//JFormattedTextField(numberFormat);
        number.setEditable(editStatus);


        JLabel middlenameText = new JLabel("Кол-во мест:");
        places = new JTextField();//new JFormattedTextField(placesFormat);
        places.setEditable(editStatus);

        JLabel birthText = new JLabel("Дата заезда:");
        dateStart = new JFormattedTextField(dateFormat);
        dateStart.setEditable(editStatus);

        JLabel mainphoneText = new JLabel("Дата выезда:");
        dateEnd = new JFormattedTextField(dateFormat2);
        dateEnd.setEditable(editStatus);

        JLabel addphoneText = new JLabel("Сезон:");
        year = new JComboBox(ITEM_YEAR);
        year.setEditable(editStatus);

        JLabel statusText = new JLabel("Статус:");
        status = new JLabel();
        status.setText("1");

        JLabel busyText = new JLabel("Занято мест:");
        busyPlaces = new JLabel();
        busyPlaces.setText("0");

        JLabel busyByDistrictText = new JLabel("Занято по районам:");
        busyPlacesByDistrict = new JLabel();
        busyPlacesByDistrict.setText("0");

        JLabel commentText = new JLabel("Комментарий:");
        comment = new JTextArea(4, 50);

        setColorTextField(name);
        setColorTextField(number);
        setColorTextField(places);
        setColorTextField(dateStart);
        setColorTextField(dateEnd);

        JPanel containerTextField = new JPanel();
        containerTextField.setLayout(new GridLayout(11, 4));

        containerTextField.add(idText);
        containerTextField.add(id);
        containerTextField.add(firstnameText);
        containerTextField.add(name);
        containerTextField.add(numberText);
        containerTextField.add(number);
        containerTextField.add(middlenameText);
        containerTextField.add(places);
        containerTextField.add(birthText);
        containerTextField.add(dateStart);
        containerTextField.add(mainphoneText);
        containerTextField.add(dateEnd);
        containerTextField.add(addphoneText);
        containerTextField.add(year);

        containerTextField.add(busyText);
        containerTextField.add(busyPlaces);

        containerTextField.add(busyByDistrictText);
        containerTextField.add(busyPlacesByDistrict);

        containerTextField.add(statusText);
        containerTextField.add(status);

        JPanel containerComment = new JPanel();
        containerComment.setLayout(new BorderLayout());
        containerComment.setBorder(BorderFactory.createEmptyBorder(5, 12, 5, 12));
        containerComment.add(commentText, BorderLayout.NORTH);
        comment.setLineWrap(true);
        comment.setWrapStyleWord(true);
        containerComment.add(new JScrollPane(comment), BorderLayout.CENTER);
        containerComment.setSize(300, 100);

        add(containerComment, BorderLayout.SOUTH);
        add(containerTextField, BorderLayout.CENTER);
    }
}

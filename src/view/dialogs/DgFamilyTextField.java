package view.dialogs;

import blogic.entity.Cognate;
import dal.Factory;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.text.ParseException;
import java.util.ArrayList;

/**
 * Created by hammer on 17.07.2017.
 */
public class DgFamilyTextField extends JPanel {

    JLabel id;
    JTextField name;
    JComboBox cognate;
    JFormattedTextField bDate;
    JFormattedTextField fPhone;
    JFormattedTextField lPhone;
    JTextField email;
    JLabel status;
    JCheckBox verificationDocum;
    JTextArea comment;

    void setColorTextField(JTextField comp) {
        comp.setBackground(Color.LIGHT_GRAY);
    }

    public DgFamilyTextField(boolean editStatus) throws ParseException {

        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        MaskFormatter dateFormat = new MaskFormatter("##.##.####");
        MaskFormatter phoneFormat = new MaskFormatter("+38(###)###-##-##");
        MaskFormatter phoneFormat2 = new MaskFormatter("+38(###)###-##-##");

        JLabel idText = new JLabel("Ид:");
        id = new JLabel();
        id.setText("0");
        JLabel firstnameText = new JLabel("ФИО:");
        name = new JTextField();
        name.setEditable(editStatus);

        JLabel middlenameText = new JLabel("Родственная связь:");
        cognate = new JComboBox(getCognates());
        cognate.setEditable(editStatus);

        JLabel birthText = new JLabel("День рождения:");
        bDate = new JFormattedTextField(dateFormat);
        bDate.setEditable(editStatus);

        JLabel mainphoneText = new JLabel("Основной телефон:");
        fPhone = new JFormattedTextField(phoneFormat);
        fPhone.setEditable(editStatus);

        JLabel addphoneText = new JLabel("Дополнительный телефон:");
        lPhone = new JFormattedTextField(phoneFormat2);
        lPhone.setEditable(editStatus);

        JLabel emailText = new JLabel("Е-мейл:");
        email = new JTextField();
        email.setEditable(editStatus);

        JLabel statusText = new JLabel("Статус:");
        status = new JLabel();
        status.setText("1");

        JLabel visitText = new JLabel("Визит:");
        verificationDocum = new JCheckBox();
        verificationDocum.setEnabled(editStatus);

        JLabel commentText = new JLabel("Комментарий:");
        comment = new JTextArea(4, 50);

        setColorTextField(name);
        setColorTextField(fPhone);

        JPanel containerTextField = new JPanel();
        containerTextField.setLayout(new GridLayout(11, 4));

        containerTextField.add(idText);
        containerTextField.add(id);
        containerTextField.add(firstnameText);
        containerTextField.add(name);
        containerTextField.add(middlenameText);
        containerTextField.add(cognate);
        containerTextField.add(birthText);
        containerTextField.add(bDate);
        containerTextField.add(emailText);
        containerTextField.add(email);

        containerTextField.add(mainphoneText);
        containerTextField.add(fPhone);
        containerTextField.add(addphoneText);
        containerTextField.add(lPhone);

        containerTextField.add(statusText);
        containerTextField.add(status);

        containerTextField.add(visitText);
        containerTextField.add(verificationDocum);


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

    private String[] getCognates(){
        ArrayList<Cognate> cognates = Factory.getLibraryDAO().getCognateList();
        String[] ret = new String[cognates.size()];
        for(int i = 0; i < cognates.size(); i++){
            ret[i] = cognates.get(i).getCognate();
        }
        return ret;
    }
}

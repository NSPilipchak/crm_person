package view.dialogs;

import blogic.entity.City;
import blogic.entity.District;
import dal.Factory;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.text.ParseException;
import java.util.ArrayList;

/**
 * Created by hammer on 17.07.2017.
 */
public class DgPersonTextField extends JPanel {

    JLabel idp;
    JLabel idpd;
    JLabel code;
    JTextField fName;
    JTextField mName;
    JTextField lName;
    JFormattedTextField bDate;
    JFormattedTextField fPhone;
    JFormattedTextField lPhone;
    JComboBox city;
    JComboBox district;
    JTextField street;
    JTextField house;
    JTextField corp;
    JTextField flat;
    JFormattedTextField sPassport;
    JFormattedTextField dPassport;
    JTextField aPassport;
    JFormattedTextField inn;
    JFormattedTextField pensionNum;
    JTextField email;
    JLabel status;
    JCheckBox verificationDocum;
    JTextArea comment;
    JTextField oldAdres;

    public DgPersonTextField(boolean editStatus) throws ParseException {

        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));


        MaskFormatter dateFormat = new MaskFormatter("##.##.####");
        MaskFormatter dateFormat2 = new MaskFormatter("##.##.####");
        MaskFormatter phoneFormat = new MaskFormatter("+38(###)###-##-##");
        MaskFormatter phoneFormat2 = new MaskFormatter("+38(###)###-##-##");
        MaskFormatter sPassportFormat = new MaskFormatter("UU ######");
        MaskFormatter pensionNumFormat = new MaskFormatter("######");
        MaskFormatter innFormat = new MaskFormatter("##########");

        JLabel idpText = new JLabel("Ид карточки:");
        idp = new JLabel();
        idp.setText("Не присвоен");
        JLabel idpdText = new JLabel("Ид данных:");
        idpd = new JLabel();
        idpd.setText("Не присвоен");
        JLabel codeText = new JLabel("Код:");
        code = new JLabel();
        code.setText("Не присвоен");
        JLabel firstnameText = new JLabel("Имя:");
        fName = new JTextField();
        fName.setEditable(editStatus);

        JLabel middlenameText = new JLabel("Отчество:");
        mName = new JTextField();
        mName.setEditable(editStatus);

        JLabel lastnameText = new JLabel("Фамилия:");
        lName = new JTextField();
        lName.setEditable(editStatus);

        JLabel birthText = new JLabel("День рождения:");
        bDate = new JFormattedTextField(dateFormat2);
        bDate.setEditable(editStatus);

        JLabel passportnumText = new JLabel("Серия паспорта:");
        sPassport = new JFormattedTextField(sPassportFormat);
        sPassport.setEditable(editStatus);

        JLabel passportdateText = new JLabel("Дата выдачи:");
        dPassport = new JFormattedTextField(dateFormat);
        dPassport.setEditable(editStatus);


        JLabel passportinfoText = new JLabel("Кем выдан:");
        aPassport = new JTextField();
        aPassport.setEditable(editStatus);

        JLabel innText = new JLabel("ИНН:");
        inn = new JFormattedTextField(innFormat);
        inn.setEditable(editStatus);

        JLabel pensionNumText = new JLabel("Пенсионное удостоверение:");
        pensionNum = new JFormattedTextField(pensionNumFormat);
        pensionNum.setEditable(editStatus);


        JLabel mainphoneText = new JLabel("Основной телефон:");
        fPhone = new JFormattedTextField(phoneFormat);
        fPhone.setEditable(editStatus);

        JLabel addphoneText = new JLabel("Дополнительный телефон:");
        lPhone = new JFormattedTextField(phoneFormat2);
        lPhone.setEditable(editStatus);

        JLabel adresText = new JLabel("Адрес облако:");
        oldAdres = new JTextField();
        oldAdres.setEditable(editStatus);

        JLabel cityText = new JLabel("Город:");
        city = new JComboBox(getCity());
        city.setEditable(editStatus);

        JLabel districtText = new JLabel("Район:");
        district = new JComboBox(getDistricts());
        district.setEnabled(editStatus);

        JLabel streetText = new JLabel("Улица:");
        street = new JTextField();
        street.setEditable(editStatus);

        JLabel houseText = new JLabel("Дом:");
        house = new JTextField();
        house.setEditable(editStatus);

        JLabel corpText = new JLabel("Корпус:");
        corp = new JTextField();
        corp.setEditable(editStatus);

        JLabel flatText = new JLabel("Квартира:");
        flat = new JTextField();
        flat.setEditable(editStatus);

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

        JPanel containerTextField = new JPanel();
        containerTextField.setLayout(new GridLayout(12, 4));

        containerTextField.add(idpText);
        containerTextField.add(idp);
        containerTextField.add(idpdText);
        containerTextField.add(idpd);
        containerTextField.add(codeText);
        containerTextField.add(code);
        containerTextField.add(firstnameText);
        containerTextField.add(fName);
        containerTextField.add(middlenameText);
        containerTextField.add(mName);
        containerTextField.add(lastnameText);
        containerTextField.add(lName);
        containerTextField.add(birthText);
        containerTextField.add(bDate);
        containerTextField.add(passportnumText);
        containerTextField.add(sPassport);
        containerTextField.add(passportinfoText);
        containerTextField.add(aPassport);
        containerTextField.add(passportdateText);
        containerTextField.add(dPassport);

        containerTextField.add(innText);
        containerTextField.add(inn);
        containerTextField.add(pensionNumText);
        containerTextField.add(pensionNum);

        containerTextField.add(emailText);
        containerTextField.add(email);

        containerTextField.add(mainphoneText);
        containerTextField.add(fPhone);
        containerTextField.add(addphoneText);
        containerTextField.add(lPhone);
        containerTextField.add(adresText);
        containerTextField.add(oldAdres);
        containerTextField.add(cityText);
        containerTextField.add(city);
        containerTextField.add(districtText);
        containerTextField.add(district);
        containerTextField.add(streetText);
        containerTextField.add(street);
        containerTextField.add(houseText);
        containerTextField.add(house);
        containerTextField.add(corpText);
        containerTextField.add(corp);
        containerTextField.add(flatText);
        containerTextField.add(flat);


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

    private String[] getDistricts() {
        ArrayList<District> districts = Factory.getLibraryDAO().getDistrictList();
        String[] ret = new String[districts.size()];
        for (int i = 0; i < districts.size(); i++) {
            ret[i] = districts.get(i).getDistrict();
        }
        return ret;
    }
    private String[] getCity(){
        ArrayList<City> citiess = Factory.getLibraryDAO().getCityList();
        String[] ret = new String[citiess.size()];
        for(int i = 0; i < citiess.size(); i++){
            ret[i] = citiess.get(i).getCity();
        }
        return ret;
    }
}

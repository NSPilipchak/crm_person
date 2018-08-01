package view.mainPane.dialog;

import blogic.entity.City;
import blogic.entity.District;
import blogic.entity.DistrictPassport;
import dal.Factory;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.text.ParseException;
import java.util.ArrayList;

/**
 * Created by hammer on 17.07.2017.
 */
public class DgGeneralTextFieldNull extends JPanel {

    JLabel idp;
    JLabel idpd;
    JLabel code;
    JLabel createInfo;
    JLabel createUser;
    JTextField fName;
    JTextField mName;
    JTextField lName;
    JTextField bDate;
    JTextField fPhone;
    JTextField lPhone;
    JTextField homePhone;
    JTextField dPassport;
//    JFormattedTextField bDate;
//    JFormattedTextField fPhone;
//    JFormattedTextField lPhone;
//    JFormattedTextField homePhone;
//    JFormattedTextField dPassport;

    JComboBox city;
    JComboBox district;
    JTextField street;
    JTextField house;
    JTextField corp;
    JTextField flat;
    JTextField sPassport;
//    JTextField aPassport;
    JComboBox aPassport;
    JTextField inn;
    JTextField pensionNum;
    JTextField email;
    JLabel status;
    JCheckBox verificationDocum;
    JTextArea comment;
    JTextField oldAddres;

    private boolean save = false;

    private boolean editStatus;

    public DgGeneralTextFieldNull(boolean editStatus) throws ParseException {
        this.editStatus = editStatus;

        JPanel rootPanel = new JPanel();
        rootPanel.setLayout(new BoxLayout(rootPanel, BoxLayout.Y_AXIS));
        rootPanel.setPreferredSize(new Dimension(520, 600));

        /**
         * Добавляем отображение ИДшников
         */
        rootPanel.add(panelId());

        /**
         * Добавляем отображение ФИО, Даты рождения
         */
        rootPanel.add(panelName());

        /**
         * Добавляем отображение Пасспортных данных, ИНН, Пенсионное удостоверение
         */
        rootPanel.add(panelPassport());

        /**
         * Добавляем отображение Телефона, Емейла
         */
        rootPanel.add(panelPhone());

        /**
         * Добавляем отображение Адресса
         */
        rootPanel.add(panelAdress());


        /**
         * панель комментария и статуса объединяем в одну панель
         */
        rootPanel.add(groupLableAndField(panelСomment(), 400, panelStatus(), 90, 90));

        /**
         * добавляем поле комментария на форму
         */
//        rootPanel.add(panelСomment());

        /**
         * добавляем поле статусов
         */
//        rootPanel.add(panelStatus());

        /**
         * добавляем основную панель
         */
        add(rootPanel);
    }

    private JPanel groupLableAndField(Component first, Component second) {
        JPanel ret = new JPanel();
        ret.add(first);
        ret.add(second);
        return ret;
    }

    private JPanel groupLableAndField(Component first, int firstWidth, Component second, int secondWidth, int height) {
        JPanel ret = new JPanel();
        first.setPreferredSize(new Dimension(firstWidth, height));
        ret.add(first);

        second.setPreferredSize(new Dimension(secondWidth, height));
        ret.add(second);
        return ret;
    }

    void setColorTextField(JTextField comp) {
        comp.setBackground(Color.LIGHT_GRAY);
    }

    private JPanel panelName() {
        JLabel firstnameText = new JLabel("Имя:");
        fName = new JTextField();
        fName.setEditable(editStatus);
        setColorTextField(fName);

        JLabel middlenameText = new JLabel("Отчество:");
        mName = new JTextField();
        mName.setEditable(editStatus);
        setColorTextField(mName);

        JLabel lastnameText = new JLabel("Фамилия:");
        lName = new JTextField();
        lName.setEditable(editStatus);
        setColorTextField(lName);

        JLabel birthText = new JLabel("День рождения:");
        try {
            bDate = new JFormattedTextField(new MaskFormatter("##.##.####"));
        } catch (ParseException e) {
            System.out.println("Unable to JFormattedTextField = bDate");
            e.printStackTrace();
        }
        bDate.setEditable(editStatus);
        setColorTextField(bDate);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new FlowLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(groupLableAndField(lastnameText, 80, lName, 200, 20));
        panel.add(Box.createRigidArea(new Dimension(5, 0)));
        panel.add(groupLableAndField(firstnameText, 80, fName, 200, 20));
        panel.add(Box.createRigidArea(new Dimension(5, 0)));
        panel.add(groupLableAndField(middlenameText, 80, mName, 200, 20));

        JPanel panel2 = new JPanel();
        panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));
        panel2.add(groupLableAndField(birthText, 100, bDate, 80, 20));

        mainPanel.setBorder(BorderFactory.createEtchedBorder());
        mainPanel.add(panel);
        mainPanel.add(panel2);

        return mainPanel;
    }

    private JPanel panelPassport() {
        JLabel passportnumText = new JLabel("Серия паспорта:");
        sPassport = new JTextField();
        sPassport.setEditable(editStatus);
        setColorTextField(sPassport);

        JLabel passportdateText = new JLabel("Дата выдачи:");
        try {
            dPassport = new JFormattedTextField(new MaskFormatter("##.##.####"));
        } catch (ParseException e) {
            System.out.println("Unable to JFormattedTextField = dPassport");
            e.printStackTrace();
        }
        dPassport.setEditable(editStatus);
        setColorTextField(dPassport);

        JLabel passportinfoText = new JLabel("Кем выдан:");
        aPassport = new JComboBox(getDistrictPassport());
        aPassport.setEditable(editStatus);
//        setColorTextField(aPassport);

        JLabel innText = new JLabel("ИНН:");
        inn = new JTextField();
        inn.setEditable(editStatus);
        setColorTextField(inn);

        JLabel pensionNumText = new JLabel("Пенс.удостоверение:");
        pensionNum = new JTextField();
        pensionNum.setEditable(editStatus);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.add(groupLableAndField(passportnumText, 100, sPassport, 100, 20));
        panel.add(groupLableAndField(passportdateText, 140, dPassport, 100, 20));

        JPanel panel2 = new JPanel();
        panel2.setLayout(new FlowLayout(FlowLayout.CENTER));
        panel2.add(groupLableAndField(passportinfoText, 100, aPassport, 360, 20));

        JPanel panel3 = new JPanel();
        panel3.setLayout(new BoxLayout(panel3, BoxLayout.X_AXIS));
        panel3.add(groupLableAndField(innText, 100, inn, 100, 20));
        panel3.add(groupLableAndField(pensionNumText, 140, pensionNum, 100, 20));

        mainPanel.setBorder(BorderFactory.createEtchedBorder());
        mainPanel.add(panel);
        mainPanel.add(panel2);
        mainPanel.add(panel3);

        return mainPanel;
    }

    private JPanel panelStatus() {
        JLabel statusText = new JLabel("Статус:");
        status = new JLabel();
        status.setText("1");

        JLabel visitText = new JLabel("Провер.:");
        verificationDocum = new JCheckBox();
        verificationDocum.setEnabled(editStatus);

        JPanel panel = new JPanel();
        panel.setBounds(10, 10, 520, 35);
        panel.setBorder(BorderFactory.createEtchedBorder());

        panel.add(groupLableAndField(statusText, status));
        panel.add(groupLableAndField(visitText, verificationDocum));

        return panel;
    }

    private JPanel panelPhone() {
        JLabel mainphoneText = new JLabel("Основной тел.:");
        try {
            fPhone = new JFormattedTextField(new MaskFormatter("+38(###)###-##-##"));
        } catch (ParseException e) {
            System.out.println("Unable to JFormattedTextField = fPhone");
            e.printStackTrace();
        }
        fPhone.setEditable(editStatus);
        setColorTextField(fPhone);

        JLabel addphoneText = new JLabel("Доп. тел.:");
        try {
            lPhone = new JFormattedTextField(new MaskFormatter("+##(###')###'-##'-##"));
        } catch (ParseException e) {
            System.out.println("Unable to JFormattedTextField = lPhone");
            e.printStackTrace();
        }
        lPhone.setEditable(editStatus);

        JLabel homephoneText = new JLabel("Домашн. тел.:");
        try {
            homePhone = new JFormattedTextField(new MaskFormatter("+38(###')###'-##'-##"));
        } catch (ParseException e) {
            System.out.println("Unable to JFormattedTextField = lPhone");
            e.printStackTrace();
        }
        homePhone.setEditable(editStatus);

        JLabel emailText = new JLabel("Е-мейл:");
        email = new JTextField();
        email.setEditable(editStatus);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new FlowLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(groupLableAndField(mainphoneText, 100, fPhone, 120, 20));
        panel.add(Box.createRigidArea(new Dimension(5, 0)));
        panel.add(groupLableAndField(addphoneText, 100, lPhone, 120, 20));

        JPanel panel2 = new JPanel();
        panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));
        panel2.add(groupLableAndField(emailText, 100, email, 120, 20));
        panel2.add(Box.createRigidArea(new Dimension(5, 0)));
        panel2.add(groupLableAndField(homephoneText, 100, homePhone, 120, 20));

        mainPanel.setBorder(BorderFactory.createEtchedBorder());
        mainPanel.add(panel);
        mainPanel.add(panel2);

        return mainPanel;
    }

    private JPanel panelAdress() {
        JLabel cityText = new JLabel("Город:");
        city = new JComboBox(getCity());
        city.setEnabled(editStatus);

        JLabel districtText = new JLabel("Район:");
        district = new JComboBox(getDistricts());
        district.setEnabled(editStatus);

        JLabel streetText = new JLabel("Улица:");
        street = new JTextField();
        street.setEditable(editStatus);
        setColorTextField(street);

        JLabel houseText = new JLabel("Дом:");
        house = new JTextField();
        house.setEditable(editStatus);
        setColorTextField(house);

        JLabel corpText = new JLabel("Корпус:");
        corp = new JTextField();
        corp.setEditable(editStatus);

        JLabel flatText = new JLabel("Квартира:");
        flat = new JTextField();
        flat.setEditable(editStatus);

        JLabel adresText = new JLabel("Адрес из облака:");
        oldAddres = new JTextField();
        oldAddres.setEditable(false);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JPanel panelFirst = new JPanel();
        panelFirst.setLayout(new FlowLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(groupLableAndField(cityText, 80, city, 150, 20));
        panel.add(groupLableAndField(districtText, 80, district, 150, 20));
        panel.add(groupLableAndField(streetText, 80, street, 150, 20));

        JPanel panel2 = new JPanel();
        panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));
        panel2.add(groupLableAndField(houseText, 80, house, 80, 20));
        panel2.add(groupLableAndField(corpText, 80, corp, 80, 20));
        panel2.add(groupLableAndField(flatText, 80, flat, 80, 20));

        panelFirst.add(panel);
        panelFirst.add(panel2);

        JPanel panelSecond = new JPanel();
        panelSecond.setLayout(new FlowLayout());
        panelSecond.add(groupLableAndField(adresText, 120, oldAddres, 330, 20));

        mainPanel.setBorder(BorderFactory.createEtchedBorder());
        mainPanel.add(panelFirst);
        mainPanel.add(panelSecond);
        return mainPanel;
    }

    private Container panelId() {
        JLabel idpText = new JLabel("Ид карточки:");
        idp = new JLabel();
        idp.setText("Не присвоен");
        JLabel idpdText = new JLabel("Ид данных:");
        idpd = new JLabel();
        idpd.setText("Не присвоен");
        JLabel codeText = new JLabel("Идентификатор:");
        code = new JLabel();
        code.setText("Не присвоен");
        JLabel createInfoText = new JLabel("Создана:");
        createInfo = new JLabel();
        createInfo.setText("Не присвоен");
        JLabel createUserText = new JLabel("Автор:");
        createUser = new JLabel();
        createUser.setText("Не присвоен");

        JPanel codePane = new JPanel();
        codePane.setBounds(10, 10, 520, 35);
        codePane.setBorder(BorderFactory.createEtchedBorder());
        codePane.add(groupLableAndField(idpText, idp));
//        codePane.add(groupLableAndField(idpdText, idpd));
        codePane.add(groupLableAndField(codeText, code));
        codePane.add(groupLableAndField(createInfoText, createInfo));
//        codePane.add(groupLableAndField(createUserText, createUser));
        add(codePane);

        return codePane;
    }

    private JPanel panelСomment() {
        JPanel containerComment = new JPanel();
        JLabel commentText = new JLabel("Комментарий:");
        containerComment.setPreferredSize(new Dimension(400, 100));
        containerComment.setLayout(new BorderLayout());
        containerComment.add(commentText, BorderLayout.NORTH);
        comment = new JTextArea(4, 50);
        comment.setLineWrap(true);
        comment.setWrapStyleWord(true);
        containerComment.add(new JScrollPane(comment), BorderLayout.CENTER);
        containerComment.setBorder(BorderFactory.createEtchedBorder());
        return containerComment;
    }
    private String[] getDistricts(){
        ArrayList<District> districts = Factory.getLibraryDAO().getDistrictList();
        String[] ret = new String[districts.size()];
        for(int i = 0; i < districts.size(); i++){
            ret[i] = districts.get(i).getDistrict();
        }
        return ret;
    }

    private String[] getDistrictPassport(){
        ArrayList<DistrictPassport> districtPassports = Factory.getLibraryDAO().getDistrictPassportList();
        String[] ret = new String[districtPassports.size()];
        for(int i = 0; i < districtPassports.size(); i++){
            ret[i] = districtPassports.get(i).getDistrictPassport();
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

package view.mainPane.dialog;

import blogic.entity.Person;
import blogic.entity.PersonData;
import dal.Factory;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static properties.Strings.WORK_USER_NAME;

/**
 * Created by hammer on 17.07.2017.
 */
public class DgGeneral extends JDialog {

    public String ret = "Cancel";
    JButton btnOk = null;
    JButton btnCancel = null;
    JButton btnApply = null;
    PersFamily familyList = null;
    PersSmena smenaList = null;
    DgGeneralTextFieldNull textField;
    private boolean editStatus = true;
    int updateForm = 0; // отвечает за отслеживание изменений в форме и необходимость перезаписи карточки в базу
    boolean createNewPerson = true;

    private int w = 900; // ширина окна
    private int h = 700; // высота окна

    private PressBtnTrue aPressBtnTrue = new PressBtnTrue();
    private PressBtnFalse aPressBtnFalse = new PressBtnFalse();
    private PressBtnApply aPressBtnApply = new PressBtnApply();
    private PressKeyEsc aPressKeyEsc = new PressKeyEsc();
    private UpdateNotTxtData documentListenerMy = new UpdateNotTxtData();

    private void initConfig() {
        setLayout(null);
        Dimension sSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = ((sSize.width - w) / 2);
        int y = (int) ((sSize.height - h) / 2 * 0.5);
        setBounds(x, y, w, h);
        setResizable(false);
    }

    private void setDocumentListener() {
        textField.fName.getDocument().addDocumentListener(documentListener);
        textField.mName.getDocument().addDocumentListener(documentListener);
        textField.lName.getDocument().addDocumentListener(documentListener);
        textField.bDate.getDocument().addDocumentListener(documentListener);
        textField.sPassport.getDocument().addDocumentListener(documentListener);
        textField.dPassport.getDocument().addDocumentListener(documentListener);
//        textField.aPassport.getDocument().addDocumentListener(documentListener);
        textField.inn.getDocument().addDocumentListener(documentListener);
        textField.pensionNum.getDocument().addDocumentListener(documentListener);
        textField.fPhone.getDocument().addDocumentListener(documentListener);
        textField.street.getDocument().addDocumentListener(documentListener);
        textField.house.getDocument().addDocumentListener(documentListener);
        textField.lPhone.getDocument().addDocumentListener(documentListener);
        textField.comment.getDocument().addDocumentListener(documentListener);
        textField.corp.getDocument().addDocumentListener(documentListener);
        textField.flat.getDocument().addDocumentListener(documentListener);
        textField.email.getDocument().addDocumentListener(documentListener);
        textField.homePhone.getDocument().addDocumentListener(documentListener);
        textField.verificationDocum.addActionListener(documentListenerMy);
        textField.city.addActionListener(documentListenerMy);
        textField.district.addActionListener(documentListenerMy);
        textField.aPassport.addActionListener(documentListenerMy);
    }

    private void setaPressKeyEsc() {
        textField.fName.addKeyListener(aPressKeyEsc);
        textField.mName.addKeyListener(aPressKeyEsc);
        textField.lName.addKeyListener(aPressKeyEsc);
        textField.bDate.addKeyListener(aPressKeyEsc);
        textField.sPassport.addKeyListener(aPressKeyEsc);
        textField.dPassport.addKeyListener(aPressKeyEsc);
        textField.aPassport.addKeyListener(aPressKeyEsc);
        textField.inn.addKeyListener(aPressKeyEsc);
        textField.pensionNum.addKeyListener(aPressKeyEsc);
        textField.fPhone.addKeyListener(aPressKeyEsc);
        textField.street.addKeyListener(aPressKeyEsc);
        textField.house.addKeyListener(aPressKeyEsc);
        textField.lPhone.addKeyListener(aPressKeyEsc);
        textField.homePhone.addKeyListener(aPressKeyEsc);
        textField.city.addKeyListener(aPressKeyEsc);
        textField.district.addKeyListener(aPressKeyEsc);
        textField.corp.addKeyListener(aPressKeyEsc);
        textField.flat.addKeyListener(aPressKeyEsc);
        textField.email.addKeyListener(aPressKeyEsc);
        textField.verificationDocum.addKeyListener(aPressKeyEsc);
        textField.comment.addKeyListener(aPressKeyEsc);
    }

    public DgGeneral(boolean editStatus) throws ParseException {
        this.editStatus = editStatus;
        initConfig();
        add(initTextData());
        add(initButtons());
        setDocumentListener();
        setaPressKeyEsc();

    }

    private JPanel initTextData() throws ParseException {
        textField = new DgGeneralTextFieldNull(editStatus);
        textField.setBounds(15, 15, 540, 610);
        textField.setBorder(BorderFactory.createEtchedBorder());
        return textField;
    }

    public void addFamilyList(int personId) {
        add(initFamilyList(personId));
    }

    public void addSmenaList(int personId) {
        add(initSmenaList(personId));
    }

    private PersFamily initFamilyList(int personId) {
        familyList = new PersFamily(personId, editStatus);
        familyList.setBounds(570, 10, 300, 280);
        familyList.setBorder(BorderFactory.createEtchedBorder());
        return familyList;
    }

    private PersSmena initSmenaList(int personId) {
        smenaList = new PersSmena(personId, editStatus);
        smenaList.setBounds(570, 320, 300, 280);
        smenaList.setBorder(BorderFactory.createEtchedBorder());
        return smenaList;
    }

    private JPanel initButtons() {
        JPanel jPanelButton = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        btnOk = new JButton("Ok");
        btnOk.setEnabled(false);
        jPanelButton.add(btnOk);
        btnOk.addActionListener(aPressBtnTrue);

        btnCancel = new JButton("Cancel");
        jPanelButton.add(btnCancel);
        btnCancel.addActionListener(aPressBtnFalse);

        btnApply = new JButton("Применить");
        btnApply.setEnabled(false);
//        jPanelButton.add(btnApply);
        btnApply.addActionListener(aPressBtnApply);

        jPanelButton.setBounds(540, 625, 300, 50);
        return jPanelButton;
    }

    private DgGeneralDocumentListiner documentListener = new DgGeneralDocumentListiner(this);

    public void setPerson(Person p) {
        createNewPerson = false;
        textField.idp.setText("" + p.getId());
        textField.code.setText("" + p.getCode());
        textField.fName.setText("" + p.getfName());
        textField.mName.setText("" + p.getmName());
        textField.lName.setText("" + p.getlName());
        textField.bDate.setText("" + p.getbDate());
        textField.fPhone.setText("" + p.getfPhone());
        textField.lPhone.setText("" + p.getlPhone());
        textField.homePhone.setText("" + p.getHomePhone());
        textField.inn.setText("" + p.getInn());
        textField.email.setText(p.getEmail() == null ? "" : "" + p.getEmail());
        textField.status.setText("" + p.getStatus());
        textField.comment.setText(p.getComment() == null ? "" : "" + p.getComment());
        textField.createInfo.setText(p.getCreateInfo() == null ? "01.01.2017" : "" + p.getCreateInfo());
        textField.createUser.setText(p.getCreateUser() == null ? "unknown" : "" + p.getCreateUser());
    }

    public void setPersonData(PersonData p) {
        textField.idpd.setText("" + p.getId());
        textField.city.setSelectedIndex(p.getCity());
        textField.district.setSelectedIndex(p.getDistrict());
        textField.street.setText("" + p.getStreet());
        textField.house.setText("" + p.getHouse());
        textField.corp.setText(p.getCorp() == null ? "" : "" + p.getCorp());
        textField.flat.setText(p.getFlat() == null ? "" : "" + p.getFlat());
        textField.sPassport.setText("" + p.getsPassport());
        textField.dPassport.setText("" + p.getdPassport());
        textField.aPassport.setSelectedItem("" + p.getaPassport());
        textField.pensionNum.setText("" + p.getPensionNum());
        textField.verificationDocum.setSelected(p.getVerificationDocum() == 1);
        textField.oldAddres.setText("" + p.getOldAdres());
    }

    private String getCode() {
        return String.valueOf(hashCode());
    }

    private String getCreateInfo() {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        return format.format(new Date());
    }

    private String getCreateUser() {
        return WORK_USER_NAME;
    }

    public Person getPerson() {
        Person p = new Person();
        p.setId(textField.idp.getText() == "Не присвоен" ? 0 : Integer.parseInt(textField.idp.getText()));
        p.setCode(textField.code.getText() == "Не присвоен" ? getCode() : textField.code.getText());
        p.setfName(textField.fName.getText());
        p.setmName(textField.mName.getText());
        p.setlName(textField.lName.getText());
        p.setbDate(textField.bDate.getText());
        p.setfPhone(textField.fPhone.getText());
        p.setlPhone(textField.lPhone.getText() == null ? "" : textField.lPhone.getText());
        p.setHomePhone(textField.homePhone.getText() == null ? "" : textField.homePhone.getText());
        p.setInn(textField.inn.getText());
        p.setEmail(textField.email.getText() == null ? "" : textField.email.getText());
        p.setStatus(Integer.parseInt(textField.status.getText()));
        p.setComment(textField.comment.getText() == null ? "" : textField.comment.getText());
        p.setCreateInfo(textField.createInfo.getText() == "Не присвоен" ? getCreateInfo() : textField.createInfo.getText());
        p.setCreateUser(textField.createUser.getText() == "Не присвоен" ? getCreateUser() : textField.createUser.getText());
        System.out.println(p.toString());
        return p;
    }

    public PersonData getPersonData() {
        PersonData p = new PersonData();
        p.setId(textField.idpd.getText() == "Не присвоен" ? 0 : Integer.parseInt(textField.idpd.getText()));
        p.setCity(textField.city.getSelectedIndex());
        p.setDistrict(textField.district.getSelectedIndex());
        p.setStreet(textField.street.getText());
        p.setHouse(textField.house.getText());
        p.setCorp(textField.corp.getText());
        p.setFlat(textField.flat.getText() == null ? "" : textField.flat.getText());
        p.setsPassport(textField.sPassport.getText());
        p.setdPassport(textField.dPassport.getText());
        p.setaPassport(textField.aPassport.getSelectedItem()+"");
        p.setPensionNum(textField.pensionNum.getText());
        p.setStatus(Integer.parseInt(textField.status.getText()));
        p.setComment(textField.comment.getText() == null ? "" : textField.comment.getText());
        p.setVerificationDocum(textField.verificationDocum.isSelected() ? 1 : 0);
        p.setOldAdres(textField.oldAddres.getText() == null ? "" : textField.oldAddres.getText());
        return p;
    }

    private class PressBtnTrue implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (checkCreateNewPerson()) {
                if (updateForm != 0) {
                    ret = "Ok";
                } else {
                    ret = "Cancel";
                }
                setVisible(false);
            }
        }
    }

    private class PressBtnFalse implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            closeDialog();
        }
    }

    private class PressBtnApply implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            ret = "Apply";
        }
    }

    private class PressKeyEsc implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                closeDialog();
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }

    private void closeDialog() {
        int res = JOptionPane.showConfirmDialog(null,
                "Закрыть диалоговое окно без сохранения изменений?",
                "Закрыть карточку?", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        System.out.println(res);
        if (res == JOptionPane.YES_OPTION) {
            ret = "Cancel";
            setVisible(false);
        }
    }

    public void setUpdateForm(int updateForm) {
        this.updateForm = updateForm;
    }

    private class UpdateNotTxtData implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            updateForm++;
        }
    }

    private boolean checkCreateNewPerson() {
//      Принимаемый параметр для поисковой функции: 1 - поиск по Фамилии; 2 - поиск по ИНН; 3 - поиск по Серии паспорта
        ArrayList<Person> pp = Factory.getPersonDAO().searchPerson(textField.inn.getText(), 2, true);
        pp.addAll(Factory.getPersonDAO().searchPerson(textField.sPassport.getText(), 3, true));
        String message;
        if (createNewPerson) {
            if (pp.size() != 0) {
                message = "Введены не уникальные данные!!!\n\n" +
                        "Указанные данные: " + "\n" +
                        "ИНН: " + textField.inn.getText() + "\n" +
                        "Серия паспорта " + textField.sPassport.getText() + "\n" +
                        "также указан в регистрационной карточке " + "\n" +
                        "ID: " + pp.get(0).getId() + "\n" +
                        "ФИО: " + pp.get(0).getlName() + " " + pp.get(0).getfName() + " " + pp.get(0).getmName() + "\n" +
                        "ИНН: " + pp.get(0).getInn() + "\n" +
                        "Серия паспорта " + pp.get(0).getPersonData().getsPassport() + "\n" +
                        "зарегистрирована в базе " + pp.get(0).getCreateInfo();

                JOptionPane.showMessageDialog(null, message, "Запись данных не возможна!", JOptionPane.ERROR_MESSAGE);
            }
            return pp.size() == 0;
        } else {
            if (pp.size() > 1) {
                for (Person p : pp) {
                    int checkPerson = p.getId();
                    int dialogPerson = Integer.parseInt(textField.idp.getText());
                    if (checkPerson != dialogPerson) {
                        message = "Обнаружено совпадение данных!!!\n\n" +
                                "Указанные данные: " + "\n" +
                                "ID: " + textField.idp.getText() + "\n" +
                                "ИНН: " + textField.inn.getText() + "\n" +
                                "Серия паспорта " + textField.sPassport.getText() + "\n" +
                                "также указан в регистрационной карточке " + "\n" +
                                "ID: " + p.getId() + "\n" +
                                "ФИО: " + p.getlName() + " " + p.getfName() + " " + p.getmName() + "\n" +
                                "ИНН: " + p.getInn() + "\n" +
                                "Cерия паспорта: " + p.getPersonData().getsPassport() + "\n" +
                                "зарегистрирована в базе " + p.getCreateInfo();

                        JOptionPane.showMessageDialog(null, message, "Запись данных не возможна!", JOptionPane.ERROR_MESSAGE);
                        return false;
                    }
                }
            }
        }
        return true;
    }
}

package view.dialogs;

import blogic.entity.Person;
import blogic.entity.PersonData;
import view.viewList.VListFamily;
import view.viewList.VListSmena;
//import view.viewList.VListVisit;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.ParseException;

/**
 * Created by hammer on 17.07.2017.
 */
public class DgPerson extends JDialog {

    public String ret = "Cancel";
    JButton btnOk = null;
    JButton btnCancel = null;
    JButton btnApply = null;
    JButton btnFamily = null;
    JButton btnVisit = null;
    DgPersonTextField textField;

    private PressBtnTrue aPressBtnTrue = new PressBtnTrue();
    private PressBtnFalse aPressBtnFalse = new PressBtnFalse();
    private PressBtnApply aPressBtnApply = new PressBtnApply();
    private PressKeyEsc aPressKeyEsc = new PressKeyEsc();
    private PressBtnFamily aPressBtnFamily = new PressBtnFamily();
    private PressBtnSmena aPressBtnSmena = new PressBtnSmena();

    public DgPerson() throws ParseException {

        setLayout(new BorderLayout());
        Dimension sSize = Toolkit.getDefaultToolkit().getScreenSize();
        int w = 850;
        int h = 510;
        int x = ((sSize.width - w) / 2);
        int y = ((sSize.height - h) / 2);
        setBounds(x, y, 850, 510);

        textField = new DgPersonTextField(true);
        add(textField, BorderLayout.CENTER);

        JPanel buttons = new JPanel();

        btnOk = new JButton("Ok");
        btnOk.setBounds(130, 330, 120, 25);
        btnOk.setEnabled(false);
        buttons.add(btnOk);
        btnOk.addActionListener(aPressBtnTrue);

        btnCancel = new JButton("Cancel");
        btnCancel.setBounds(260, 330, 150, 25);
        buttons.add(btnCancel);
        btnCancel.addActionListener(aPressBtnFalse);

        btnApply = new JButton("Применить");
        btnApply.setBounds(260, 330, 150, 25);
        btnApply.setEnabled(false);
        buttons.add(btnApply);
        btnApply.addActionListener(aPressBtnApply);

        btnFamily = new JButton("Родственники");
        btnFamily.setBounds(260, 330, 150, 25);
        btnFamily.setActionCommand("" + textField.idp);
        buttons.add(btnFamily);
        btnFamily.addActionListener(aPressBtnFamily);

        btnVisit = new JButton("Визиты");
        btnVisit.setBounds(260, 330, 150, 25);
        btnVisit.setActionCommand("" + textField.idp);
        buttons.add(btnVisit);
        btnVisit.addActionListener(aPressBtnSmena);


        add(buttons, BorderLayout.SOUTH);

        textField.fName.getDocument().addDocumentListener(documentListener);
        textField.mName.getDocument().addDocumentListener(documentListener);
        textField.lName.getDocument().addDocumentListener(documentListener);
        textField.bDate.getDocument().addDocumentListener(documentListener);
        textField.sPassport.getDocument().addDocumentListener(documentListener);
        textField.dPassport.getDocument().addDocumentListener(documentListener);
        textField.aPassport.getDocument().addDocumentListener(documentListener);
        textField.inn.getDocument().addDocumentListener(documentListener);
        textField.pensionNum.getDocument().addDocumentListener(documentListener);
        textField.fPhone.getDocument().addDocumentListener(documentListener);
        textField.street.getDocument().addDocumentListener(documentListener);
        textField.house.getDocument().addDocumentListener(documentListener);

        textField.fName.addKeyListener(aPressKeyEsc);
    }

    private DocumentListener documentListener = new DocumentListener() {
        public void check(DgPersonTextField textField) {
            if (textField.fName.getText().equals("") || textField.mName.getText().equals("") ||
                    textField.lName.getText().equals("") ||
                    textField.bDate.getText().equals("  .  .    ") || textField.pensionNum.getText().equals("") ||
                    textField.inn.getText().equals("") || textField.fPhone.getText().equals("+3 8(   )   -  -  ") ||
                    textField.sPassport.getText().equals("         ") || textField.aPassport.getText().equals("") ||
                    textField.dPassport.getText().equals("  .  .    ") ||
                    textField.street.getText().equals("") ||
                    textField.house.getText().equals("")
                    ) {
                btnOk.setEnabled(false);
            } else {
                btnOk.setEnabled(true);
            }
        }


        @Override
        public void changedUpdate(DocumentEvent e) {
            check(textField);
        }

        @Override
        public void insertUpdate(DocumentEvent e) {
            check(textField);
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            check(textField);
        }
    };

    public void setPerson(Person p) {
        textField.idp.setText("" + p.getId());
        textField.code.setText("" + p.getCode());
        textField.fName.setText("" + p.getfName());
        textField.mName.setText("" + p.getmName());
        textField.lName.setText("" + p.getlName());
        textField.bDate.setText("" + p.getbDate());
        textField.fPhone.setText("" + p.getfPhone());
        textField.lPhone.setText("" + p.getlPhone());
        textField.inn.setText("" + p.getInn());
        textField.email.setText(p.getEmail() == null ? "" : "" + p.getEmail());
        textField.status.setText("" + p.getStatus());
        textField.comment.setText(p.getComment() == null ? "" : "" + p.getComment());
    }

    public void setPersonData(PersonData p) {
        textField.idpd.setText(""+p.getId());
        textField.city.setSelectedIndex(p.getCity());
        textField.district.setSelectedIndex(p.getDistrict());
        textField.street.setText("" + p.getStreet());
        textField.house.setText("" + p.getHouse());
        textField.corp.setText(p.getCorp() == null ? "" : "" + p.getCorp());
        textField.flat.setText(p.getFlat() == null ? "" : "" + p.getFlat());
        textField.sPassport.setText("" + p.getsPassport());
        textField.dPassport.setText("" + p.getdPassport());
        textField.aPassport.setText("" + p.getaPassport());
        textField.pensionNum.setText("" + p.getPensionNum());
        textField.verificationDocum.setSelected(p.getVerificationDocum() == 1);
        textField.oldAdres.setText(""+p.getOldAdres());
    }

    public String getCode() {
        return String.valueOf(hashCode());
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
        p.setInn(textField.inn.getText());
        p.setEmail(textField.email.getText() == null ? "" : textField.email.getText());
        p.setStatus(Integer.parseInt(textField.status.getText()));
        p.setComment(textField.comment.getText() == null ? "" : textField.comment.getText());
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
        p.setaPassport(textField.aPassport.getText());
        p.setPensionNum(textField.pensionNum.getText());
        p.setStatus(Integer.parseInt(textField.status.getText()));
        p.setComment(textField.comment.getText() == null ? "" : textField.comment.getText());
        p.setVerificationDocum(textField.verificationDocum.isSelected() ? 1 : 0);
        p.setOldAdres(textField.oldAdres.getText() == null ? "" : textField.oldAdres.getText());
        return p;
    }

    private class PressBtnTrue implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            ret = "Ok";
            setVisible(false);
        }
    }

    private class PressBtnFalse implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            ret = "Cancel";
            setVisible(false);
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
                ret = "Cancel";
                setVisible(false);
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }

    private class PressBtnFamily implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            VListFamily vListFamily = new VListFamily(getPerson());
            vListFamily.setModal(true);
            vListFamily.setVisible(true);
        }
    }

    private class PressBtnSmena implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
//            int idp = 1;
            VListSmena vListSmena = new VListSmena();
            vListSmena.setModal(true);
            vListSmena.setVisible(true);
        }

    }
}

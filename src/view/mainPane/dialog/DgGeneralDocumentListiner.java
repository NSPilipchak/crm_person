package view.mainPane.dialog;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by hammer on 07.09.2017.
 */
public class DgGeneralDocumentListiner implements DocumentListener {

    DgGeneral dg;

    public DgGeneralDocumentListiner(DgGeneral dg) {
        this.dg = dg;
    }

    private boolean checkPassport(JTextField str) {
        Pattern p = Pattern.compile("(^[А-Я]{2}[0-9]{6}$)|(^[0-9]{9}$)");
        Matcher m = p.matcher(str.getText());
        str.setBackground(m.matches() ? Color.WHITE : Color.LIGHT_GRAY);
        System.out.print(m.matches() ? "" : ("checkPassport " + m.matches() + " str " + str.getText() + "\n"));
        return m.matches();
    }

    private boolean checkINN(JTextField str) {
        Pattern p = Pattern.compile("(^[0-9]{10}$)");
        Matcher m = p.matcher(str.getText());
        str.setBackground(m.matches() ? Color.WHITE : Color.LIGHT_GRAY);
        System.out.print(m.matches() ? "" : ("checkINN " + m.matches() + " str " + str.getText() + "\n"));
        return m.matches();
    }

    private boolean checkDate(JTextField str) {
        Pattern p = Pattern.compile(
                "(^(([0][1-9])|([12][0-9])|([3][01]))" +
                        "\\.(([0][1-9])|([1][012]))\\.([12][0-9]{3})$)");
        Matcher m = p.matcher(str.getText());
        str.setBackground(m.matches() ? Color.WHITE : Color.LIGHT_GRAY);
        System.out.print(m.matches() ? "" : ("checkDate " + m.matches() + " str " + str.getText() + "\n"));
        return m.matches();
    }

    private boolean checkTXT(JTextField str) {
        Pattern p = Pattern.compile(
                "[А-яІі'ЄєЇї0-9\\s\\W]{2,50}");
        Matcher m = p.matcher(str.getText());
        str.setBackground(m.matches() ? Color.WHITE : Color.LIGHT_GRAY);
        System.out.print(m.matches() ? "" : ("checkTXT " + m.matches() + " str " + str.getText() + "\n"));
        return m.matches();
    }

    private boolean checkNum(JTextField str) {
        Pattern p = Pattern.compile(
                "[0-9]{1,6}[А-яІі'ЄєЇї0-9\\s\\W]*");
        Matcher m = p.matcher(str.getText());
        str.setBackground(m.matches() ? Color.WHITE : Color.LIGHT_GRAY);
        System.out.print(m.matches() ? "" : ("checkNum " + m.matches() + " str " + str.getText() + "\n"));
        return m.matches();
    }

    private boolean checkPhone(JTextField str) {
        Pattern p = Pattern.compile(
                "^\\+38\\([0-9]{3}\\)[0-9]{3}\\W[0-9]{2}\\W[0-9]{2}$");
        Matcher m = p.matcher(str.getText());
        str.setBackground(m.matches() ? Color.WHITE : Color.LIGHT_GRAY);
        System.out.print(m.matches() ? "" : ("checkPhone " + m.matches() + " str " + str.getText() + "\n"));
        return m.matches();
    }

    public void check(DgGeneralTextFieldNull textField) {
        if (checkINN(textField.inn)
                & checkPassport(textField.sPassport)
                & checkDate(textField.bDate)
                & checkDate(textField.dPassport)
                & checkTXT(textField.fName)
                & checkTXT(textField.mName)
                & checkTXT(textField.lName)
//                & checkTXT(textField.aPassport)
                & checkTXT(textField.street)
                & checkNum(textField.house)
                & checkPhone(textField.fPhone)
                ) {
            dg.btnOk.setEnabled(true);
        } else {
            dg.btnOk.setEnabled(false);
        }
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        dg.updateForm++;
        check(dg.textField);
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        dg.updateForm++;
        check(dg.textField);
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        dg.updateForm++;
        check(dg.textField);
    }
}

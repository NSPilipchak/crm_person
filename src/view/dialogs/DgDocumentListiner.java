package view.dialogs;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by hammer on 07.09.2017.
 */
public class DgDocumentListiner implements DocumentListener {

    DgFamily dg;
    DgSmena ds;
    DgUser du;
    DgCognate dc;
    DgDistrict dd;
    DgApp da;
    DgCity dcyt;
    DgDistrictPassport dgdp;

    public DgDocumentListiner(DgCognate dc) {
        this.dc = dc;
    }

    public DgDocumentListiner(DgDistrictPassport dgdp) {
        this.dgdp = dgdp;
    }

    public DgDocumentListiner(DgCity dcyt) {
        this.dcyt = dcyt;
    }

    public DgDocumentListiner(DgApp da) {
        this.da = da;
    }

    public DgDocumentListiner(DgDistrict dd) {
        this.dd = dd;
    }

    public DgDocumentListiner(DgUser du) {
        this.du = du;
    }

    public DgDocumentListiner(DgFamily dg) {
        this.dg = dg;
    }

    public DgDocumentListiner(DgSmena ds) {
        this.ds = ds;
    }

    private boolean checkDate(JTextField str) {
        Pattern p = Pattern.compile(
                "(^(([0][1-9])|([12][0-9])|([3][01]))" +
                        "\\.(([0][1-9])|([1][012]))\\.([12][0-9]{3})$)");
        Matcher m = p.matcher(str.getText());
        str.setBackground(m.matches() ? Color.WHITE : Color.LIGHT_GRAY);
        return m.matches();
    }

    private boolean checkTXT(JTextField str) {
        Pattern p = Pattern.compile(
                "[А-яІі'ЄєЇї0-9\\s\\W]{2,50}");
        Matcher m = p.matcher(str.getText());
        str.setBackground(m.matches() ? Color.WHITE : Color.LIGHT_GRAY);
        return m.matches();
    }

    private boolean checkUser(JTextField str) {
        Pattern p = Pattern.compile(
                "[A-z]{4,10}");
        Matcher m = p.matcher(str.getText());
        str.setBackground(m.matches() ? Color.WHITE : Color.LIGHT_GRAY);
        return m.matches();
    }

    private boolean checkNum(JTextField str) {
        Pattern p = Pattern.compile(
                "[0-9]{1,6}[А-яІі'ЄєЇї0-9\\s\\W]*");
        Matcher m = p.matcher(str.getText());
        str.setBackground(m.matches() ? Color.WHITE : Color.LIGHT_GRAY);
        return m.matches();
    }

    private boolean checkNumber(JTextField str) {
        Pattern p = Pattern.compile(
                "([0-9]{1,4})");
        Matcher m = p.matcher(str.getText());
        str.setBackground(m.matches() ? Color.WHITE : Color.LIGHT_GRAY);
        return m.matches();
    }

    private boolean checkPhone(JTextField str) {
        Pattern p = Pattern.compile(
                "^\\+38\\([0-9]{3}\\)[0-9]{3}\\W[0-9]{2}\\W[0-9]{2}$");
        Matcher m = p.matcher(str.getText());
        str.setBackground(m.matches() ? Color.WHITE : Color.LIGHT_GRAY);
        return m.matches();
    }

    private boolean checkNewPass(JTextField strf, JTextField strs, JTextField pass) {
        if (strf.getText().equals("") && strs.getText().equals("") && pass.getText().equals("")) {
            return false;
        } else if (strf.getText().equals("") && strs.getText().equals("") && !pass.getText().equals("")) {
            strf.setBackground(Color.WHITE);
            strs.setBackground(Color.WHITE);
            return true;
        } else if (strs.getText().equals(strf.getText())) {
            Pattern p = Pattern.compile("[A-z0-9]{5,10}");
            Matcher ms = p.matcher(strs.getText());
            Matcher mf = p.matcher(strf.getText());
            strs.setBackground(ms.matches() ? Color.GREEN : Color.PINK);
            strf.setBackground(mf.matches() ? Color.GREEN : Color.PINK);
            if (ms.matches() || mf.matches()) {
                return true;
            }
        } else if (!strs.getText().equals(strf.getText())) {
            strf.setBackground(Color.PINK);
            strs.setBackground(Color.PINK);
            return false;
        }
        return false;
    }

    private void check(DgFamilyTextField textField) {
        if (checkPhone(textField.fPhone) &
                checkTXT(textField.name)
                ) {
            dg.btnOk.setEnabled(true);
        } else {
            dg.btnOk.setEnabled(false);
        }
    }

    private void check(DgSmenaTextField textField) {
        if (checkTXT(textField.name)
                & checkNumber(textField.number)
                & checkDate(textField.dateStart)
                & checkDate(textField.dateEnd)
                & checkNumber(textField.places)
                ) {
            ds.btnOk.setEnabled(true);
        } else {
            ds.btnOk.setEnabled(false);
        }
    }

    private void check(DgUserTextField textField) {
        if (checkUser(textField.user)
                & checkNewPass(textField.tfPasswordFirst, textField.tfPasswordSecond, textField.pass)) {
            du.btnOk.setEnabled(true);
        } else

        {
            du.btnOk.setEnabled(false);
        }
    }

    private void check(DgCognateTextField textField) {
        if (checkTXT(textField.cognate)) {
            dc.btnOk.setEnabled(true);
        } else {
            dc.btnOk.setEnabled(false);
        }
    }

    private void check(DgDistrictPassport textField) {
        if (checkTXT(textField.textField.districtPassport)) {
            dgdp.btnOk.setEnabled(true);
        } else {
            dgdp.btnOk.setEnabled(false);
        }
    }

    private void check(DgDistrictTextField textField) {
        if (checkTXT(textField.district)
                & checkNumber(textField.quota)) {
            dd.btnOk.setEnabled(true);
        } else {
            dd.btnOk.setEnabled(false);
        }
    }

    private void check(DgApp textField) {
        if (checkMD5(textField.value)) {
            da.btnOk.setEnabled(true);
        } else {
            da.btnOk.setEnabled(false);
        }
    }

    private void check(DgCity textField) {
        if (checkTXT(textField.textField.city)) {
            dcyt.btnOk.setEnabled(true);
        } else {
            dcyt.btnOk.setEnabled(false);
        }
    }

    private boolean checkMD5(JTextField value) {
        Pattern p = Pattern.compile(
                "([A-z0-9]{2,30})|([A-z0-9]{32})|"
                        + "(^(([0][1-9])|([12][0-9])|([3][01]))" +
                        "\\.(([0][1-9])|([1][012]))\\.([12][0-9]{3})$)|" +
                        "(^[0-9]\\.[0-9]\\.[0-9]\\.[0-9]$)");
        Matcher m = p.matcher(value.getText());
        value.setBackground(m.matches() ? Color.WHITE : Color.LIGHT_GRAY);
        return m.matches();
    }

    private void selectCheck() {
        if (dg != null)
            check(dg.textField);
        else if (ds != null)
            check(ds.textField);
        else if (du != null)
            check(du.textField);
        else if (dc != null)
            check(dc.textField);
        else if (dd != null)
            check(dd.textField);
        else if (da != null)
            check(da);
        else if (dcyt != null)
            check(dcyt);
        else if (dgdp != null)
            check(dgdp);
    }


    @Override
    public void changedUpdate(DocumentEvent e) {
        selectCheck();
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        selectCheck();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        selectCheck();
    }
}

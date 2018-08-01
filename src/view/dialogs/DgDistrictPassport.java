package view.dialogs;

import blogic.entity.Cognate;
import blogic.entity.DistrictPassport;

import javax.swing.*;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

/**
 * Created by hammer on 17.07.2017.
 */
public class DgDistrictPassport extends JDialog {

    public String ret = "Cancel";
    JButton btnOk = null;
    JButton btnCancel = null;
    DgDistrictPassportTextField textField;

    private PressBtnTrue aPressBtnTrue = new PressBtnTrue();
    private PressBtnFalse aPressBtnFalse = new PressBtnFalse();

    public DgDistrictPassport() throws ParseException {

        setLayout(new BorderLayout());
        Dimension sSize = Toolkit.getDefaultToolkit().getScreenSize();
        int w = 450;
        int h = 450;
        int x = ((sSize.width - w) / 2);
        int y = ((sSize.height - h) / 2);
        setBounds(x, y, w, h);

        textField = new DgDistrictPassportTextField(true);
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
        add(buttons, BorderLayout.SOUTH);

        textField.districtPassport.getDocument().addDocumentListener(documentListener);
    }

    private DocumentListener documentListener = new DgDocumentListiner(this);

    public void setDistrictPassport(DistrictPassport u) {
        textField.id.setText("" + u.getId());
        textField.districtPassport.setText("" + u.getDistrictPassport());
    }

    public DistrictPassport getDistrictPassport() {
        DistrictPassport u = new DistrictPassport();
        u.setId(Integer.parseInt(textField.id.getText()));
        u.setDistrictPassport(textField.districtPassport.getText());
        return u;
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
}

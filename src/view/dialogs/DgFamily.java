package view.dialogs;

import blogic.entity.Family;

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
public class DgFamily extends JDialog {

    public String ret = "Cancel";
    JButton btnOk = null;
    JButton btnCancel = null;
    DgFamilyTextField textField;

    private PressBtnTrue aPressBtnTrue = new PressBtnTrue();
    private PressBtnFalse aPressBtnFalse = new PressBtnFalse();
    private PressKeyEsc aPressKeyEsc = new PressKeyEsc();

    public DgFamily() throws ParseException {

        setLayout(new BorderLayout());
        Dimension sSize = Toolkit.getDefaultToolkit().getScreenSize();
        int w = 450;
        int h = 450;
        int x = ((sSize.width - w) / 2);
        int y = ((sSize.height - h) / 2);
        setBounds(x, y, w, h);

        textField = new DgFamilyTextField(true);
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

        textField.name.getDocument().addDocumentListener(documentListener);
        textField.fPhone.getDocument().addDocumentListener(documentListener);

        textField.name.addKeyListener(aPressKeyEsc);
    }

    private DocumentListener documentListener = new DgDocumentListiner(this);

    public void setFamily(Family p) {
        textField.id.setText("" + p.getId());
        textField.name.setText("" + p.getName());
        textField.cognate.setSelectedItem("" + p.getCognate());
        textField.bDate.setText("" + p.getbDate());
        textField.fPhone.setText("" + p.getfPhone());
        textField.lPhone.setText("" + p.getlPhone());
        textField.email.setText(p.getEmail()==null?"":""+p.getEmail());
        textField.status.setText("" + p.getStatus());
        textField.verificationDocum.setSelected(p.getVerificationDocum() == 1);
        textField.comment.setText(p.getComment()==null?"":""+p.getComment());
    }

    public Family getFamily() {
        Family p = new Family();
        p.setId(Integer.parseInt(textField.id.getText()));
        p.setName(textField.name.getText());
        p.setCognate(textField.cognate.getSelectedItem()+"");
        p.setbDate(textField.bDate.getText());
        p.setfPhone(textField.fPhone.getText());
        p.setlPhone(textField.lPhone.getText() == null ? "" : textField.lPhone.getText());
        p.setEmail(textField.email.getText() == null ? "" : textField.email.getText());
        p.setStatus(Integer.parseInt(textField.status.getText()));
        p.setVerificationDocum(textField.verificationDocum.isSelected() ? 1 : 0);
        p.setComment(textField.comment.getText());
        System.out.println(p.toString());
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
}

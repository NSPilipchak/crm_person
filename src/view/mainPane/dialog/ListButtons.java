package view.mainPane.dialog;

import blogic.entity.FamilyDM;
import blogic.entity.PersonDM;
import blogic.entity.SmenaDM;

import javax.swing.*;
import java.awt.*;

/**
 * Created by hammer on 22.07.2017.
 */
public class ListButtons extends JPanel {
    JButton btnCreate;
    JButton btnUpdate;
    JButton btnDelete;
    private boolean editStatus;

    public ListButtons(FamilyDM dm, boolean editStatus) {
        this.editStatus = editStatus;
        init();
        createBtn();
        btnCreate.addActionListener(dm.aAddToPerson);
        btnUpdate.addActionListener(dm.aUpdateToPerson);
        btnDelete.addActionListener(dm.aDelete);
        addBtn();
    }

    public ListButtons(SmenaDM dm, boolean editStatus) {
        this.editStatus = editStatus;
        init();
        createBtn();
        btnCreate.addActionListener(dm.aAddToPerson);
        btnUpdate.addActionListener(dm.aUpdate);
        btnDelete.addActionListener(dm.aDeleteFromPerson);
        addBtn();
    }

    private void addBtn() {
        add(btnCreate);
        add(btnUpdate);
        add(btnDelete);
    }

    private void init() {
        setLayout(null);
        setBounds(0, 0, 136, 20);
    }

    private void createBtn() {
        btnCreate = new JButton("+");
        btnCreate.setToolTipText("Создать новую запись");
        btnCreate.setIcon(new ImageIcon("resources/ic_exposure_plus_1_black_24dp.png"));
        btnCreate.setEnabled(editStatus);
        btnCreate.setBounds(0, 0, 45, 25);

        btnUpdate = new JButton("*");
        btnUpdate.setToolTipText("Редактировать выбранную запись");
        btnUpdate.setIcon(new ImageIcon("resources/ic_create_black_24dp.png"));
        btnUpdate.setEnabled(editStatus);
        btnUpdate.setBounds(45, 0, 45, 25);

        btnDelete = new JButton("-");
        btnDelete.setToolTipText("Удалить выбранную запись");
        btnDelete.setIcon(new ImageIcon("resources/ic_delete_forever_black_24dp.png"));
        btnDelete.setEnabled(editStatus);
        btnDelete.setBounds(90, 0, 45, 25);
    }
}

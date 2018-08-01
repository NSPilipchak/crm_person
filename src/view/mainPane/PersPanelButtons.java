package view.mainPane;

import blogic.entity.PersonDMwithPersonData;

import javax.swing.*;
import javax.swing.border.TitledBorder;

/**
 * Created by hammer on 22.07.2017.
 */
public class PersPanelButtons extends JPanel {
    JButton btnRefresh;
    JButton btnCreate;
    JButton btnUpdate;
    JButton btnDelete;

    public PersPanelButtons(PersonDMwithPersonData dm) {
        init();
        createBtn();
        btnRefresh.addActionListener(dm.aRead);
        btnCreate.addActionListener(dm.aCreate);
        btnUpdate.addActionListener(dm.aUpdate);
        btnDelete.addActionListener(dm.aDelete);
        addBtn();
    }
    private void addBtn() {
//        add(btnRefresh);
        add(btnCreate);
        add(btnUpdate);
        add(btnDelete);
    }

    private void init() {
        setLayout(null);
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
//        setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        setBorder(new TitledBorder("Кнопочки"));

    }

    private void createBtn() {
        btnRefresh = new JButton("Обновить список");
        btnCreate = new JButton("Новая запись");
        btnUpdate = new JButton("Редактировать");
        btnDelete = new JButton("Удалить запись");
    }
}

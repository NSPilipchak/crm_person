package reports.exportReports;

import javax.swing.*;

/**
 * Created by hammer on 17.08.2017.
 */
class Buttons extends JPanel {
    private JButton btnRefresh;
    private JButton btnOption;
    private JButton btnSave;

    Buttons(EntityPersonSmena dm) {
        init();
        createBtn();
        btnRefresh.addActionListener(dm.aRead);
        btnOption.addActionListener(dm.aOption);
        btnSave.addActionListener(dm.aSave);
        addBtn();
    }



    private void init() {
        setLayout(null);
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
    }

    private void createBtn() {
        btnRefresh = new JButton("Обновить");
        btnOption = new JButton("Настроить вывод");
        btnSave = new JButton("Сохранить отчет");
    }

    private void addBtn() {
        add(btnRefresh);
        add(btnOption);
        add(btnSave);
    }
}

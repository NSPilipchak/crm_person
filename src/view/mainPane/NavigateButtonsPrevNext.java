package view.mainPane;

import blogic.entity.PersonDMwithPersonData;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.TextAttribute;
import java.util.HashMap;
import java.util.Map;

import static properties.Properies.MAX_RESULT;

class NavigateButtonsPrevNext extends JPanel {
    private JTextField textInputMax_Result;
    private JLabel txtMaxResult;
    private JButton btnNext;
    private JButton btnPrev;
    private JButton btnApply;
    private PersonDMwithPersonData dm;

    private ActionNextPrev actionNextPrev = new ActionNextPrev();

    NavigateButtonsPrevNext(NavigateCommand cmd) {
        this.dm = cmd.dm;
        createBtn();
        setLayout(null);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        btnNext.addActionListener(actionNextPrev);
        btnPrev.addActionListener(actionNextPrev);
        btnApply.addActionListener(actionNextPrev);

        JPanel first = new JPanel();
        first.setLayout(new GridLayout(1, 1, 0, 0));
        first.add(btnApply);

        add(first);
        JPanel second = new JPanel();
        second.setLayout(new GridLayout(4, 1, 0, 0));
        second.setBorder(BorderFactory.createEtchedBorder());
        second.setBorder(new TitledBorder("Навигация"));
        second.setMinimumSize(new Dimension(200, 150));
        second.setMaximumSize(new Dimension(200, 150));
        second.add(txtMaxResult);
        second.add(textInputMax_Result);
        second.add(btnNext);
        second.add(btnPrev);

        add(second);
    }

    private void createBtn() {
        txtMaxResult = new JLabel("Кол-во записей в пакете:");
        textInputMax_Result = new JTextField(MAX_RESULT);
        textInputMax_Result.setColumns(10);
        btnNext = new JButton("Следующая страница >>");
        btnNext.setActionCommand("next");
        btnPrev = new JButton("<< Предыдущая страница");
        btnPrev.setActionCommand("prev");
        btnApply = new JButton("Применить настройки");

        Map applyMap = new HashMap();
        applyMap.put(TextAttribute.SIZE, 14);
        applyMap.put(Font.SERIF, "");
        applyMap.put(Font.BOLD, "");

        btnApply.setFont(getFont().deriveFont(applyMap));
        btnApply.setActionCommand("apply");
    }

    private class ActionNextPrev implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String act = e.getActionCommand();
            switch (act) {
                case "next":
                    nextPrev(dm.getFirst() + dm.getMaxResult());
                    break;
                case "prev":
                    nextPrev(dm.getFirst() - dm.getMaxResult());
                    break;
                case "apply":
                    dm.setMaxResult(textInputMax_Result.getText().equals("") ? 100 : Integer.parseInt(textInputMax_Result.getText()));
                    break;
            }
            dm.reRead();
            textInputMax_Result.setText(String.valueOf(dm.getMaxResult()));
        }

        private void nextPrev(int first) {
            int maxRow = dm.getMaxRow();
            if (first < 0) {
                first = 0;
            } else if (first > maxRow - Integer.parseInt(MAX_RESULT)) {
                first = maxRow - Integer.parseInt(MAX_RESULT);
            }
            dm.selectPage(first);
        }
    }
}

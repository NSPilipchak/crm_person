package reports.importBase;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import static reports.importBase.ImportFromXLSPerson.readPersonFromExcel;
import static reports.importBase.ImportFromXLSSmena.readSmenaFromExcel;
import static reports.importBase.ImportFromXLSSmenaToPerson.wrihtSmenaToPersonFromExcel;

/**
 * Created by hammer on 16.08.2017.
 */

/**
 * result == 0  // нет всех колонок в файле екселя
 * result == 1  // все прошло успешно
 * result == 2  // загружены не все строки
 */

public class ActionAddFeatures implements ActionListener {
    @Override
    @Deprecated
    public void actionPerformed(ActionEvent e) {
        String str = e.getActionCommand();
        JFileChooser fd = new JFileChooser();
        int result = 0;
        if (fd.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            try {
                switch (str) {
                    case "smena":
                        result = readSmenaFromExcel(fd.getSelectedFile());
                        viewMessage(result);
                        break;
                    case "person":
                        result = readPersonFromExcel(fd.getSelectedFile(),0);
                        viewMessage(result);
                        break;
                    case "personSmena":
                        result = readPersonFromExcel(fd.getSelectedFile(),1);
                        viewMessage(result);
                        break;
                    case "smenaToPerson":
                        result = wrihtSmenaToPersonFromExcel(fd.getSelectedFile());
                        viewMessage(result);
                        break;
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    private void viewMessage(int i) {

        switch (i) {
            case 0:
                JOptionPane.showMessageDialog(null, "Не удается распознать файл для загрузки",
                        "Неверный файл", JOptionPane.ERROR_MESSAGE);
                break;
            case 1:
                JOptionPane.showMessageDialog(null, "Загрузка прошла успешно",
                        "Данный загруженны", JOptionPane.INFORMATION_MESSAGE);
                break;
            case 2:
                JOptionPane.showMessageDialog(null, "Загруженны не все данные, " +
                                "необходимо проверить загруженные данные.",
                        "Внимание!!!", JOptionPane.ERROR_MESSAGE);
                break;
        }

    }
}

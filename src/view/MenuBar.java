package view;

import dal.Factory;
import dal.HibernateBackUpBase;
import reports.exportGlobalReport.ReportGlobal;
import reports.exportReportFromCrossing.ReportCrossList;
import reports.exportReports.SavePersonListFromSmena;
import reports.importBase.ActionAddFeatures;
import view.viewList.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import static properties.Properies.*;
import static properties.Strings.WORK_USER_PERMISSION;

/**
 * Created by hammer on 17.07.2017.
 */
public class MenuBar extends JMenuBar {

    private ActionDirectory actionDirectory = new ActionDirectory();
    private ActionAddFeatures actionAddFeatures = new ActionAddFeatures();
    private ActionReports actionReports = new ActionReports();
    private ActionAdmin actionAdmin = new ActionAdmin();
    private ActionInfo actionInfo = new ActionInfo();
    private JMenuItem reCountSmenas, usersControl, backUpBase2, properties,
            features1, features2, features3, features4,
            directFamily, directSmena, directHousing, directCitys,
            directDistricts, directCognates, directDistrictsPassport, smenaList,
            crossTable, report3, directPerson, about, edu;

    public MenuBar() {

        JMenu mFile = new JMenu("Файл");
        JMenuItem mfQuit = new JMenuItem("Выход");


        JMenu mReport = new JMenu("Отчеты");
        smenaList = new JMenuItem("Сформировать список на смену");
        smenaList.setEnabled(false);
        crossTable = new JMenuItem("Отчет для пересечения базы");
        crossTable.setEnabled(false);
        report3 = new JMenuItem("Глобальный отчет");
        report3.setEnabled(false);

        JMenu mLib = new JMenu("Справочники");
        directPerson = new JMenuItem("Персоны");
        directPerson.setEnabled(false);
        directFamily = new JMenuItem("Родственники");
        directFamily.setEnabled(false);
        directSmena = new JMenuItem("Смены");
        directSmena.setEnabled(false);
        directHousing = new JMenuItem("Раcселение");
        directHousing.setEnabled(false);
        directCitys = new JMenuItem("Города");
        directCitys.setEnabled(false);
        directDistricts = new JMenuItem("Районы города");
        directDistricts.setEnabled(false);
        directCognates = new JMenuItem("Родственные связи");
        directCognates.setEnabled(false);
        directDistrictsPassport = new JMenuItem("Кем выдан пасспорт");
        directDistrictsPassport.setEnabled(false);


        JMenu addFeatures = new JMenu("Доп.возможности");
        features1 = new JMenuItem("Загрузить смены");
        features1.setEnabled(false);
        features2 = new JMenuItem("Загрузить перосны");
        features2.setEnabled(false);
        features3 = new JMenuItem("Загрузить перосны+смену");
        features3.setEnabled(false);
        features4 = new JMenuItem("Загрузить перосне смену");
        features4.setEnabled(false);

        JMenu admin = new JMenu("Администрирование");
        reCountSmenas = new JMenuItem("Пересчет занятых мест на смену");
        reCountSmenas.setEnabled(false);
        usersControl = new JMenuItem("Пользователи");
        usersControl.setEnabled(false);
        backUpBase2 = new JMenuItem("Бэкап базы");
        backUpBase2.setEnabled(false);
        properties = new JMenuItem("Настройки");
        properties.setEnabled(false);


        JMenu info = new JMenu("?");
        about = new JMenuItem("О программе...");
        about.setEnabled(false);
        edu = new JMenuItem("Как это работает");
        edu.setEnabled(false);

        directPerson.setActionCommand("person");
        directFamily.setActionCommand("family");
        directSmena.setActionCommand("smena");
        directHousing.setActionCommand("housing");
        directCitys.setActionCommand("city");
        directDistricts.setActionCommand("district");
        directCognates.setActionCommand("cognate");
        directDistrictsPassport.setActionCommand("districtsPassport");

        directPerson.addActionListener(actionDirectory);
        directFamily.addActionListener(actionDirectory);
        directSmena.addActionListener(actionDirectory);
        directHousing.addActionListener(actionDirectory);
        directCitys.addActionListener(actionDirectory);
        directDistricts.addActionListener(actionDirectory);
        directCognates.addActionListener(actionDirectory);
        directDistrictsPassport.addActionListener(actionDirectory);

        smenaList.setActionCommand("smenaList");
        smenaList.addActionListener(actionReports);
        crossTable.setActionCommand("crossTable");
        crossTable.addActionListener(actionReports);
        report3.setActionCommand("globalReport");
        report3.addActionListener(actionReports);

        mfQuit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int i = JOptionPane.showConfirmDialog(null, "Завершение работы приложения", "Завершение работы", JOptionPane.YES_NO_OPTION);
                if (i == 0)
                    System.exit(0);
            }
        });

        features1.setActionCommand("smena");
        features2.setActionCommand("person");
        features3.setActionCommand("personSmena");
        features4.setActionCommand("smenaToPerson");

        reCountSmenas.setActionCommand("reCountSmenas");
        usersControl.setActionCommand("userControl");
        backUpBase2.setActionCommand("backUpBase");
        properties.setActionCommand("properties");

        about.setActionCommand("about");
        edu.setActionCommand("edu");

        features1.addActionListener(actionAddFeatures);
        features2.addActionListener(actionAddFeatures);
        features3.addActionListener(actionAddFeatures);
        features4.addActionListener(actionAddFeatures);

        reCountSmenas.addActionListener(actionAdmin);
        usersControl.addActionListener(actionAdmin);
        backUpBase2.addActionListener(actionAdmin);
        properties.addActionListener(actionAdmin);

        about.addActionListener(actionInfo);
        edu.addActionListener(actionInfo);

        mFile.add(mfQuit);

        mReport.add(smenaList);
        mReport.add(crossTable);
        mReport.add(report3);

        mLib.add(directPerson);
        mLib.add(directFamily);
        mLib.add(directSmena);
        mLib.add(directHousing);
        mLib.add(directCitys);
        mLib.add(directDistricts);
        mLib.add(directCognates);
        mLib.add(directDistrictsPassport);

        addFeatures.add(features1);
        addFeatures.add(features2);
        addFeatures.add(features3);
        addFeatures.add(features4);
        addFeatures.addSeparator();

        admin.add(reCountSmenas);
        admin.add(usersControl);
        admin.add(backUpBase2);
        admin.add(properties);

        info.add(edu);
        info.addSeparator();
        info.add(about);


        add(mFile);
        add(mReport);
        add(mLib);
        add(addFeatures);
        add(admin);
        add(info);
    }

    public void setEnableMenu() {
        /**
         * необходимо проверить минимальный уровень доступа
         */

        smenaList.setEnabled(permissionUser(WORK_USER_PERMISSION));
        crossTable.setEnabled(permissionUser(WORK_USER_PERMISSION));
        report3.setEnabled(permissionUser(WORK_USER_PERMISSION));

        directPerson.setEnabled(false);
        directFamily.setEnabled(permissionSuperUser(WORK_USER_PERMISSION));
        directSmena.setEnabled(permissionSuperUser(WORK_USER_PERMISSION));
        directCitys.setEnabled(permissionSuperUser(WORK_USER_PERMISSION));
        directHousing.setEnabled(false);
        directDistricts.setEnabled(permissionSuperUser(WORK_USER_PERMISSION));
        directCognates.setEnabled(permissionSuperUser(WORK_USER_PERMISSION));
        directDistrictsPassport.setEnabled(permissionSuperUser(WORK_USER_PERMISSION));

        features1.setEnabled(permissionSuperUser(WORK_USER_PERMISSION));
        features2.setEnabled(permissionSuperUser(WORK_USER_PERMISSION));
        features3.setEnabled(permissionSuperUser(WORK_USER_PERMISSION));
        features4.setEnabled(permissionSuperUser(WORK_USER_PERMISSION));

        reCountSmenas.setEnabled(permissionSuperUser(WORK_USER_PERMISSION));
        usersControl.setEnabled(permissionAdmin(WORK_USER_PERMISSION));
        backUpBase2.setEnabled(permissionAdmin(WORK_USER_PERMISSION));
        properties.setEnabled(permissionAdmin(WORK_USER_PERMISSION));

        edu.setEnabled(true);
        about.setEnabled(true);
    }

    private class ActionDirectory implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String str = e.getActionCommand();
            switch (str) {
                case "person":
                    VListPerson vListPerson = new VListPerson();
                    vListPerson.setModal(true);
                    vListPerson.setVisible(true);
                    break;
                case "family":
                    VListFamily vListFamily = new VListFamily();
                    vListFamily.setModal(true);
                    vListFamily.setVisible(true);
                    break;
                case "smena":
                    VListSmena vListSmena = new VListSmena();
                    vListSmena.setModal(true);
                    vListSmena.setVisible(true);
                    break;
                case "city":
                    VListCity vListCity = new VListCity();
                    vListCity.setModal(true);
                    vListCity.setVisible(true);
                    break;
                case "district":
                    VListDistrict vListDistrict = new VListDistrict();
                    vListDistrict.setModal(true);
                    vListDistrict.setVisible(true);
                    break;
                case "cognate":
                    VListCognate vListCognate = new VListCognate();
                    vListCognate.setModal(true);
                    vListCognate.setVisible(true);
                    break;
                case "districtsPassport":
                    VListDistrictPassport vListDistrictPassport = new VListDistrictPassport();
                    vListDistrictPassport.setModal(true);
                    vListDistrictPassport.setVisible(true);
                    break;
            }
        }
    }

    private class ActionReports implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String str = e.getActionCommand();
            switch (str) {
                case "smenaList":
                    SavePersonListFromSmena savePersonListFromSmena = new SavePersonListFromSmena();
                    savePersonListFromSmena.setModal(true);
                    savePersonListFromSmena.setVisible(true);
                    break;
                case "crossTable":
                    ReportCrossList reportCrossList = new ReportCrossList();
                    reportCrossList.setModal(true);
                    reportCrossList.setVisible(true);
                    break;
                case "globalReport":
                    ReportGlobal reportGlobal = new ReportGlobal();
                    reportGlobal.setModal(true);
                    reportGlobal.setVisible(true);
                    break;
            }
        }
    }

    private class ActionInfo implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String str = e.getActionCommand();
            switch (str) {
                case "about":
                    new About();
                    break;
                case "edu":
                    Desktop desktop = null;
                    if (Desktop.isDesktopSupported()) {
                        desktop = Desktop.getDesktop();
                    }
                    try {
                        desktop.open(new File("./", "read_me.pdf"));
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                    }
                    break;
            }
        }
    }

    private class ActionAdmin implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String str = e.getActionCommand();
            switch (str) {
                case "userControl":
                    VListUser vListUser = new VListUser();
                    vListUser.setModal(true);
                    vListUser.setVisible(true);
                    break;
                case "backUpBase":
                    //                    new JpaBackup();
                    new HibernateBackUpBase();
                    break;
                case "properties":
                    break;
                case "reCountSmenas":
                    int i = Factory.getSmenaDAO().reCountSmena();
                    if (i == 0)
                        JOptionPane.showMessageDialog(null, "Не могу посчитать смены...", "Что то пошло не так...", JOptionPane.ERROR_MESSAGE);
                    else if (i == 1)
                        JOptionPane.showMessageDialog(null, "Смены пересчитаны", "Завершено.", JOptionPane.INFORMATION_MESSAGE);
                    break;
            }
        }
    }
}

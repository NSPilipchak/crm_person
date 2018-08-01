package properties;

import javax.swing.*;
import java.awt.*;

import static properties.Strings.WORK_USER_PERMISSION;

/**
 * Created by hammer on 26.05.2017.
 */
public class Properies {

    public static String currentVersion = "0.0.2.8";
    public static String currentVersionDate = "03.07.2018";

    /**
     * Принудительное обграничение выгрузки отчетов.
     * Есть нюансы с оплатой за проделанную работу.
     * Ненадежный заказчик.
     * kolich.x10@gmail.com
     */
    public static boolean CROP_REPORT = false;

    public static String MAX_RESULT = (String) AppSettings.get("Setting_MainTable_MaxResult");
    public static String MAX_ROW = (String) AppSettings.get("Setting_MainTable_MaxRow");
    public static boolean MAX_ROW_BASE = Integer.parseInt((String) AppSettings.get("MaxRowBase")) == 1;
    public static boolean STATUS_BAR = Integer.parseInt((String) AppSettings.get("StatusBar")) == 1;
    public static int STATUS_BAR_UPDATE = Integer.parseInt((String) AppSettings.get("StatusBarUpdate"));
    public static int STATUS_BASE_UPDATE = Integer.parseInt((String) AppSettings.get("StatusBaseUpdate"));
    public static boolean SEARCH_INTO_BASE = Integer.parseInt((String) AppSettings.get("SearchInBase")) == 1;
    public static boolean VIEW_SMENA_IN_FILTER = Integer.parseInt((String) AppSettings.get("ViewSmenaInFilter")) == 1;
    public static int VIEW_YEAR_IN_FILTER = Integer.parseInt((String) AppSettings.get("ViewYearInFilter"));

    public static String BUS_VOLUME = AppSettings.get("Bus").toString();
    public static String DB_HDM2DDL = AppSettings.get("DB_hbm2ddl").toString();
    public static String DB_URL = AppSettings.get("DBurl").toString()
            + ":3306/"
            + AppSettings.get("DBname").toString()
            + "?useSSL=false";
    public static String DB_LOGIN = "login";

    public static String DB_PASS = "pass";

    public static boolean permission() {
        if (WORK_USER_PERMISSION == 3) {
            JOptionPane.showMessageDialog(null, "Для Вашего пользователя" +
                    " данное действие недоступно", "Доступ закрыт", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
        return true;
    }

    public static boolean permissionAdmin(int i) {
        System.out.println(i);
        if (i == 1) {
            return true;
        }
        return false;
    }

    public static boolean permissionSuperUser(int i) {
        System.out.println(i);
        if (i == 1 || i == 2)
            return true;
        return false;
    }

    public static boolean permissionUser(int i) {
        System.out.println(i);
        if (i == 1 || i == 2 || i == 0) {
            return true;
        }
        return false;
    }
}

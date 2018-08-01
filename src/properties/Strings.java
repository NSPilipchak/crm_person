package properties;


import java.awt.*;

/**
 * Created by hammer on 26.05.2017.
 */
public class Strings {

    public static String WORK_USER_NAME = null;
    public static int WORK_USER_PERMISSION;

    public static String[] STATUS_PERSON = {
            "Удаленная",
            "Активная",
            "Черный список",
            "VIP персона",
            "RIP"
    };

    //насройки диалоговых окон
    public static Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    public static int DG_WIDH = 525;
    public static int DG_HEIGH = 600;

    public static final String[] ITEM_YEAR = {
            "---",
            "2015",
            "2016",
            "2017",
            "2018",
            "2019",
            "2020",
            "2021"
    };

    public static final String[] USER_ROLES = {
            "Пользователь",
            "Администратор",
            "Супер пользователь",
            "Только просмотр"
    };
    public static final String[] USER_STATUS = {
            "Доступ закрыт",
            "Доступ разрешен"
    };
}

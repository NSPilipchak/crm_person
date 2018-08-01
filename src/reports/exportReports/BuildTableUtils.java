package reports.exportReports;

import java.time.LocalDate;
import java.time.Period;

class BuildTableUtils {

    static boolean birthday(String startDate, String endDate, String bDate) {
        LocalDate dateStart = getData(startDate);
        LocalDate dateEnd = getData(endDate);
        LocalDate dateEqualse = getData(bDate);
        return eqalseDateFromPeriod(dateStart, dateEnd, dateEqualse, false);
    }

    private static LocalDate getData(String string) {
        String[] arr = string.split("\\.");
        int day = Integer.parseInt(arr[0]);
        int month = Integer.parseInt(arr[1]);
        int year = Integer.parseInt(arr[2]);
        LocalDate date = LocalDate.of(year, month, day);
        return date;
    }

    private static boolean eqalseDateFromPeriod(LocalDate dateStart, LocalDate dateEnd, LocalDate dateEqualse, boolean checkYear) {
        while (!eqalseDate(dateStart, dateEnd)) {
            if (checkYear) {
                if (dateStart.getDayOfMonth() == dateEqualse.getDayOfMonth() &&
                        dateStart.getMonth() == dateEqualse.getMonth() &&
                        dateStart.getYear() == dateEqualse.getYear())
                    return true;
            } else {
                if (dateStart.getDayOfMonth() == dateEqualse.getDayOfMonth() &&
                        dateStart.getMonth() == dateEqualse.getMonth())
                    return true;
            }
            dateStart = dateStart.plusDays(1);
        }
        return false;
    }

    private static boolean eqalseDate(LocalDate date1, LocalDate date2) {
        if (date1.getDayOfMonth() == date2.getDayOfMonth() &&
                date1.getMonth() == date2.getMonth())
            return true;
        return false;
    }
}

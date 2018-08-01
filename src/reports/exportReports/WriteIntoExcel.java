package reports.exportReports;

import blogic.entity.City;
import blogic.entity.District;
import blogic.entity.Person;
import blogic.entity.Smena;
import dal.Factory;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;

import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;

import static properties.Properies.BUS_VOLUME;
import static properties.Strings.*;

/**
 * Created by hammer on 16.06.2017.
 */
class WriteIntoExcel {

    private ArrayList<Person> pp;
    private Workbook book;
    private Sheet sheet;
    private Row row;
    private int currentRow;
    private ArrayList<Smena> smena;
    private ArrayList status;
    private String[] ITEM_CITY;
    private String[] ITEM_DISTRICT;

    WriteIntoExcel() {
    }

    private String arrayToString(ArrayList array) {
        String sb = "";
        if (array != null && array.size() > 0) {
            for (Object str : array) {
                sb += (sb.length() > 0 ? "," + str : str);
            }
        }
        return sb;
    }

    private String arrayStatusToStringName(ArrayList array) {
        String sb = "";
        if (array != null && array.size() > 0) {
            for (Object str : array) {
                sb += (sb.length() > 0 ? ",\n " + STATUS_PERSON[(int) str] : STATUS_PERSON[(int) str]);
            }
        }
        sb += ".";
        return sb;
    }

    private String arraySmenaNameToString(ArrayList<Smena> array) {
        String sb = "";
        if (array != null && array.size() > 0) {
            for (Smena str : array) {
                sb += (sb.length() > 0 ?
                        ",\n " + str.getId() :
                        str.getId());
            }
        }
        sb += ".";
        return sb;
    }

    private String arraySmenaNumberToString(ArrayList<Smena> array) {
        String sb = "";
        if (array != null && array.size() > 0) {
            for (Smena str : array) {
                sb += (sb.length() > 0 ? ", " + str.getNumber() : str.getNumber());
            }
        }
        return sb;
    }

    private void init(ArrayList<Person> pp, ArrayList<Smena> smena, ArrayList status) {
        currentRow = 0;
        this.pp = pp;
        this.smena = smena;
        this.status = status;
        book = new HSSFWorkbook();
        sheet = book.createSheet("Список " + arraySmenaNumberToString(smena));
        row = sheet.createRow(currentRow);
        ITEM_CITY = getCity();
        for (int i = 0; i < ITEM_CITY.length ; i++) {
            System.out.println(ITEM_CITY[i]);
        }
        ITEM_DISTRICT = getDistrict();
        for (int i = 0; i < ITEM_DISTRICT.length ; i++) {
            System.out.println(ITEM_DISTRICT[i]);
        }
    }

    private String[] getCity() {
        ArrayList<City> citiess = Factory.getLibraryDAO().getCityList();
        String[] ret = new String[citiess.size()];
        for (int i = 0; i < citiess.size(); i++) {
            ret[i] = citiess.get(i).getCity();
        }
        return ret;
    }

    private String[] getDistrict() {
        ArrayList<District> citiess = Factory.getLibraryDAO().getDistrictList();
        String[] ret = new String[citiess.size()];
        for (int i = 0; i < citiess.size(); i++) {
            ret[i] = citiess.get(i).getDistrict();
        }
        return ret;
    }

    @SuppressWarnings("deprecation")
    public void writeIntoExcel(File file, ArrayList<Person> pp,
                               ArrayList<Smena> smena, ArrayList status, boolean boolBus) throws IOException {

        init(pp, smena, status);
        autor();
        options();
        headDoc();
        addPeriodDoc();
        if (boolBus) {
            writeIntoExcelWhishBus();
        } else {
            headTable();
            loadTable();
        }
        sizeColumn();
        writeFile(file);
    }

    private void writeIntoExcelWhishBus() {
        int start = 0;
        int currentEnd = 0;
        int end = pp.size();

        String[] listBus = BUS_VOLUME.split(",");

        for (int i = 0; i < listBus.length; i++) {
            currentRow++;
            row = sheet.createRow(currentRow);
            row.createCell(0).setCellValue("Автобус №" + (i + 1) + " кiлькість місць " + listBus[i] + "ч.");
            sheet.addMergedRegion(new CellRangeAddress(
                    currentRow, //first row (0-based)
                    currentRow, //last row (0-based)
                    0, //first column (0-based)
                    10 //last column (0-based)
            ));
            HSSFFont font = (HSSFFont) book.createFont();
            HSSFCellStyle style = (HSSFCellStyle) book.createCellStyle();
            font.setFontHeightInPoints((short) 12);
            font.setBold(true);
            style.setFont(font);
            style.setAlignment(HorizontalAlignment.CENTER);
            setStyle(row, style);

            currentRow++;
            headTable();
            currentEnd = currentEnd + Integer.parseInt(listBus[i]);
            if (currentEnd > end)
                currentEnd = end;

            loadTable(start, currentEnd);
            start = currentEnd;
        }
    }


    private void options() {
        currentRow++;
        row = sheet.createRow(currentRow);
        row.createCell(0).setCellValue("ID Змiни:");
        row.createCell(1).setCellValue(arraySmenaNameToString(smena));
        currentRow++;
        row = sheet.createRow(currentRow);
        row.createCell(0).setCellValue("Статус:");
        row.createCell(1).setCellValue(arrayStatusToStringName(status));
        currentRow++;
    }

    private void addPeriodDoc() {
        row = sheet.createRow(currentRow);
        String str = "";
        for (Smena s : smena) {
            str += (str.length() > 0 ?
                    "; " + s.getName() + " з " + s.getDateStart() + " по " + s.getDateEnd() :
                    s.getName() + " з " + s.getDateStart() + " по " + s.getDateEnd());
        }

        row.createCell(0).setCellValue(str);
        sheet.addMergedRegion(new CellRangeAddress(
                currentRow, //first row (0-based)
                currentRow, //last row (0-based)
                0, //first column (0-based)
                6 //last column (0-based)
        ));
        HSSFFont font = (HSSFFont) book.createFont();
        HSSFCellStyle style = (HSSFCellStyle) book.createCellStyle();
        font.setFontHeightInPoints((short) 14);
        font.setBold(false);
        font.setItalic(true);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        setStyle(row, style);
        currentRow++;
    }

    private void writeFile(File file) {
        int i = 0;
        try (FileOutputStream out = new FileOutputStream(file)) {
            book.write(out);
            book.close();
        } catch (IOException e) {
            e.printStackTrace();
            i++;
            JOptionPane.showMessageDialog(null, "Не удалось сохранить файл", "Упс, что то не так...", JOptionPane.ERROR_MESSAGE);
        }
        if (i == 0)
            JOptionPane.showMessageDialog(null, file, "Файл сохранен", JOptionPane.INFORMATION_MESSAGE);
    }

    private void sizeColumn() {
        for (int i = 1; i < 9; i++) {
            sheet.autoSizeColumn(i);
        }
    }

    private void loadRow(int i, HSSFCellStyle style) {
        row = sheet.createRow(currentRow++);
        row.createCell(0).setCellValue(i + 1);

        row.createCell(1).setCellValue(pp.get(i).getId() + "-" + pp.get(i).getCode());

        row.createCell(2).setCellValue(pp.get(i).getlName() + " " +
                pp.get(i).getfName() + " " +
                pp.get(i).getmName());

        for (Smena s : smena) {
            if (BuildTableUtils.birthday(s.getDateStart(), s.getDateEnd(), pp.get(i).getbDate())) {
                row.createCell(3).setCellValue(pp.get(i).getbDate() + " (Д.Р.)");
                break;
            } else {
                row.createCell(3).setCellValue(pp.get(i).getbDate());
            }
        }

        row.createCell(4).setCellValue(pp.get(i).getInn());

        row.createCell(5).setCellValue(pp.get(i).getPersonData().getsPassport());

        String str = ITEM_CITY[pp.get(i).getPersonData().getCity()] + ", "
                + pp.get(i).getPersonData().getStreet() + ", буд. " + pp.get(i).getPersonData().getHouse();
        if (pp.get(i).getPersonData().getCorp() != null)
            if (!pp.get(i).getPersonData().getCorp().equals("") || pp.get(i).getPersonData().getCorp().length() != 0)
                str += ", корп. " + pp.get(i).getPersonData().getCorp();
        if (pp.get(i).getPersonData().getFlat() != null)
            if (!pp.get(i).getPersonData().getFlat().equals("") || pp.get(i).getPersonData().getFlat().length() != 0)
                str += ", кв. " + pp.get(i).getPersonData().getFlat();
        row.createCell(6).setCellValue(str);

        str = ITEM_DISTRICT[pp.get(i).getPersonData().getDistrict()];
        row.createCell(7).setCellValue(str);

        row.createCell(8).setCellValue(pp.get(i).getfPhone());

        row.createCell(9).setCellValue(STATUS_PERSON[pp.get(i).getStatus()]);

        row.createCell(10).setCellValue(pp.get(i).getComment());

        setStyle(row, style);
    }

    private void loadTable() {
        HSSFCellStyle style = (HSSFCellStyle) book.createCellStyle();
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());

        for (int i = 0; i < pp.size(); i++) {
            loadRow(i, style);
        }
    }

    private void loadTable(int start, int end) {
        HSSFCellStyle style = (HSSFCellStyle) book.createCellStyle();
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());

        for (int i = start; i < end; i++) {
            loadRow(i, style);
        }
    }

    private void headDoc() {
        currentRow++;
        row = sheet.createRow(currentRow);
        row.createCell(0).setCellValue("РЕЄСТР УЧАСНИКІВ ");// + arraySmenaNumberToString(smena)
//                + ITEM_YEAR[smena.get(0).getYear()] + " року.");
        sheet.addMergedRegion(new CellRangeAddress(
                currentRow, //first row (0-based)
                currentRow, //last row (0-based)
                0, //first column (0-based)
                6 //last column (0-based)
        ));
        HSSFFont font = (HSSFFont) book.createFont();
        HSSFCellStyle style = (HSSFCellStyle) book.createCellStyle();
        font.setFontHeightInPoints((short) 15);
        font.setBold(true);
        font.setItalic(true);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        setStyle(row, style);
        currentRow++;
    }

    @Deprecated
    private void headTable() {
        // создаем шрифт
        HSSFFont font = (HSSFFont) book.createFont();
        // создаем стиль для ячейки
        HSSFCellStyle style = (HSSFCellStyle) book.createCellStyle();
        // указываем, что хотим его видеть жирным
        font.setBold(true);
        // и применяем к этому стилю жирный шрифт
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(XSSFCellStyle.VERTICAL_JUSTIFY);
        style.setFont(font);

        row = sheet.createRow(currentRow);
        row.createCell(0).setCellValue("№ п/п");
        row.createCell(1).setCellValue("Код");
        row.createCell(2).setCellValue("Прізвище, ім'я, по батькові \n" +
                "одержувача соціальних виплат");
        row.createCell(3).setCellValue("Дата народження");
        row.createCell(4).setCellValue("Ідентифікаційний код\n" +
                " одержувача соціальних виплат");
        row.createCell(5).setCellValue("Серія та номер паспорта\n" +
                " одержувача соціальних виплат");
        row.createCell(6).setCellValue("Адреса реєстрації\n" +
                " одержувача соціальних виплат");
        row.createCell(7).setCellValue("Район прописки");
        row.createCell(8).setCellValue("Телефон");
        row.createCell(9).setCellValue("Статус");
        row.createCell(10).setCellValue("Комментарий");

        setStyle(row, style);
        currentRow++;
    }

    private void autor() {
        HSSFFont font = (HSSFFont) book.createFont();
        // создаем стиль для ячейки
        HSSFCellStyle style = (HSSFCellStyle) book.createCellStyle();
        font.setFontHeightInPoints((short) 8);
        font.setItalic(true);
//        font.set;
        style.setFont(font);
        SimpleDateFormat formatDate = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        row.createCell(0).setCellValue("Створено " + formatDate.format(new java.util.Date()) + " користувачем " + WORK_USER_NAME + ".");
        setStyle(row, style);
        currentRow++;
    }

    private void setStyle(Row row, HSSFCellStyle style) {
        for (int i = 0; i < row.getPhysicalNumberOfCells(); i++) {
            // применяем созданный выше стиль к каждой ячейке
            row.getCell(i).setCellStyle(style);
        }
    }

    private boolean birthday(String string) {

        for (int i = 0; i < smena.size(); i++) {
            LocalDate startDate = getData(smena.get(i).getDateStart());
            LocalDate endDate = getData(smena.get(i).getDateEnd());


        }
        return false;
    }

    private LocalDate getData(String string) {
        String[] arr = string.split(".");

        return null;
    }

}

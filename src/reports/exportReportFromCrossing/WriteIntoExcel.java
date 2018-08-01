package reports.exportReportFromCrossing;

import blogic.entity.City;
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

    WriteIntoExcel() {
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
                        ",\n " + str.getName() + " " + ITEM_YEAR[str.getYear()] :
                        str.getName() + " " + ITEM_YEAR[str.getYear()]);
            }
        }
        sb += ".";
        return sb;
    }

    private void init(ArrayList<Person> pp, ArrayList<Smena> smena, ArrayList status) {
        currentRow = 0;
        this.pp = pp;
        this.smena = smena;
        this.status = status;
        book = new HSSFWorkbook();
        sheet = book.createSheet("CrossTable");
        row = sheet.createRow(currentRow);
        ITEM_CITY = getCity();
    }

    private String[] getCity() {
        ArrayList<City> citiess = Factory.getLibraryDAO().getCityList();
        String[] ret = new String[citiess.size()];
        for (int i = 0; i < citiess.size(); i++) {
            ret[i] = citiess.get(i).getCity();
        }
        return ret;
    }

    @SuppressWarnings("deprecation")
    public void writeIntoExcel(File file, ArrayList<Person> pp,
                               ArrayList<Smena> smena, ArrayList status) throws IOException {
        init(pp, smena, status);
        autor();
        options();
        headDoc();
        addPeriodDoc();
        headTable();
        loadTable();
        sizeColumn();
        writeFile(file);
    }

    private void options() {
        currentRow++;
        row = sheet.createRow(currentRow);
        row.createCell(0).setCellValue("Смена:");
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
            str += "заезд №" + s.getNumber() + " с " + s.getDateStart() + " по " + s.getDateEnd() + "; ";
        }

        row.createCell(0).setCellValue(str);
        sheet.addMergedRegion(new CellRangeAddress(
                currentRow, //first row (0-based)
                currentRow, //last row (0-based)
                0, //first column (0-based)
                14 //last column (0-based)
        ));
        HSSFFont font = (HSSFFont) book.createFont();
        HSSFCellStyle style = (HSSFCellStyle) book.createCellStyle();
        font.setFontHeightInPoints((short) 12);
        font.setBold(false);
        font.setItalic(true);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.JUSTIFY);
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
        for (int i = 1; i < 15; i++) {
            sheet.autoSizeColumn(i);
        }
    }

    private void loadRow(int i, HSSFCellStyle style) {
        row = sheet.createRow(currentRow++);
        // "№"
        row.createCell(0).setCellValue(i + 1);
        // "Проект",
        row.createCell(1).setCellValue("Наименование проекта");
        // "Дата регистрации",
        row.createCell(2).setCellValue(pp.get(i).getCreateInfo());
        // "ИНН",
        row.createCell(3).setCellValue(pp.get(i).getInn());
        // "Паспорт",
        row.createCell(4).setCellValue(pp.get(i).getPersonData().getsPassport());
        // "Дата получения услуги",
        String str = "";
        for (Smena s : pp.get(i).getSmenasList()) {
            for (Smena ss : smena) {
                if (s.getId() == ss.getId()) {
                    if (str.length() > 2) {
                        str += ", ";
                    }
                    if (s.getDateStart().substring(0, 5).equals("01.01") | s.getDateStart().substring(0, 5).equals("00.00"))
                        str += "резерв " + s.getDateStart().substring(6, s.getDateStart().length());
                    else
                        str += s.getDateStart();
                }
            }
        }
        row.createCell(5).setCellValue(str);
        // "ФИО",
        row.createCell(6).setCellValue(pp.get(i).getlName() + " "
                + pp.get(i).getfName() + " "
                + pp.get(i).getmName());
        // "Дата рождения",
        row.createCell(7).setCellValue(pp.get(i).getbDate());
        // "Адрес",
        row.createCell(8).setCellValue(
//                ITEM_CITY[pp.get(i).getPersonData().getCity()] + ", " +
                pp.get(i).getPersonData().getStreet());
        // "Дом",
        row.createCell(9).setCellValue(pp.get(i).getPersonData().getHouse());
        // "Корпус",
        row.createCell(10).setCellValue(pp.get(i).getPersonData().getCorp());
        // "Квартира",
        row.createCell(11).setCellValue(pp.get(i).getPersonData().getFlat());
        // "Телефон мобильный",
        row.createCell(12).setCellValue(pp.get(i).getfPhone());
        // "Альтернативный мобильный",
        row.createCell(13).setCellValue(pp.get(i).getlPhone());
        // "Телефон домашний"
        row.createCell(14).setCellValue(pp.get(i).getHomePhone());

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

    private void headDoc() {
        currentRow++;
        row = sheet.createRow(currentRow);
        row.createCell(0).setCellValue("Список отдыхающих програмы «Наименование проекта» "
                + ITEM_YEAR[smena.get(0).getYear()] + " года.");
        sheet.addMergedRegion(new CellRangeAddress(
                currentRow, //first row (0-based)
                currentRow, //last row (0-based)
                0, //first column (0-based)
                14 //last column (0-based)
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
        row.createCell(1).setCellValue("Проект");
        row.createCell(2).setCellValue("Дата регистрации");
        row.createCell(3).setCellValue("ИНН");
        row.createCell(4).setCellValue("Паспорт");
        row.createCell(5).setCellValue("Дата получения услуги");
        row.createCell(6).setCellValue("ФИО");
        row.createCell(7).setCellValue("Дата рождения");
        row.createCell(8).setCellValue("Адрес");
        row.createCell(9).setCellValue("Дом");
        row.createCell(10).setCellValue("Корпус");
        row.createCell(11).setCellValue("Квартира");
        row.createCell(12).setCellValue("Телефон мобильный");
        row.createCell(13).setCellValue("Альтернативный мобильный");
        row.createCell(14).setCellValue("Телефон домашний");

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
        row.createCell(0).setCellValue("Создано " + formatDate.format(new java.util.Date()) + " пользователем " + WORK_USER_NAME + ".");
        setStyle(row, style);
        currentRow++;
    }

    private void setStyle(Row row, HSSFCellStyle style) {
        for (int i = 0; i < row.getPhysicalNumberOfCells(); i++) {
            // применяем созданный выше стиль к каждой ячейке
            row.getCell(i).setCellStyle(style);
        }
    }
}

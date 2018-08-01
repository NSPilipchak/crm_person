package reports.exportGlobalReport;

import blogic.entity.*;
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
import java.util.ArrayList;
import java.util.Set;

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
    private String[] ITEM_VERIF = new String[]{"Не проверенно", "Проверенно"};

    private int[] rowSeason = new int[ITEM_YEAR.length];
    private int[] rowFamily = new int[3];
    private int lastColumn = 0;

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
        ITEM_DISTRICT = getDistrict();
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
        ArrayList<District> districts = Factory.getLibraryDAO().getDistrictList();
        String[] ret = new String[districts.size()];
        for (int i = 0; i < districts.size(); i++) {
            ret[i] = districts.get(i).getDistrict();
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
        for (int i = 1; i < lastColumn; i++) {
            sheet.autoSizeColumn(i);
        }
    }

    private void loadRowPerson(int i, HSSFCellStyle style) {
        row = sheet.createRow(currentRow++);

        int columnNumber = 0;
        //`№`,
        row.createCell(columnNumber++).setCellValue(i + 1);
        //`id`,
        row.createCell(columnNumber++).setCellValue(pp.get(i).getId());
        // `Имя`,
        row.createCell(columnNumber++).setCellValue(pp.get(i).getfName());
        // `Отчество`,
        row.createCell(columnNumber++).setCellValue(pp.get(i).getmName());
        // `Фамилия`,
        row.createCell(columnNumber++).setCellValue(pp.get(i).getlName());
        // `День рождения`,
        row.createCell(columnNumber++).setCellValue(pp.get(i).getbDate());
        // `идентификатор`,
        row.createCell(columnNumber++).setCellValue(pp.get(i).getCode());
        // `комментарий`,
        row.createCell(columnNumber++).setCellValue(pp.get(i).getComment());
        // `дата создания карточки`,
        row.createCell(columnNumber++).setCellValue(pp.get(i).getCreateInfo());
        // `автор`,
        row.createCell(columnNumber++).setCellValue(pp.get(i).getCreateUser());
        // `е-почта`,
        row.createCell(columnNumber++).setCellValue(pp.get(i).getEmail());
        // `осн.телефон`,
        row.createCell(columnNumber++).setCellValue(pp.get(i).getfPhone());
        // `доп.телефон`,
        row.createCell(columnNumber++).setCellValue(pp.get(i).getlPhone());
        // `дом.телефон`,
        row.createCell(columnNumber++).setCellValue(pp.get(i).getHomePhone());
        // `Инн`,
        row.createCell(columnNumber++).setCellValue(pp.get(i).getInn());
        // `статус`,
        row.createCell(columnNumber++).setCellValue(STATUS_PERSON[pp.get(i).getStatus()]);
        // `город`,
        row.createCell(columnNumber++).setCellValue(ITEM_CITY[pp.get(i).getPersonData().getCity()]);
        // `район города`,
        row.createCell(columnNumber++).setCellValue(ITEM_DISTRICT[pp.get(i).getPersonData().getDistrict()]);
        // `улица`,
        row.createCell(columnNumber++).setCellValue(pp.get(i).getPersonData().getStreet());
        // `дом`,
        row.createCell(columnNumber++).setCellValue(pp.get(i).getPersonData().getHouse());
        // `корпус`,
        row.createCell(columnNumber++).setCellValue(pp.get(i).getPersonData().getCorp());
        // `квартира`,
        row.createCell(columnNumber++).setCellValue(pp.get(i).getPersonData().getFlat());
        // `адрес из Облака`,
        row.createCell(columnNumber++).setCellValue(pp.get(i).getPersonData().getOldAdres());
        // `пенсионное удостоверение`,
        row.createCell(columnNumber++).setCellValue(pp.get(i).getPersonData().getPensionNum());
        // `серия паспорта`,
        row.createCell(columnNumber++).setCellValue(pp.get(i).getPersonData().getsPassport());
        // `Кем выдан паспорт`,
        row.createCell(columnNumber++).setCellValue(pp.get(i).getPersonData().getaPassport());
        // `дата выдачи паспорта`,
        row.createCell(columnNumber++).setCellValue(pp.get(i).getPersonData().getdPassport());
        // `сверка документа`
        row.createCell(columnNumber++).setCellValue(ITEM_VERIF[pp.get(i).getPersonData().getVerificationDocum()]);

//        setStyle(row, style);
    }

    private void loadRowSmena(int i, HSSFCellStyle style) {
        if (pp.get(i).getSmenasList() != null) {
            System.out.println(pp.get(i).getSmenasList().toString());
            Set<Smena> smenas = pp.get(i).getSmenasList();
            for (Smena s : smenas) {
                int column = rowSeason[s.getYear()];
                //`id`,
                row.createCell(column++).setCellValue(s.getId());
                // `Имя`,
                row.createCell(column++).setCellValue(s.getName());
                // `год`,
                row.createCell(column++).setCellValue(ITEM_YEAR[s.getYear()]);
                // `Номер недели`,
                row.createCell(column++).setCellValue(s.getNumber());
                // `Дата старта`,
                row.createCell(column++).setCellValue(s.getDateStart());
                // `Дата окончания`,
                row.createCell(column++).setCellValue(s.getDateEnd());
                // `Кол-во мест`,
                row.createCell(column++).setCellValue(s.getPlaces());
                // `Занято мест`
                row.createCell(column++).setCellValue(s.getBusyPlaces());
                // `Статус`,
                row.createCell(column++).setCellValue(s.getStatus());
                // `Коммент`,
                row.createCell(column++).setCellValue(s.getComment());
//                setStyle(row, style);
            }
        }
    }

    private void loadRowFamily(int i, HSSFCellStyle style) {
        if (pp.get(i).getFamilyList() != null) {
            Set<Family> families = pp.get(i).getFamilyList();
            int num = 0;
            for (Family f : families) {
                num = num > 2 ? 2 : num;
                int column = rowFamily[num];
                // `id`,
                row.createCell(column++).setCellValue(f.getId());
                // `ФИО`,
                row.createCell(column++).setCellValue(f.getName());
                // `Родственная связь`,
                row.createCell(column++).setCellValue(f.getCognate());
                // `День рождения`,
                row.createCell(column++).setCellValue(f.getbDate());
                // `Осн.телефон`,
                row.createCell(column++).setCellValue(f.getfPhone());
                // `доп.телефон`,
                row.createCell(column++).setCellValue(f.getlPhone());
                // `е-почта`,
                row.createCell(column++).setCellValue(f.getEmail());
                // `комментарий`,
                row.createCell(column++).setCellValue(f.getComment());
                // `статус`
                row.createCell(column++).setCellValue(f.getStatus());
                num++;
//                setStyle(row, style);
            }
        }
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
            loadRowPerson(i, style);
            loadRowFamily(i, style);
            loadRowSmena(i, style);
        }
    }

    private void headDoc() {
        currentRow++;
        row = sheet.createRow(currentRow);
        row.createCell(0).setCellValue("Выгрузка данных из базы");
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
        Row rowHead = sheet.getRow(currentRow - 1);

        int columnNumber = 0;
        //`№`,
        row.createCell(columnNumber++).setCellValue("№ п/п");
        //`id`,
        row.createCell(columnNumber++).setCellValue("ID person");
        // `Имя`,
        row.createCell(columnNumber++).setCellValue("Имя");
        // `Отчество`,
        row.createCell(columnNumber++).setCellValue("Отчество");
        // `Фамилия`,
        row.createCell(columnNumber++).setCellValue("Фамилия");
        // `День рождения`,
        row.createCell(columnNumber++).setCellValue("День рождения");
        // `идентификатор`,
        row.createCell(columnNumber++).setCellValue("Идентификатор");
        // `комментарий`,
        row.createCell(columnNumber++).setCellValue("Комментарий");
        // `дата создания карточки`,
        row.createCell(columnNumber++).setCellValue("Дата создания карточки");
        // `автор`,
        row.createCell(columnNumber++).setCellValue("Автор");
        // `е-почта`,
        row.createCell(columnNumber++).setCellValue("E-mail");
        // `осн.телефон`,
        row.createCell(columnNumber++).setCellValue("осн.телефон");
        // `доп.телефон`,
        row.createCell(columnNumber++).setCellValue("доп.телефон");
        // `дом.телефон`,
        row.createCell(columnNumber++).setCellValue("дом.телефон");
        // `Инн`,
        row.createCell(columnNumber++).setCellValue("ИНН");
        // `статус`,
        row.createCell(columnNumber++).setCellValue("Статус");
        // `город`,
        row.createCell(columnNumber++).setCellValue("Город");
        // `район города`,
        row.createCell(columnNumber++).setCellValue("Район города");
        // `улица`,
        row.createCell(columnNumber++).setCellValue("Улица");
        // `дом`,
        row.createCell(columnNumber++).setCellValue("Дом");
        // `корпус`,
        row.createCell(columnNumber++).setCellValue("Корпус");
        // `квартира`,
        row.createCell(columnNumber++).setCellValue("Квартира");
        // `адрес из Облака`,
        row.createCell(columnNumber++).setCellValue("Адрес из Облака");
        // `пенсионное удостоверение`,
        row.createCell(columnNumber++).setCellValue("Пенсионное удостоверение");
        // `серия паспорта`,
        row.createCell(columnNumber++).setCellValue("Серия паспорта");
        // `Кем выдан паспорт`,
        row.createCell(columnNumber++).setCellValue("Кем выдан паспорт");
        // `дата выдачи паспорта`,
        row.createCell(columnNumber++).setCellValue("Дата выдачи паспорта");
        // `сверка документа`
        row.createCell(columnNumber++).setCellValue("Сверка документа");

        //смены по годам
        int firstCol, lastCol;
        for (int i = 0; i < ITEM_YEAR.length; i++) {
            rowSeason[i] = columnNumber;
            firstCol = columnNumber;
            rowHead.createCell(firstCol).setCellValue(ITEM_YEAR[i]);
            //`id`,
            row.createCell(columnNumber++).setCellValue("ID smena");
            // `Имя`,
            row.createCell(columnNumber++).setCellValue("Имя");
            // `год`,
            row.createCell(columnNumber++).setCellValue("Год");
            // `Номер недели`,
            row.createCell(columnNumber++).setCellValue("Номер недели");
            // `Дата старта`,
            row.createCell(columnNumber++).setCellValue("Дата старта");
            // `Дата окончания`,
            row.createCell(columnNumber++).setCellValue("Дата окончания");
            // `Кол-во мест`,
            row.createCell(columnNumber++).setCellValue("Кол-во мест");
            // `Занято мест`
            row.createCell(columnNumber++).setCellValue("Занято мест");
            // `Статус`,
            row.createCell(columnNumber++).setCellValue("Статус");
            // `Коммент`,
            lastCol = columnNumber;
            row.createCell(columnNumber++).setCellValue("Коммент");

            sheet.addMergedRegion(new CellRangeAddress(
                    currentRow - 1, //first row (0-based)
                    currentRow - 1, //last row (0-based)
                    firstCol, //first column (0-based)
                    lastCol //last column (0-based)
            ));

            HSSFCellStyle styleHead = style;
            styleHead.setAlignment(HorizontalAlignment.CENTER);
            rowHead.getCell(firstCol).setCellStyle(styleHead);
        }

        //Родственники
        for (int i = 0; i < 3; i++) {
            firstCol = columnNumber;
            rowFamily[i] = columnNumber;
            rowHead.createCell(firstCol).setCellValue("Родственник " + i);
            // `id`,
            row.createCell(columnNumber++).setCellValue("ID family");
            // `ФИО`,
            row.createCell(columnNumber++).setCellValue("ФИО");
            // `Родственная связь`,
            row.createCell(columnNumber++).setCellValue("Родственная связь");
            // `День рождения`,
            row.createCell(columnNumber++).setCellValue("День рождения");
            // `Осн.телефон`,
            row.createCell(columnNumber++).setCellValue("Осн.телефон");
            // `доп.телефон`,
            row.createCell(columnNumber++).setCellValue("доп.телефон");
            // `е-почта`,
            row.createCell(columnNumber++).setCellValue("E-mail");
            // `комментарий`,
            row.createCell(columnNumber++).setCellValue("Комментарий");
            // `статус`
            lastCol = columnNumber;
            row.createCell(columnNumber++).setCellValue("Статус");

            sheet.addMergedRegion(new CellRangeAddress(
                    currentRow - 1, //first row (0-based)
                    currentRow - 1, //last row (0-based)
                    firstCol, //first column (0-based)
                    lastCol //last column (0-based)
            ));

            HSSFCellStyle styleHead = style;
            styleHead.setAlignment(HorizontalAlignment.CENTER);
            rowHead.getCell(firstCol).setCellStyle(styleHead);
        }

        lastColumn = columnNumber;
        setStyle(row, style);
        currentRow++;

        System.out.println("rowFamily = " + rowFamily[0] + ", "
                + rowFamily[1] + ", "
                + rowFamily[2] + ", "
                + ", rowSeason = "
                + rowSeason[0] + ", "
                + rowSeason[1] + ", "
                + rowSeason[2] + ", "
                + rowSeason[3] + ", "
                + rowSeason[4] + ", "
                + rowSeason[5] + ", "
                + rowSeason[6] + ", "
                + rowSeason[7]);
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
        row.createCell(0).setCellValue("Сформировано " + formatDate.format(new java.util.Date()) + " пользователем " + WORK_USER_NAME + ".");
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

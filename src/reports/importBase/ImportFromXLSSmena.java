package reports.importBase;

import blogic.entity.Smena;
import dal.Factory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by hammer on 10.08.2017.
 */
public class ImportFromXLSSmena {

    private static ArrayList<Smena> arrayList = new ArrayList<>();
    private static int smena_id = 55;
    private static int smena_name = 55;
    private static int smena_number = 55;
    private static int smena_places = 55;
    private static int smena_dateStart = 55;
    private static int smena_dateEnd = 55;
    private static int smena_year = 55;
    private static int smena_status = 55;
    private static int smena_comment = 55;


    public ImportFromXLSSmena() {
    }

    private static boolean checkFile(HSSFRow row) {
        Iterator<Cell> cells = row.iterator();
        while (cells.hasNext()) {
            Cell cell = cells.next();
            System.out.println("cell.getColumnIndex() " + cell.getColumnIndex());
            switch (cell.getStringCellValue()) {
                case "smena_id":
                    smena_id = cell.getColumnIndex();
                    System.out.println(smena_id);
                    break;
                case "name":
                    smena_name = cell.getColumnIndex();
                    System.out.println(smena_name);
                    break;
                case "number":
                    smena_number = cell.getColumnIndex();
                    System.out.println(smena_number);
                    break;
                case "places":
                    smena_places = cell.getColumnIndex();
                    System.out.println(smena_places);
                    break;
                case "dateStart":
                    smena_dateStart = cell.getColumnIndex();
                    System.out.println(smena_dateStart);
                    break;
                case "dateEnd":
                    smena_dateEnd = cell.getColumnIndex();
                    System.out.println(smena_dateEnd);
                    break;
                case "year":
                    smena_year = cell.getColumnIndex();
                    System.out.println(smena_year);
                    break;
                case "status":
                    smena_status = cell.getColumnIndex();
                    System.out.println(smena_status);
                    break;
                case "comment":
                    smena_comment = cell.getColumnIndex();
                    System.out.println(smena_comment);
                    break;
            }
        }
        if (smena_id == 55 || smena_name == 55 || smena_number == 55 ||
                smena_places == 55 || smena_dateStart == 55 || smena_dateEnd == 55 ||
                smena_year == 55 || smena_status == 55 || smena_comment == 55)
            return false;
        return true;
    }

    public static int readSmenaFromExcel(File file) throws IOException {
        HSSFWorkbook book = new HSSFWorkbook(new FileInputStream(file));
        HSSFSheet sheet = book.getSheetAt(0);

        Iterator<Row> rowIterator = sheet.rowIterator();
        if (rowIterator.hasNext()) {
            if (!checkFile((HSSFRow) rowIterator.next()))
                return 0; // неверный файл - кривая шапка
        }
        while (rowIterator.hasNext()) {
            HSSFRow row = (HSSFRow) rowIterator.next();
            if (row.getCell(smena_id) != null) {
                Smena smena = getSmena(row);
                arrayList.add(smena);
            }
        }
        book.close();
        printArray();
        if (loadToBase() == arrayList.size())
            return 1; //все прошло успешно
        return 2;//кол-во загруженных строк не совпадает с размером массива
    }

    public static void printArray() {
        for (Smena smena : arrayList) {
            System.out.println(smena);
        }
    }

    @Deprecated
    private static Smena getSmena(HSSFRow row) {
        Smena smena = new Smena();
        HSSFCell smena_idCell = row.getCell(smena_id);
        int cellType = smena_idCell.getCellType();
        if (smena_idCell != null) {
            switch (cellType) {
                case Cell.CELL_TYPE_STRING:
                    smena.setId(Integer.parseInt(smena_idCell.getStringCellValue()));
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    smena.setId((int) smena_idCell.getNumericCellValue());
                    break;
            }
        }

        HSSFCell smena_nameCell = row.getCell(smena_name);
        smena.setName(smena_nameCell.getStringCellValue());
        HSSFCell smena_numberCell = row.getCell(smena_number);
        smena.setNumber((int) smena_numberCell.getNumericCellValue());
        HSSFCell smena_placesCell = row.getCell(smena_places);
        smena.setPlaces((int) smena_placesCell.getNumericCellValue());
        HSSFCell smena_dateStartCell = row.getCell(smena_dateStart);
        smena.setDateStart(smena_dateStartCell.getStringCellValue());
        HSSFCell smena_dateEndCell = row.getCell(smena_dateEnd);
        smena.setDateEnd(smena_dateEndCell.getStringCellValue());
        HSSFCell smena_yearCell = row.getCell(smena_year);
        smena.setYear((int) smena_yearCell.getNumericCellValue());
        HSSFCell smena_commentCell = row.getCell(smena_comment);
        smena.setComment(smena_commentCell == null ? ""
                : smena_commentCell.getStringCellValue());
        HSSFCell smena_statusCell = row.getCell(smena_status);
        smena.setStatus((int) smena_statusCell.getNumericCellValue());
        return smena;
    }

    private static int loadToBase() {
        Smena smena;
        int ret = 0;
        int id;
        for (int i = 0; i < arrayList.size(); i++) {
            smena = arrayList.get(i);
            id = Factory.getSmenaDAO().addSmena(smena);
            if (id != 0)
                ret++;
        }
        return ret;
    }
}

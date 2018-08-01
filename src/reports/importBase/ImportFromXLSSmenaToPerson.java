package reports.importBase;

import blogic.entity.Person;
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
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by hammer on 10.08.2017.
 */
public class ImportFromXLSSmenaToPerson {

    static class SmenaPerson implements Serializable {
        int smena;
        int person;

        public SmenaPerson(int smena, int person) {
            this.smena = smena;
            this.person = person;
        }

        @Override
        public String toString() {
            return "SmenaPerson{" +
                    "smena=" + smena +
                    ", person=" + person +
                    '}';
        }
    }

    private static ArrayList<SmenaPerson> smenaPersonArrayList = new ArrayList<>();

    private static int person_code = 555;
    private static int smena_id = 555;

    public ImportFromXLSSmenaToPerson() {
    }

    private static boolean checkFile(HSSFRow row) {
        Iterator<Cell> cells = row.iterator();
        while (cells.hasNext()) {
            Cell cell = cells.next();
            System.out.println("cell.getColumnIndex() " + cell.getColumnIndex());
            switch (cell.getStringCellValue()) {
                case "person_code":
                    person_code = cell.getColumnIndex();
                    System.out.println(person_code);
                    break;
                case "smena_id":
                    smena_id = cell.getColumnIndex();
                    System.out.println(smena_id);
                    break;
            }
            if (person_code != 555 && smena_id != 555)
                break;
        }
        return true;
    }

    @Deprecated
    public static int wrihtSmenaToPersonFromExcel(File file) throws IOException {
        HSSFWorkbook book = new HSSFWorkbook(new FileInputStream(file));
        HSSFSheet sheet = book.getSheet("smenas");

        Iterator<Row> rowIterator = sheet.rowIterator();
        if (rowIterator.hasNext()) {
            if (!checkFile((HSSFRow) rowIterator.next()))
                return 0; // неверный файл - кривая шапка
        }
        while (rowIterator.hasNext()) {
            HSSFRow row = (HSSFRow) rowIterator.next();
            System.out.println(row.getRowNum());
            int id = 1;
            if (!row.getCell(person_code).getStringCellValue().equals("")) {
                int smena = getSmenaId(row);
                int person = getPersonId(row);
                SmenaPerson sp = new SmenaPerson(smena, person);
                System.out.println(sp.toString());
                smenaPersonArrayList.add(sp);
            }
        }
        book.close();
        printArray();

        if (loadToBase() == smenaPersonArrayList.size())
            return 1; //все прошло успешно
        return 2;//кол-во загруженных строк не совпадает с размером массива
    }

    public static void printArray() {
        for (SmenaPerson person : smenaPersonArrayList) {
            System.out.println(person);
        }
        System.out.println("Size " + smenaPersonArrayList.size());
    }

    @Deprecated
    private static int getSmenaId(HSSFRow row) {
        HSSFCell smena_idCell = row.getCell(smena_id);
        return (int) smena_idCell.getNumericCellValue();
    }

    private static int getPersonId(HSSFRow row) {
        HSSFCell person_codeCell = row.getCell(person_code);
        return Factory.getPersonDAO().searchPerson(person_codeCell.getStringCellValue(), 4, true)
                .get(0)
                .getId();
    }

    private static int loadToBase() {
        int ret = 0;
        for (int i = 0; i < smenaPersonArrayList.size(); i++) {
            Person person = Factory.getPersonDAO().getPerson(smenaPersonArrayList.get(i).person);
            Smena smenaBase = Factory.getSmenaDAO().getSmena(smenaPersonArrayList.get(i).smena);
            person.getSmenasList().add(smenaBase);
            Factory.getPersonDAO().updatePerson(person);



//            Person person = Factory.getPersonDAO().getPerson(personId);
//            Smena smena = Factory.getSmenaDAO().getSmena(ls.realRow);
//            person.getSmenasList().add(smena);
//            Factory.getPersonDAO().updatePerson(person);
            ret++;
        }
        return ret;
    }
}

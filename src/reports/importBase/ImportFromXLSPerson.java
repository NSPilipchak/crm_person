package reports.importBase;

import blogic.entity.Family;
import blogic.entity.Person;
import blogic.entity.PersonData;
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
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by hammer on 10.08.2017.
 */
public class ImportFromXLSPerson {

    static ArrayList<Person> arrayList = new ArrayList<>();

    private static int person_id = 555;
    private static int person_code = 555;
    private static int person_fName = 555;
    private static int person_mName = 555;
    private static int person_lName = 555;
    private static int person_bDate = 555;
    private static int person_inn = 555;
    private static int person_fPhone = 555;
    private static int person_lPhone = 555;
    private static int person_email = 555;
    private static int person_comment = 555;
    private static int personData_oldAdres = 555;
    private static int person_status = 555;
    private static int personData_sPassport = 555;
    private static int personData_aPassport = 555;
    private static int personData_dPassport = 555;
    private static int personData_city = 555;
    private static int personData_district = 555;
    private static int personData_street = 555;
    private static int personData_house = 555;
    private static int personData_corp = 555;
    private static int personData_flat = 555;
    private static int personData_pensionNum = 555;
    private static int personData_comment = 555;
    private static int personData_verificationDocum = 555;
    private static int personData_status = 555;
    private static int family_cognate = 555;
    private static int family_name = 555;
    private static int family_bDate = 555;
    private static int family_email = 555;
    private static int family_fPhone = 555;
    private static int family_lPhone = 555;
    private static int family_comment = 555;
    private static int family_verificatioDocum = 555;
    private static int family_status = 555;
    private static int family2_cognate = 555;
    private static int family2_name = 555;
    private static int family2_bDate = 555;
    private static int family2_email = 555;
    private static int family2_fPhone = 555;
    private static int family2_lPhone = 555;
    private static int family2_comment = 555;
    private static int family2_verificatioDocum = 555;
    private static int family2_status = 555;
    private static int smena_id = 555;

    private static int needSmena = 0; // 0 - загрузка привязки смены не нужна; 1 - загружается привязка смены


    public ImportFromXLSPerson() {
    }

    private static boolean checkFile(HSSFRow row) {
        Iterator<Cell> cells = row.iterator();
        while (cells.hasNext()) {
            Cell cell = cells.next();
            System.out.println("cell.getColumnIndex() " + cell.getColumnIndex());
            switch (cell.getStringCellValue()) {
                case "person_id":
                    person_id = cell.getColumnIndex();
                    System.out.println(person_id);
                    break;
                case "person_code":
                    person_code = cell.getColumnIndex();
                    System.out.println(person_code);
                    break;
                case "person_fName":
                    person_fName = cell.getColumnIndex();
                    System.out.println(person_fName);
                    break;
                case "person_mName":
                    person_mName = cell.getColumnIndex();
                    System.out.println(person_mName);
                    break;
                case "person_lName":
                    person_lName = cell.getColumnIndex();
                    System.out.println(person_lName);
                    break;
                case "person_bDate":
                    person_bDate = cell.getColumnIndex();
                    System.out.println(person_bDate);
                    break;
                case "person_inn":
                    person_inn = cell.getColumnIndex();
                    System.out.println(person_inn);
                    break;
                case "person_fPhone":
                    person_fPhone = cell.getColumnIndex();
                    System.out.println(person_fPhone);
                    break;
                case "person_lPhone":
                    person_lPhone = cell.getColumnIndex();
                    System.out.println(person_lPhone);
                    break;
                case "person_email":
                    person_email = cell.getColumnIndex();
                    System.out.println(person_email);
                    break;
                case "person_comment":
                    person_comment = cell.getColumnIndex();
                    System.out.println(person_comment);
                    break;
                case "personData_oldAdres":
                    personData_oldAdres = cell.getColumnIndex();
                    System.out.println(personData_oldAdres);
                    break;
                case "person_status":
                    person_status = cell.getColumnIndex();
                    System.out.println(person_status);
                    break;
                case "personData_sPassport":
                    personData_sPassport = cell.getColumnIndex();
                    System.out.println(personData_sPassport);
                    break;
                case "personData_aPassport":
                    personData_aPassport = cell.getColumnIndex();
                    System.out.println(personData_aPassport);
                    break;
                case "personData_dPassport":
                    personData_dPassport = cell.getColumnIndex();
                    System.out.println(personData_dPassport);
                    break;
                case "personData_city":
                    personData_city = cell.getColumnIndex();
                    System.out.println(personData_city);
                    break;
                case "personData_district":
                    personData_district = cell.getColumnIndex();
                    System.out.println(personData_district);
                    break;
                case "personData_street":
                    personData_street = cell.getColumnIndex();
                    System.out.println(personData_street);
                    break;
                case "personData_house":
                    personData_house = cell.getColumnIndex();
                    System.out.println(personData_house);
                    break;
                case "personData_corp":
                    personData_corp = cell.getColumnIndex();
                    System.out.println(personData_corp);
                    break;
                case "personData_flat":
                    personData_flat = cell.getColumnIndex();
                    System.out.println(personData_flat);
                    break;
                case "personData_pensionNum":
                    personData_pensionNum = cell.getColumnIndex();
                    System.out.println(personData_pensionNum);
                    break;
                case "personData_comment":
                    personData_comment = cell.getColumnIndex();
                    System.out.println(personData_comment);
                    break;
                case "personData_verificationDocum":
                    personData_verificationDocum = cell.getColumnIndex();
                    System.out.println(personData_verificationDocum);
                    break;
                case "personData_status":
                    personData_status = cell.getColumnIndex();
                    System.out.println(personData_status);
                    break;
                case "family_cognate":
                    family_cognate = cell.getColumnIndex();
                    System.out.println(family_cognate);
                    break;
                case "family_name":
                    family_name = cell.getColumnIndex();
                    System.out.println(family_name);
                    break;
                case "family_bDate":
                    family_bDate = cell.getColumnIndex();
                    System.out.println(family_bDate);
                    break;
                case "family_email":
                    family_email = cell.getColumnIndex();
                    System.out.println(family_email);
                    break;
                case "family_fPhone":
                    family_fPhone = cell.getColumnIndex();
                    System.out.println(family_fPhone);
                    break;
                case "family_lPhone":
                    family_lPhone = cell.getColumnIndex();
                    System.out.println(family_lPhone);
                    break;
                case "family_comment":
                    family_comment = cell.getColumnIndex();
                    System.out.println(family_comment);
                    break;
                case "family_verificatioDocum":
                    family_verificatioDocum = cell.getColumnIndex();
                    System.out.println(family_verificatioDocum);
                    break;
                case "family_status":
                    family_status = cell.getColumnIndex();
                    System.out.println(family_status);
                    break;
                case "family2_cognate":
                    family2_cognate = cell.getColumnIndex();
                    System.out.println(family2_cognate);
                    break;
                case "family2_name":
                    family2_name = cell.getColumnIndex();
                    System.out.println(family2_name);
                    break;
                case "family2_bDate":
                    family2_bDate = cell.getColumnIndex();
                    System.out.println(family2_bDate);
                    break;
                case "family2_email":
                    family2_email = cell.getColumnIndex();
                    System.out.println(family2_email);
                    break;
                case "family2_fPhone":
                    family2_fPhone = cell.getColumnIndex();
                    System.out.println(family2_fPhone);
                    break;
                case "family2_lPhone":
                    family2_lPhone = cell.getColumnIndex();
                    System.out.println(family2_lPhone);
                    break;
                case "family2_comment":
                    family2_comment = cell.getColumnIndex();
                    System.out.println(family2_comment);
                    break;
                case "family2_verificatioDocum":
                    family2_verificatioDocum = cell.getColumnIndex();
                    System.out.println(family2_verificatioDocum);
                    break;
                case "family2_status":
                    family2_status = cell.getColumnIndex();
                    System.out.println(family2_status);
                    break;
                case "smena_id":
                    smena_id = cell.getColumnIndex();
                    System.out.println(smena_id);
                    break;
            }
        }
        if (person_id == 555 || person_code == 555 || person_fName == 555 ||
                person_mName == 555 || person_lName == 555 || person_bDate == 555 ||
                person_inn == 555 || person_fPhone == 555 || person_lPhone == 555 ||
                person_email == 555 || person_comment == 555 || personData_oldAdres == 555 ||
                person_status == 555 || personData_sPassport == 555 || personData_aPassport == 555 ||
                personData_dPassport == 555 || personData_city == 555 || personData_district == 555 ||
                personData_street == 555 || personData_house == 555 || personData_corp == 555 ||
                personData_flat == 555 || personData_pensionNum == 555 || personData_comment == 555 ||
                personData_verificationDocum == 555 || personData_status == 555 || family_cognate == 555 ||
                family_name == 555 || family_bDate == 555 || family_email == 555 ||
                family_fPhone == 555 || family_lPhone == 555 || family_comment == 555 ||
                family_verificatioDocum == 555 || family_status == 555 || family2_cognate == 555 ||
                family2_name == 555 || family2_bDate == 555 || family2_email == 555 ||
                family2_fPhone == 555 || family2_lPhone == 555 || family2_comment == 555 ||
                family2_verificatioDocum == 555 || family2_status == 555)
            return false;
        if (needSmena == 1 && smena_id == 555)
            return false;
        return true;
    }

    @Deprecated
    public static int readPersonFromExcel(File file, int smenaNeed) throws IOException {
        needSmena = smenaNeed;
        HSSFWorkbook book = new HSSFWorkbook(new FileInputStream(file));
        HSSFSheet sheet = book.getSheetAt(0);

        Iterator<Row> rowIterator = sheet.rowIterator();
        if (rowIterator.hasNext()) {
            if (!checkFile((HSSFRow) rowIterator.next()))
                return 0; // неверный файл - кривая шапка
        }
        while (rowIterator.hasNext()) {
            HSSFRow row = (HSSFRow) rowIterator.next();
            System.out.println(row.getRowNum());
            if (!row.getCell(person_code).getStringCellValue().equals("")) {
                Person person = getPerson(row);
                PersonData personData = getPersonData(row);
                Family family1 = getPersonFamilyFirst(row);
                Family family2 = getPersonFamilySecond(row);
                if (needSmena == 1) {
                    Smena smena = getSmena(row);
                    person.getSmenasList().add(smena);
                }
                person.setPersonData(personData);
                if (family1 != null)
                    person.getFamilyList().add(family1);
                if (family2 != null)
                    person.getFamilyList().add(family2);
                arrayList.add(person);
                System.out.println(person);
            }
        }
        book.close();
        printArray();

        if (loadToBase() == arrayList.size())
            return 1; //все прошло успешно
        return 2;//кол-во загруженных строк не совпадает с размером массива
    }

    public static void printArray() {
        for (Person person : arrayList) {
            System.out.println(person);
        }
    }

    @Deprecated
    private static Smena getSmena(HSSFRow row) {
        Smena smena = new Smena();
        HSSFCell smena_idCell = row.getCell(smena_id);
        if (smena_idCell != null) {
            int cellType = smena_idCell.getCellType();
            switch (cellType) {
                case Cell.CELL_TYPE_STRING:
                    smena = Factory.getSmenaDAO().getSmena(Integer.parseInt(smena_idCell.getStringCellValue()));
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    smena = Factory.getSmenaDAO().getSmena((int) smena_idCell.getNumericCellValue());
                    break;
                case Cell.CELL_TYPE_BLANK:
                    break;
            }
        }
        return smena;
    }

    @Deprecated
    private static Person getPerson(HSSFRow row) {
        Person person = new Person();
        HSSFCell person_idCell = row.getCell(person_id);
        int cellType = person_idCell.getCellType();
        if (person_idCell != null) {
            switch (cellType) {
                case Cell.CELL_TYPE_STRING:
                    person.setId(Integer.parseInt(person_idCell.getStringCellValue()));
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    person.setId((int) person_idCell.getNumericCellValue());
                    break;
            }
        }

        HSSFCell person_codeCell = row.getCell(person_code);
        person.setCode(person_codeCell.getStringCellValue());
        HSSFCell person_fNameCell = row.getCell(person_fName);
        person.setfName(person_fNameCell.getStringCellValue());
        HSSFCell person_mNameCell = row.getCell(person_mName);
        person.setmName(person_mNameCell.getStringCellValue());
        HSSFCell person_lNameCell = row.getCell(person_lName);
        person.setlName(person_lNameCell.getStringCellValue());
        HSSFCell person_bDateCell = row.getCell(person_bDate);
        person.setbDate(person_bDateCell.getStringCellValue());
        HSSFCell person_innCell = row.getCell(person_inn);
        person.setInn(person_innCell.getStringCellValue());


        HSSFCell person_fPhoneCell = row.getCell(person_fPhone);
        cellType = person_fPhoneCell.getCellType();
        if (person_fPhoneCell != null) {
            switch (cellType) {
                case Cell.CELL_TYPE_STRING:
                    person.setfPhone(parsePhoneNumber(person_fPhoneCell.getStringCellValue()));
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    person.setfPhone(parsePhoneNumber((int) person_fPhoneCell.getNumericCellValue()));
                    break;
            }
        }

        HSSFCell person_lPhoneCell = row.getCell(person_lPhone);
        person.setlPhone(parsePhoneNumber(person_lPhoneCell == null ? 0
                : (int) person_lPhoneCell.getNumericCellValue()));

        HSSFCell person_emailCell = row.getCell(person_email);
        person.setEmail(person_emailCell == null ? ""
                : person_emailCell.getStringCellValue());
        HSSFCell person_commentCell = row.getCell(person_comment);
        person.setComment(person_commentCell == null ? ""
                : person_commentCell.getStringCellValue());
        HSSFCell person_statusCell = row.getCell(person_status);
        person.setStatus((int) person_statusCell.getNumericCellValue());
        return person;
    }

    @Deprecated
    private static PersonData getPersonData(HSSFRow row) {
        PersonData personData = new PersonData();
        HSSFCell personData_sPassportCell = row.getCell(personData_sPassport);
        personData.setsPassport(
                        personData_sPassportCell.getStringCellValue().toUpperCase());
        HSSFCell personData_aPassportCell = row.getCell(personData_aPassport);
        personData.setaPassport(personData_aPassportCell.getStringCellValue());
        HSSFCell personData_dPassportCell = row.getCell(personData_dPassport);
        personData.setdPassport(personData_dPassportCell.getStringCellValue());
        HSSFCell personData_cityCell = row.getCell(personData_city);
        personData.setCity((int) personData_cityCell.getNumericCellValue());
        HSSFCell personData_districtCell = row.getCell(personData_district);
        personData.setDistrict((int) personData_districtCell.getNumericCellValue());
        HSSFCell personData_streetCell = row.getCell(personData_street);
        personData.setStreet(personData_streetCell.getStringCellValue());

        HSSFCell personData_houseCell = row.getCell(personData_house);
        int cellType = personData_houseCell.getCellType();
        if (personData_houseCell != null) {
            switch (cellType) {
                case Cell.CELL_TYPE_STRING:
                    personData.setHouse(personData_houseCell.getStringCellValue());
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    personData.setHouse(String.valueOf((int) personData_houseCell.getNumericCellValue()));
                    break;
                case Cell.CELL_TYPE_BLANK:
                    personData.setHouse("");
                    break;
            }
        }

        HSSFCell personData_corpCell = row.getCell(personData_corp);
        if (personData_corpCell != null) {
            cellType = personData_corpCell.getCellType();
            switch (cellType) {
                case Cell.CELL_TYPE_STRING:
                    personData.setCorp(personData_corpCell.getStringCellValue());
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    personData.setCorp(String.valueOf((int) personData_corpCell.getNumericCellValue()));
                    break;
                case Cell.CELL_TYPE_BLANK:
                    personData.setCorp("");
                    break;
            }
        }

        HSSFCell personData_flatCell = row.getCell(personData_flat);
        if (personData_flatCell != null) {
            cellType = personData_flatCell.getCellType();
            switch (cellType) {
                case Cell.CELL_TYPE_STRING:
                    personData.setFlat(personData_flatCell.getStringCellValue());
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    personData.setFlat(String.valueOf((int) personData_flatCell.getNumericCellValue()));
                    break;
                case Cell.CELL_TYPE_BLANK:
                    personData.setFlat("");
                    break;
            }
        }

        HSSFCell personData_pensionNumCell = row.getCell(personData_pensionNum);
        if (personData_pensionNumCell != null) {
            cellType = personData_pensionNumCell.getCellType();
            switch (cellType) {
                case Cell.CELL_TYPE_STRING:
                    personData.setPensionNum("0"); //номер не может быть пустым
                    if (personData_pensionNumCell != null && !"".equals(personData_pensionNumCell.getStringCellValue())) {
                        personData.setPensionNum(personData_pensionNumCell.getStringCellValue());
                    }
//                    personData.setPensionNum(personData_pensionNumCell.getStringCellValue());
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    personData.setPensionNum("0"); //номер не может быть пустым
                    if (personData_pensionNumCell != null && !"".equals(personData_pensionNumCell.getNumericCellValue())) {
                        personData.setPensionNum(String.valueOf(personData_pensionNumCell.getNumericCellValue()));
                    }

                    personData.setPensionNum(String.valueOf((int) personData_pensionNumCell.getNumericCellValue()));
                    break;
                case Cell.CELL_TYPE_BLANK:
                    personData.setPensionNum("");
                    break;
            }
        }

        HSSFCell personData_commentCell = row.getCell(personData_comment);
        if (personData_commentCell != null) {
            cellType = personData_commentCell.getCellType();
            switch (cellType) {
                case Cell.CELL_TYPE_STRING:
                    personData.setComment(personData_commentCell.getStringCellValue());
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    personData.setComment(String.valueOf((int) personData_commentCell.getNumericCellValue()));
                    break;
                case Cell.CELL_TYPE_BLANK:
                    personData.setComment("");
                    break;
            }
        }

        HSSFCell personData_verificationDocumCell = row.getCell(personData_verificationDocum);
        personData.setVerificationDocum((int) personData_verificationDocumCell.getNumericCellValue());

        HSSFCell personData_oldAdresCell = row.getCell(personData_oldAdres);
        personData.setOldAdres(personData_oldAdresCell.getStringCellValue());
        HSSFCell personData_statusCell = row.getCell(personData_status);
        personData.setStatus((int) personData_statusCell.getNumericCellValue());
        return personData;
    }

    @Deprecated
    private static Family getPersonFamilyFirst(HSSFRow row) {
        Family family = new Family();
        HSSFCell family_cognateCell = row.getCell(family_cognate);
        if (family_cognateCell == null)
            return null;
        family.setCognate(family_cognateCell.getStringCellValue());
        HSSFCell family_nameCell = row.getCell(family_name);
        family.setName(family_nameCell.getStringCellValue());
        HSSFCell family_bDateCell = row.getCell(family_bDate);
        family.setbDate(family_bDateCell == null ? "" : family_bDateCell.getStringCellValue());
        HSSFCell family_emailCell = row.getCell(family_email);
        family.setEmail(family_emailCell == null ? "" : family_emailCell.getStringCellValue());

        HSSFCell family_fPhoneCell = row.getCell(family_fPhone);
        int cellType = family_fPhoneCell.getCellType();
        if (family_fPhoneCell != null) {
            switch (cellType) {
                case Cell.CELL_TYPE_STRING:
                    family.setfPhone(parsePhoneNumber(family_fPhoneCell.getStringCellValue()));
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    family.setfPhone(parsePhoneNumber((int) family_fPhoneCell.getNumericCellValue()));
                    break;
            }
        }

        HSSFCell family_lPhoneCell = row.getCell(family_lPhone);
        family.setlPhone(parsePhoneNumber(family_lPhoneCell == null ? 0 : (int) family_lPhoneCell.getNumericCellValue()));
        HSSFCell family_commentCell = row.getCell(family_comment);
        family.setComment(family_commentCell == null ? "" : family_commentCell.getStringCellValue());
        HSSFCell family_verificatioDocumCell = row.getCell(family_verificatioDocum);
        family.setVerificationDocum((int) family_verificatioDocumCell.getNumericCellValue());
        HSSFCell family_statusCell = row.getCell(family_status);
        family.setStatus((int) family_statusCell.getNumericCellValue());
        return family;
    }

    @Deprecated
    private static Family getPersonFamilySecond(HSSFRow row) {
        Family family = new Family();
        HSSFCell family2_cognateCell = row.getCell(family2_cognate);
        if (family2_cognateCell == null)
            return null;
        family.setCognate(family2_cognateCell.getStringCellValue());
        HSSFCell family2_nameCell = row.getCell(family2_name);
        family.setName(family2_nameCell.getStringCellValue());
        HSSFCell family2_bDateCell = row.getCell(family2_bDate);
        family.setbDate(family2_bDateCell == null ? "" : family2_bDateCell.getStringCellValue());
        HSSFCell family2_emailCell = row.getCell(family2_email);
        family.setEmail(family2_emailCell == null ? "" : family2_emailCell.getStringCellValue());
        HSSFCell family2_fPhoneCell = row.getCell(family2_fPhone);
        family.setfPhone(parsePhoneNumber((int) family2_fPhoneCell.getNumericCellValue()));
        HSSFCell family2_lPhoneCell = row.getCell(family2_lPhone);
        family.setlPhone(parsePhoneNumber(family2_lPhoneCell == null ? 0 : (int) family2_lPhoneCell.getNumericCellValue()));
        HSSFCell family2_commentCell = row.getCell(family2_comment);
        family.setComment(family2_commentCell == null ? "" : family2_commentCell.getStringCellValue());
        HSSFCell family2_verificatioDocumCell = row.getCell(family2_verificatioDocum);
        family.setVerificationDocum((int) family2_verificatioDocumCell.getNumericCellValue());
        HSSFCell family2_statusCell = row.getCell(family2_status);
        family.setStatus((int) family2_statusCell.getNumericCellValue());
        return family;
    }

    private static String parsePhoneNumber(int i) {
        String phoneStr = "" + i;
        if (phoneStr.length() != 9)
            return "";
        String ret = "+38(0";
        ret += phoneStr.substring(0, 2) + ")"
                + phoneStr.substring(2, 5) + "-"
                + phoneStr.substring(5, 7) + "-"
                + phoneStr.substring(7, 9);
        return ret;
    }

    private static String parsePhoneNumber(String i) {
        String phoneStr = "" + i;
        if (phoneStr.length() != 9)
            return "";
        String ret = "+38(0";
        ret += phoneStr.substring(0, 2) + ")"
                + phoneStr.substring(2, 5) + "-"
                + phoneStr.substring(5, 7) + "-"
                + phoneStr.substring(7, 9);
        return ret;
    }

    private static String parseSeriaPassport(String str) {
        String ret = "";
//        System.out.println("str " + "\"" + str + "\"");
        ret += str.substring(0, 2) + ""
                + str.substring(2);
        return ret;
    }

    private static String parseAdres(String adres) {
        if (adres.length() != 9)
            return "";
        String ret = "+38 (0";
        return ret;
    }

    private static int loadToBase() {
        Person person;
        PersonData personDataIn;
        Set<Family> familySetIn;
        Family family;
        int ret = 0;
        int id;
        for (int i = 0; i < arrayList.size(); i++) {
            person = arrayList.get(i);
            personDataIn = person.getPersonData();
            familySetIn = person.getFamilyList();

            Iterator<Family> familyIterator = familySetIn.iterator();
            Set<Family> familySetOut = new HashSet<>();

            while (familyIterator.hasNext()) {
                family = familyIterator.next();
                System.out.println(family);
                if (!family.getCognate().equals("")) {
                    id = Factory.getFamilyDAO().addFamily(family);
                    familySetOut.add(Factory.getFamilyDAO().getFamily(id));
                }
            }
            person.setFamilyList(familySetOut);

            id = Factory.getPersonDataDAO().addPersonData(personDataIn);
            person.setPersonData(Factory.getPersonDataDAO().findPersonDataById(id));

            if (needSmena == 1) {
                Smena smena;
                Set<Smena> smenaSet = person.getSmenasList();
                Iterator<Smena> smenaIterator = smenaSet.iterator();
                while (smenaIterator.hasNext()) {
                    smena = smenaIterator.next();
                    Smena smenaBase = Factory.getSmenaDAO().getSmena(smena.getId());
                    person.getSmenasList().clear();
                    person.getSmenasList().add(smenaBase);
                }
            }

            Factory.getPersonDAO().addPerson(person);
            if (id != 0)
                ret++;
        }
        return ret;
    }
}

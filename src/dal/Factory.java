package dal;

import dal.implementations.*;
import dal.interfaces.*;

/**
 * Created by hammer on 22.07.2017.
 */
public class Factory {

    private static Factory instance = null;
    private static FamilyDAO familyDAO = null;
    private static PersonDAO personDAO = null;
    private static SmenaDAO smenaDAO = null;
    private static PersonDataDAO personDataDAO = null;
    private static LibraryDAO libraryDAO = null;
    private static UserDAO userDAO = null;
    private static ReportDAO reportDAO = null;

    public static synchronized Factory getInstance() {
        if (instance == null) {
            instance = new Factory();
        }
        return instance;
    }

    public static FamilyDAO getFamilyDAO() {
        if (familyDAO == null) {
            familyDAO = new FamilyDAO_MySQL_Hibernate_2();
        }
        return familyDAO;
    }

    public static PersonDAO getPersonDAO() {
        if (personDAO == null) {
            personDAO = new PersonDAO_MySQL_Hibenate_2();
        }
        return personDAO;
    }

    public static SmenaDAO getSmenaDAO() {
        if (smenaDAO == null) {
            smenaDAO = new SmenaDAO_MySQL_Hibernate();
        }
        return smenaDAO;
    }

    public static PersonDataDAO getPersonDataDAO() {
        if (personDataDAO == null) {
            personDataDAO = new PersonDataDAO_MySQL_Hibernate();
        }
        return personDataDAO;
    }

    public static LibraryDAO getLibraryDAO() {
        if (libraryDAO == null) {
            libraryDAO = new LibraryDAO_MySQL_Hibernate();
        }
        return libraryDAO;
    }

    public static UserDAO getUserDAO() {
        if (userDAO == null) {
            userDAO = new UserDAO_MySQL_Hibernate();
        }
        return userDAO;
    }

    public static ReportDAO getReportDAO() {
        if (reportDAO == null) {
            reportDAO = new ReportDAO_MySQL_Hibernate();
        }
        return reportDAO;
    }
}

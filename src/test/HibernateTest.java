package test;

import blogic.entity.Family;
import blogic.entity.Person;
import blogic.entity.PersonData;
import blogic.entity.Smena;
import dal.Factory;
import dal.interfaces.PersonDAO;
import dal.implementations.PersonDAO_MySQL_Hibenate_2;
import junit.framework.Assert;
import org.junit.Test;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Random;

public class HibernateTest {

    /**
     * запускать только на ПУСТОЙ тестовой базе
     */

    @Test
    public void addPersonTest() {
        CreateData cd = new CreateData();
        // Установим данные
        Person person = cd.newPerson();

        PersonData personData = cd.newPersonData();
        Factory.getPersonDataDAO().addPersonData(personData);
        person.setPersonData(personData);

        // Добавим в базу
        int id = Factory.getPersonDAO().addPerson(person);
        // Перечитаем
        Person personDB = Factory.getPersonDAO().getPerson(id);
        // Убедимся, что считывание совпадает с тем, что записывали
        Assert.assertTrue(person.toString().equals(personDB.toString()));
//    }
//        @Test
//        public void updatePersonTest() {
        // Изменим объект, запишем и снова убедимся, что все в порядке
        person.setfName(cd.rndText());
        person.setmName(cd.rndText());
        person.setlName(cd.rndText());
        Factory.getPersonDAO().updatePerson(person);
        Person personDB2 = Factory.getPersonDAO().getPerson(id);
        Assert.assertTrue(person.toString().equals(personDB2.toString()));
        Assert.assertFalse(person.toString().equals(personDB.toString()));
//        }
//    @Test
//    public void addFamilyTest() {
        // Пробуем добавить 1-го родственника
        Family family = cd.newFamily();
        int idFam = Factory.getFamilyDAO().addFamily(family);
        // Перечитаем
        Family familyDB = Factory.getFamilyDAO().getFamily(idFam);
        System.out.println(family.toString());
        System.out.println(familyDB.toString());
        Assert.assertTrue(family.toString().equals(familyDB.toString()));
        // Убедимся, что у персоны еще нет родственников
        Assert.assertTrue(Factory.getPersonDAO().getPerson(id).getFamilyList().size() == 0);

        //Добавляем Персоне родственника
        person.getFamilyList().add(family);
        Factory.getPersonDAO().updatePerson(person);
        // Убедимся, что у персоны появился 1 родственник
        Assert.assertTrue(Factory.getPersonDAO().getPerson(id).getFamilyList().size() == 1);
//    }
//    @Test
//    public void updateFamilyTest() {
        //Добавим Персоне сразу двух родственников
        Family family2 = cd.newFamily();
        Factory.getFamilyDAO().addFamily(family2);
        Family family3 = cd.newFamily();
        Factory.getFamilyDAO().addFamily(family3);
        person.getFamilyList().add(family2);
        person.getFamilyList().add(family3);
        Factory.getPersonDAO().updatePerson(person);
        Assert.assertTrue(Factory.getPersonDAO().getPerson(id).getFamilyList().size() == 3);


        //Добавим смену
        Smena smena = cd.newSmena();
        int idSmen = Factory.getSmenaDAO().addSmena(smena);
        // Перечитаем
        Smena smenaDB = Factory.getSmenaDAO().getSmena(idSmen);
        // Убедимся, что считывание совпадает с тем, что записывали
        Assert.assertTrue(smena.toString().equals(smenaDB.toString()));

        // Убедимся, что персона еще отдыхала
        Assert.assertTrue(Factory.getPersonDAO().getPerson(id).getSmenasList().size() == 0);

        //Добавляем Персоне отдых
        person.getSmenasList().add(smena);
        Factory.getPersonDAO().updatePerson(person);
        // Убедимся, что у персоны появился 1 приезд
        Assert.assertTrue(Factory.getPersonDAO().getPerson(id).getSmenasList().size() == 1);

    }

    @Test
    public void randomTest() {
        CreateData cd = new CreateData();
        // Установим данные
        Person person2 = cd.newPerson();

        PersonData personData = cd.newPersonData();

        Factory.getPersonDataDAO().addPersonData(personData);
        person2.setPersonData(personData);

        // Добавим в базу
        int id = Factory.getPersonDAO().addPerson(person2);
        // Перечитаем
        Person personDB = Factory.getPersonDAO().getPerson(id);
        // Убедимся, что считывание совпадает с тем, что записывали
        Assert.assertTrue(person2.toString().equals(personDB.toString()));
//    }
//        @Test
//        public void updatePersonTest() {
        // Изменим объект название предмета, запишем и снова убедимся, что все в порядке
        person2.setfName(cd.rndText());
        person2.setmName(cd.rndText());
        person2.setlName(cd.rndText());
        Factory.getPersonDAO().updatePerson(person2);
        Person personDB2 = Factory.getPersonDAO().getPerson(id);
        Assert.assertTrue(person2.toString().equals(personDB2.toString()));
        Assert.assertFalse(person2.toString().equals(personDB.toString()));
//        }
//    @Test
//    public void addFamilyTest() {
        // Пробуем добавить 1-го родственника
        Family family = cd.newFamily();
        int idFam = Factory.getFamilyDAO().addFamily(family);
        // Перечитаем
        Family familyDB = Factory.getFamilyDAO().getFamily(idFam);
        System.out.println(family.toString());
        System.out.println(familyDB.toString());
        Assert.assertTrue(family.toString().equals(familyDB.toString()));
        // Убедимся, что у персоны еще нет родственников
        Assert.assertTrue(Factory.getPersonDAO().getPerson(id).getFamilyList().size() == 0);

        //Добавляем Персоне родственника
        person2.getFamilyList().add(family);
        Factory.getPersonDAO().updatePerson(person2);
        // Убедимся, что у персоны появился 1 родственник
        Assert.assertTrue(Factory.getPersonDAO().getPerson(id).getFamilyList().size() == 1);
//    }
//    @Test
//    public void updateFamilyTest() {
        //Добавим Персоне сразу двух родственников
        Family family2 = cd.newFamily();
        Factory.getFamilyDAO().addFamily(family2);
        Family family3 = cd.newFamily();
        Factory.getFamilyDAO().addFamily(family3);
        person2.getFamilyList().add(family2);
        person2.getFamilyList().add(family3);
        Factory.getPersonDAO().updatePerson(person2);
        Assert.assertTrue(Factory.getPersonDAO().getPerson(id).getFamilyList().size() == 3);


        //Добавим смену
        Smena smena = Factory.getSmenaDAO().getSmena(2);
        // Перечитаем
        //Добавляем Персоне отдых
        person2.getSmenasList().add(smena);
        smena = Factory.getSmenaDAO().getSmena(5);
        person2.getSmenasList().add(smena);
        Factory.getPersonDAO().updatePerson(person2);
        // Убедимся, что у персоны появился 1 приезд
        Assert.assertTrue(Factory.getPersonDAO().getPerson(id).getSmenasList().size() == 1);
    }

    @Test
    public void PersonToPersonDataTest() {
        CreateData cd = new CreateData();
        System.out.println("Hibernate tutorial start");

        PersonData personData = cd.newPersonData();
        int id = Factory.getPersonDataDAO().addPersonData(personData);

        PersonData personDataDB = Factory.getPersonDataDAO().findPersonDataById(id);
        // Убедимся, что считывание совпадает с тем, что записывали
        Assert.assertTrue(personData.toString().equals(personDataDB.toString()));

        Person person = cd.newPerson();

        personData.setPerson(person);
        id = Factory.getPersonDAO().addPerson(person);

        Person personDB = Factory.getPersonDAO().getPerson(id);
        // Убедимся, что считывание совпадает с тем, что записывали
        Assert.assertTrue(person.toString().equals(personDB.toString()));


        cd.printResult();
    }

    @Test
    public void writeToBaseTest() {
        CreateData cd = new CreateData();

        //создали новую персону
        Person person = cd.newPerson();
        //создали новую дату для персоны
        PersonData personData = cd.newPersonData();
        //записали пДату в базу
        Factory.getPersonDataDAO().addPersonData(personData);
        //привязали к пДате Персону
        personData.setPerson(person);
        //создали новую смену
        Smena smena = cd.newSmena();
        //записали смену в базу
        Factory.getSmenaDAO().addSmena(smena);
        //добавили смену к персоне
        person.getSmenasList().add(smena);
        //Создали нового родственника 1
        Family family = cd.newFamily();
        //Записали нового родственника в базу 1
        Factory.getFamilyDAO().addFamily(family);
        //добавили родственника к персоне 1
        person.getFamilyList().add(family);
        //Создали нового родственника 2
        family = cd.newFamily();
        //Записали нового родственника в базу 2
        Factory.getFamilyDAO().addFamily(family);
        //добавили родственника к персоне 2
        person.getFamilyList().add(family);
        //Создали нового родственника 3
        family = cd.newFamily();
        //Записали нового родственника в базу 3
        Factory.getFamilyDAO().addFamily(family);
        //добавили родственника к персоне 3
        person.getFamilyList().add(family);
        // записываем персону в базу и получаем ИД персоны для проверки того что записали
        int id = Factory.getPersonDAO().addPerson(person);
        //вычитываем записанную персону
        Person personDB = Factory.getPersonDAO().getPerson(id);
        System.out.println("вычитываем записанную персону 1="+ person.toString());
        System.out.println("вычитываем записанную персону 2="+ personDB.toString());
        // Убедимся, что считывание совпадает с тем, что записывали
        Assert.assertEquals(person, personDB);
//        Assert.assertEq
    }
}

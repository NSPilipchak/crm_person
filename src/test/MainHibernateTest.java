package test;

import blogic.entity.Family;
import blogic.entity.Person;
import dal.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hammer on 01.08.2017.
 */
public class MainHibernateTest {

    public MainHibernateTest() {
        printList();

//        setlist();

        Family family2 = new Family();
        family2.setName("Анатолий иванович Пупкин");
        family2.setbDate("11.11.3333");
        family2.setCognate("отец");                                                 //родственная связь
        family2.setfPhone("05050505050");
        family2.setVerificationDocum(1);
        family2.setStatus(1);
        int ii = Factory.getFamilyDAO().addFamily(family2);
        Person person = Factory.getPersonDAO().getPerson(1);
        person.getFamilyList().add(family2);
        Factory.getPersonDAO().updateFamilyList(person, family2);

        printList();

        System.out.println("#######################");
        System.out.println("#######################");
        printListwishentry();

        System.exit(0);
    }


    private void printListwishentry() {
        ArrayList<Person> personArrayList = Factory.getPersonDAO().getAllPerson();
        System.out.println("========Все персоны=========");
        for (Person person : personArrayList) {
            System.out.println("Персона : " + person.getfName() + " " + person.getlName() + "  Номер кода : " + person.getCode());
            ArrayList<Family> familyArrayList = Factory.getFamilyDAO().getFamilyByPerson(person.getId());
            for (Family family : familyArrayList) {
                System.out.println("Родственник " + family.getName() + " " + family.getCognate());
            }
        }
    }


    private void printList() {

        //Смотрим список Персон
        ArrayList<Person> personArrayList = Factory.getPersonDAO().getAllPerson();
        System.out.println("List of PERSON");
        System.out.println("-------------start");
        // В списке видим, персоны, что родственники пока не привязаны к персонам - количество = 0
        for (Person p : personArrayList) {
            System.out.println(p.getId() + ":" + p.getfName() + " " + p.getmName() + " " + p.getlName()
//                    +". Number of family:" + p.getFamilyList().size()
            );
        }
        System.out.println("----------------end");

        //Смотрим список Персон
        ArrayList<Family> familyArrayList = Factory.getFamilyDAO().getAllFamily();
        System.out.println("List of FAMILY");
        System.out.println("-------------start");
        // В списке видим, Родственников
        for (Family f : familyArrayList) {
            System.out.println(f.getId() + ":" + f.getName()
//                    + " - " + f.getPerson().getlName()
            );
        }
        System.out.println("----------------end");
    }


    public void setlist() {
        Person person = new Person();
        person.setCode("1234");
        person.setfName("Антон");
        person.setmName("Юрьевич");
        person.setlName("Васильев");
        person.setbDate("22.22.3333");
        person.setfPhone("55555555555");
//        person.setsPassport("АА 123456");
//        person.setdPassport("44.44.8888");
//        person.setaPassport("кем выдан");
//        person.setCity(1);
//        person.setDistrict(2);
//        person.setStreet("Улица");
//        person.setHouse("55");
//        person.setInn("1234567890");
//        person.setPensionNum("123456");
//        person.setVerificationDocum(1);
        person.setStatus(1);

        Family family = new Family();
        family.setName("Василий иванович Пупкин");
        family.setbDate("55.55.6666");
        family.setCognate("Брат");                                                 //родственная связь
        family.setfPhone("05050505050");
        family.setVerificationDocum(1);
        family.setStatus(1);
        int i = Factory.getFamilyDAO().addFamily(family);

        Family family2 = new Family();
        family2.setName("Евгений иванович Пупкин");
        family2.setbDate("11.11.3333");
        family2.setCognate("Сестра");                                                 //родственная связь
        family2.setfPhone("05050505050");
        family2.setVerificationDocum(1);
        family2.setStatus(1);
        int ii = Factory.getFamilyDAO().addFamily(family2);

        Family family3 = new Family();
        family3.setName("Сергей иванович Пупкин");
        family3.setbDate("11.11.3333");
        family3.setCognate("дедушка");                                                 //родственная связь
        family3.setfPhone("05050505050");
        family3.setVerificationDocum(1);
        family3.setStatus(1);
        int iii = Factory.getFamilyDAO().addFamily(family3);

        person.getFamilyList().add(family);
        person.getFamilyList().add(family2);
        person.getFamilyList().add(family3);

        System.out.println("**********************");
        System.out.println("*********first********");
        System.out.println("**********************");

        int id = Factory.getPersonDAO().addPerson(person);

        person = new Person();
        person.setCode("1234");
        person.setfName("Гантон");
        person.setmName("Дрьевич");
        person.setlName("Аасильев");
        person.setbDate("22.22.3333");
        person.setfPhone("55555555555");
//        person.setsPassport("АА 654321");
//        person.setdPassport("44.44.8888");
//        person.setaPassport("кем выдан");
//        person.setCity(1);
//        person.setDistrict(2);
//        person.setStreet("Улица");
//        person.setHouse("55");
//        person.setInn("0987654321");
//        person.setPensionNum("654321");
//        person.setVerificationDocum(1);
        person.setStatus(1);

        family = new Family();
        family.setName("Федор Дрьевич Аасильев");
        family.setbDate("55.55.6666");
        family.setCognate("Брат");                                                 //родственная связь
        family.setfPhone("05050505050");
        family.setVerificationDocum(1);
        family.setStatus(1);
        int iiii = Factory.getFamilyDAO().addFamily(family);

        family2 = new Family();
        family2.setName("иванович Дрьевич Аасильев");
        family2.setbDate("11.11.3333");
        family2.setCognate("Сестра");                                                 //родственная связь
        family2.setfPhone("05050505050");
        family2.setVerificationDocum(1);
        family2.setStatus(1);
        int iiiii = Factory.getFamilyDAO().addFamily(family2);

        person.getFamilyList().add(family);
        person.getFamilyList().add(family2);
        System.out.println("**********************");
        System.out.println("*********two**********");
        System.out.println("**********************");

        id = Factory.getPersonDAO().addPerson(person);
    }
}

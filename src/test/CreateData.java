package test;

import blogic.entity.Family;
import blogic.entity.Person;
import blogic.entity.PersonData;
import blogic.entity.Smena;
import dal.Factory;
import dal.implementations.PersonDAO_MySQL_Hibenate_2;
import dal.interfaces.PersonDAO;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by hammer on 05.08.2017.
 */
public class CreateData {
    public CreateData() {
    }

    private int rnd() {
        Random random = new Random();
        int ret = random.nextInt(9) + 1;
        return ret;
    }

    private String rndEmail() {
        String ret = rndText().substring(0, 8) + "@gmail.com";
        return ret;
    }

    private String rndInt(int x) {
        String ret = "";
        for (int i = 0; i < x; i++) {
            ret += rnd();
        }
        return ret;
    }

    public String rndText() {
        SecureRandom random = new SecureRandom();
        return new BigInteger(130, random).toString(32);//.substring(0, 6);
    }

    private String rndPassport() {
        String ret = "АА " + rndInt(6);
        return ret;
    }

    private String rndDate() {
        String ret = rndInt(2) + "." + rndInt(2) + "." + rndInt(4);
        return ret;
    }

    private String rndPhone() {
        String ret = "+38(" + rndInt(3) + ")" + rndInt(3) + "-" + rndInt(2) + "-" + rndInt(2);
        return ret;
    }

    private String rndComment() {
        String ret = "comment №" + rndInt(3) + rndText();
        return ret;
    }

    public void printResult() {
        PersonDAO personDAO = new PersonDAO_MySQL_Hibenate_2();
        ArrayList<Person> personArrayList = Factory.getPersonDAO().getAllPerson();
        for (Person p : personArrayList) {
            System.out.println("*******======>");
            System.out.println("*******Found person: " + p);
            System.out.println("*******PersonData from person entity: "
                    + p.getPersonData().getId());
            System.out.println("*******PersonData from database before transaction complete: "
                    + personDAO.findPersonDataById(p.getPersonDataId()));
            System.out.println("<======*******");
        }
    }

    public Person newPerson() {
        Person person = new Person();
        person.setCode("Code-" + rndInt(6));
        person.setfName(rndText());
        person.setmName(rndText());
        person.setlName(rndText());
        person.setbDate(rndDate());
        person.setfPhone(rndPhone());
        person.setlPhone(rndPhone());
        person.setInn(rndInt(10));
        person.setStatus(1);
        person.setComment(rndComment());
        person.setEmail(rndEmail());
        return person;
    }

    public PersonData newPersonData() {
        PersonData personData = new PersonData();
        personData.setsPassport(rndPassport());
        personData.setdPassport(rndDate());
        personData.setaPassport(rndText());
        personData.setCity(1);
        personData.setDistrict(2);
        personData.setStreet(rndText());
        personData.setHouse(rndInt(2));
        personData.setPensionNum(rndInt(6));
        personData.setVerificationDocum(1);
        personData.setCorp(rndInt(2));
        personData.setFlat(rndInt(2));
        return personData;
    }

    public Family newFamily() {
        Family family = new Family();
        family.setName(rndText());
        family.setbDate(rndDate());
        family.setCognate(rndText().substring(0, 8));                                                 //родственная связь
        family.setfPhone(rndPhone());
        family.setVerificationDocum(1);
        family.setStatus(1);
        family.setComment(rndComment());
        family.setEmail(rndEmail());
        family.setlPhone(rndPhone());
        return family;
    }

    public Smena newSmena() {
        Smena smena = new Smena();
        smena.setName("смена-" + rndInt(3));
        smena.setNumber(Integer.parseInt(rndInt(2)));
        smena.setPlaces(Integer.parseInt(rndInt(3)));
        smena.setYear(2);
        smena.setDateStart(rndDate());
        smena.setDateEnd(rndDate());
        smena.setStatus(1);
        smena.setComment(rndComment());
        return smena;
    }

}

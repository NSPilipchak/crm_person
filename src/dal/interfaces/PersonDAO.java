package dal.interfaces;

import blogic.entity.Family;
import blogic.entity.Person;
import blogic.entity.PersonData;

import java.util.ArrayList;
import java.util.Set;

/**
 * Created by hammer on 14.07.2017.
 */
public interface PersonDAO {

    int countPerson();

    int addPerson(Person p);

    int updatePerson(Person p);

    int updateFamilyList(Person p, Family families);

    void deletePerson(Person p);

    Person getPerson(int personId);

    public PersonData findPersonDataById(int personId);

    ArrayList<Person> getAllPerson();

    ArrayList<Person> getAllPerson(int first, int max);

    ArrayList<Person> getFiltredPerson(int first, int max,
                                       ArrayList smena, ArrayList status,
                                       ArrayList verification);

    ArrayList<Person> findPersonBySmenaAndStatus(ArrayList smenaId, ArrayList status, boolean lazyInitialize);

    ArrayList<Person> searchPerson(String search);

    ArrayList<Person> searchPerson(String search, int parametr, boolean strongSearch);

}

package dal.interfaces;

import blogic.entity.Family;
import blogic.entity.Person;
import blogic.entity.PersonData;

import java.util.ArrayList;

/**
 * Created by hammer on 14.07.2017.
 */
public interface PersonDataDAO {

    int addPersonData(PersonData pd);

    void updatePersonData(PersonData pd);

    void deletePerson(PersonData pd);

    PersonData findPersonDataById(int personId);

    ArrayList<PersonData> getAllPersonData();
}
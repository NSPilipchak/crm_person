package dal.interfaces;

import blogic.entity.Family;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hammer on 20.07.2017.
 */
public interface FamilyDAO {

    int addFamily(Family f);

    void updateFamily(Family f);

    void deleteFamily(Family f);

    Family getFamily(int familyId);

    ArrayList<Family> getAllFamily();

    ArrayList<Family> getFamilyById(List<Integer> idf);

    ArrayList<Family> getFamilyByPerson(int personId);

}

package dal.implementations;

import blogic.entity.PersonData;
import dal.HibernateUtil;
import dal.interfaces.PersonDataDAO;
import org.hibernate.Hibernate;
import org.hibernate.Session;

import java.util.ArrayList;

/**
 * Created by hammer on 20.07.2017.
 */
public class PersonDataDAO_MySQL_Hibernate implements PersonDataDAO {


    @Override
    public int addPersonData(PersonData pd) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        int result = (int) session.save(pd);
        session.getTransaction().commit();
        session.close();
        return result;
    }

    @Override
    public void updatePersonData(PersonData pd) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.saveOrUpdate(pd);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void deletePerson(PersonData pd) {

    }

    @Override
    public PersonData findPersonDataById(int personId) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        PersonData result = (PersonData) session.get(PersonData.class, personId);
        session.getTransaction().commit();
        session.close();
        return result;
    }

    @Override
    public ArrayList<PersonData> getAllPersonData() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        ArrayList<PersonData> personDataArrayList =
                (ArrayList<PersonData>) session.createQuery("from PersonData order by id").list();
        session.getTransaction().commit();
        session.close();
        return personDataArrayList;
    }
}

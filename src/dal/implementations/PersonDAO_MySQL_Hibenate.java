package dal.implementations;

import blogic.entity.Family;
import blogic.entity.Person;
import blogic.entity.PersonData;
import dal.HibernateUtil;
import dal.interfaces.PersonDAO;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Created by hammer on 14.07.2017.
 */
public class PersonDAO_MySQL_Hibenate implements PersonDAO {
    @Override
    public int countPerson() {
        return 0;
    }

    @Override
    public int addPerson(Person p) {
        Session session = null;
        int ret = 0;
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            ret = (int) session.save(p);
            session.getTransaction().commit();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка при вставке", JOptionPane.OK_OPTION);
        }
        session.close();
        return ret;
    }

    @Override
    public ArrayList<Person> getAllPerson() {
        Session session;
        ArrayList<Person> pp;
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            Query query = session.createQuery("FROM Person"); //HQL
            pp = (ArrayList<Person>) query.getResultList();
            session.getTransaction().commit();
        } catch (Throwable ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Ошибка при вычитке", JOptionPane.OK_OPTION);
            throw new ExceptionInInitializerError(ex);
        }
        session.close();
        return pp;
    }

    @Override
    public ArrayList<Person> searchPerson(String search) {
        return null;
    }

    @Override
    public ArrayList<Person> searchPerson(String search, int parametr, boolean strongSearch) {
        return null;
    }

    @Override
    public ArrayList<Person> getAllPerson(int first, int max) {
        return null;
    }

    @Override
    public ArrayList<Person> getFiltredPerson(int first, int max, ArrayList smena, ArrayList status, ArrayList verification) {
        return null;
    }

    @Override
    public ArrayList<Person> findPersonBySmenaAndStatus(ArrayList smenaId, ArrayList status, boolean lazyInitialize) {
        return null;
    }

    @Override
    public Person getPerson(int personId) {

        return null;
    }

    @Override
    public PersonData findPersonDataById(int personId) {
        return null;
    }

    @Override
    public int updatePerson(Person p) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            session.update(p);
            session.getTransaction().commit();
        } catch (Throwable ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Ошибка при обновлении", JOptionPane.OK_OPTION);
        }
        session.close();
        return 0;
    }

    @Override
    public int updateFamilyList(Person p, Family families) {
        return 0;
    }

    @Override
    public void deletePerson(Person p) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(p);
            session.getTransaction().commit();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка при удалении", JOptionPane.OK_OPTION);
        }
    }
}

package dal.implementations;

import blogic.entity.Family;
import blogic.entity.Person;
import dal.HibernateUtil;
import dal.interfaces.FamilyDAO;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by hammer on 20.07.2017.
 */
public class FamilyDAO_MySQL_Hibernate implements FamilyDAO {
    @Override
    public int addFamily(Family f) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            session.save(f);
            session.getTransaction().commit();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка при вставке", JOptionPane.OK_OPTION);
        }
        session.close();
        return 0;
    }

//    @Override
    public int addFamilyByPerson(Family family, Person person) {
        Session session = null;
        try {
            System.out.println("addFamilyByPerson");
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();

            Set<Family> fam = new HashSet<>();
            fam.add(family);

            session.getTransaction().commit();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage().toString(), "Ошибка при вставке", JOptionPane.OK_OPTION);
        }
        session.close();
        return 0;

    }

//    @Override
    public ArrayList<Family> readAllFamily() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        ArrayList<Family> pp = new ArrayList<>();
        try {
            session.beginTransaction();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
        Query query = session.createQuery("FROM Family"); //HQL
        pp = (ArrayList<Family>) query.getResultList();
        session.getTransaction().commit();
        session.close();
        return pp;
    }

    @Override
    public ArrayList<Family> getFamilyByPerson(int idPerson) {
        return null;
    }

    @Override
    public void updateFamily(Family f) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            session.update(f);
            session.getTransaction().commit();
        } catch (Throwable ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Ошибка при обновлении", JOptionPane.OK_OPTION);
        }
        session.close();
    }

    @Override
    public void deleteFamily(Family f) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(f);
            session.getTransaction().commit();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка при удалении", JOptionPane.OK_OPTION);
        }
    }

    @Override
    public Family getFamily(int familyId) {
        return null;
    }

    @Override
    public ArrayList<Family> getAllFamily() {
        return null;
    }

    @Override
    public ArrayList<Family> getFamilyById(List<Integer> idf) {
        return null;
    }

}

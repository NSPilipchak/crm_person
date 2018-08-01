package dal.implementations;

import blogic.entity.Smena;
import dal.HibernateUtil;
import dal.interfaces.SmenaDAO;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hammer on 20.07.2017.
 */
public class SmenaDAO_MySQL_Hibernate implements SmenaDAO {
    @Override
    public int addSmena(Smena s) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        int ret = (int) session.save(s);
        session.getTransaction().commit();
        session.close();
        return ret;
    }

    @Override
    public Smena getSmena(int smenaId) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Smena result = session.get(Smena.class, smenaId);
        session.getTransaction().commit();
        session.close();
        return result;
    }

    @Override
    public ArrayList<Smena> getAllSmena() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        ArrayList<Smena> smenaArrayList = (ArrayList<Smena>) session.createQuery("from Smena order by id").list();
        session.getTransaction().commit();
        session.close();
        return smenaArrayList;
    }

    @Override
    public ArrayList<Smena> getSmenaByPerson(int personId) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        ArrayList<Smena> smenaArrayList;
        Query query = session.createQuery(
                "select s from Smena s left join fetch s.personList p where p.id=:personId order by s.id");
        query.setParameter("personId", personId);

        smenaArrayList = (ArrayList<Smena>) query.getResultList();
        session.getTransaction().commit();
        session.close();
        return smenaArrayList;
    }

    @Override
    public void updateSmena(Smena s) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.saveOrUpdate(s);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void deleteSmena(Smena s) {

    }

    @Override
    public ArrayList<Smena> getSmenaBySeason(ArrayList seasonId) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        ArrayList<Smena> smenaArrayList;
        Query query = session.createQuery(
                "select s from Smena s where s.year in:seasonId order by s.id");
        query.setParameterList("seasonId", seasonId);

        smenaArrayList = (ArrayList<Smena>) query.getResultList();
        session.getTransaction().commit();
        session.close();
        return smenaArrayList;
    }

    @Override
    public ArrayList<Smena> getSmenaById(ArrayList smenaId) {
        if (smenaId == null)
            return null;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        ArrayList<Smena> smenaArrayList;
        Query query = session.createQuery(
                "select s from Smena s where s.id in:smenaId order by s.id");
        query.setParameterList("smenaId", smenaId);

        smenaArrayList = (ArrayList<Smena>) query.getResultList();
        session.getTransaction().commit();
        session.close();
        return smenaArrayList;
    }

    @Override
    public int changeCountSmena(int smenaId, int districtId, int count) {
        try {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();

            Smena smena = session.get(Smena.class, smenaId);
            smena.setBusyPlaces(smena.getBusyPlaces() + count);
            session.update(smena);

            session.getTransaction().commit();
            session.close();
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public int reCountSmena() {
        try {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            ArrayList<Smena> smenaArrayList = (ArrayList<Smena>) session.createQuery("from Smena order by id").list();
            for (Smena smenaSet : smenaArrayList) {
                Query query = session.createQuery("SELECT COUNT(*) FROM Person p " +
                        "left join p.smenasList s " +
                        "WHERE p.status in:status " +
                        "and s.id in:smena "
                );
                query.setParameter("smena", smenaSet.getId());
                query.setParameter("status", 1);

                System.out.println("smenaSet.getName() " + smenaSet.getName() + " = " + query.getResultList());
                String countResult = String.valueOf(query.getResultList()).replaceAll("[\\[\\]]", "");
                int count = Integer.parseInt(countResult);
                smenaSet.setBusyPlaces(count);
                session.update(smenaSet);
            }

            session.getTransaction().commit();
            session.close();
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }
}

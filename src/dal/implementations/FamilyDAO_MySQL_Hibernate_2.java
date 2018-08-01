package dal.implementations;

import blogic.entity.Family;
import dal.HibernateUtil;
import dal.interfaces.FamilyDAO;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hammer on 20.07.2017.
 */
public class FamilyDAO_MySQL_Hibernate_2 implements FamilyDAO {

    @Override
    public int addFamily(Family f) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        int result = (int) session.save(f);
        session.getTransaction().commit();
        session.close();
        return result;
    }

    @Override
    public ArrayList<Family> getFamilyByPerson(int personId) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        ArrayList<Family> familyArrayList;
        Query query = session.createQuery(
                "select f from Family f left join fetch f.personList p where p.id=:personId"
        );

        query.setParameter("personId", personId);
        familyArrayList = (ArrayList<Family>) query.list();
        System.out.println("******** "+familyArrayList.toString());
        session.getTransaction().commit();
        session.close();
        return familyArrayList;
    }

    @Override
    public void updateFamily(Family f) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.saveOrUpdate(f);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void deleteFamily(Family f) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.delete(f);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public Family getFamily(int familyId) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Family result = (Family) session.get(Family.class, familyId);
        // Насильная инициализация списка. Вообщем-то не очень хорошая практика так делать
        Hibernate.initialize(result.getPersonList());
        session.getTransaction().commit();
        session.close();
        return result;
    }

    @Override
    public ArrayList<Family> getAllFamily() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        ArrayList<Family> familyArrayList = (ArrayList<Family>) session.createQuery("from Family order by name").list();
        // Насильная инициализация списка. Вообщем-то не очень хорошая практика так делать
        for (Family f : familyArrayList) {
            Hibernate.initialize(f.getPersonList());
        }
        session.getTransaction().commit();
        session.close();
        return familyArrayList;
    }

    @Override
    public ArrayList<Family> getFamilyById(List<Integer> idf) {
        StringBuffer sb = new StringBuffer("");
        if (idf != null && idf.size() > 0) {
            for (int id : idf) {
                sb.append(sb.length() > 0 ? "," + id : id);
            }
            sb.insert(0, "WHERE subjectId in (");
            sb.append(")");
        }
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
//        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        ArrayList<Family> familyArrayList =
                (ArrayList<Family>) session.createQuery("from Family " + sb.toString() + " order by name").list();
        // Насильная инициализация списка. Вообщем-то не очень хорошая практика так делать
        for (Family f : familyArrayList) {
            Hibernate.initialize(f.getPersonList());
        }
        session.getTransaction().commit();
        session.close();
        return familyArrayList;
    }
}

package dal.implementations;

import blogic.entity.User;
import dal.HibernateUtil;
import dal.interfaces.UserDAO;
import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.ArrayList;

/**
 * Created by hammer on 06.08.2017.
 */
public class UserDAO_MySQL_Hibernate implements UserDAO {

    @Override
    public int addUser(User u) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        int ret = (int) session.save(u);
        session.getTransaction().commit();
        session.close();
        return ret;
    }

    @Override
    public void updateUser(User u) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.saveOrUpdate(u);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void deleteUser(User u) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.delete(u);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public User getUser(int userId) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        User result = session.get(User.class, userId);
        session.getTransaction().commit();
        session.close();
        return result;
    }

    @Override
    public ArrayList<User> getUserList() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        ArrayList<User> smenaArrayList = (ArrayList<User>) session.createQuery("from User order by id").list();
        session.getTransaction().commit();
        session.close();
        return smenaArrayList;
    }

    @Override
    public boolean checkUser(String user, String pass) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("select password from User where user like :search");
        query.setParameter("search", user);
        String basePass = String.valueOf(query.getResultList());
        basePass = basePass.substring(1, basePass.length() - 1);
        session.getTransaction().commit();
        session.close();
        if (basePass.equals(DigestUtils.md5Hex(pass))) {
            return true;
        }
        return false;
    }

    @Override
    public User getUser(String user) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("from User where user like :search");
        query.setParameter("search", user);
        ArrayList<User> result = (ArrayList<User>) query.getResultList();
        session.getTransaction().commit();
        session.close();
        return result.get(0);
    }

    @Override
    public String[] users() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        ArrayList user = (ArrayList) session.createQuery("select user from User where status=1 order by id").list();
        String[] ret = new String[user.size()];
        for (int i = 0; i < user.size(); i++) {
            ret[i] = user.get(i).toString();
        }
        session.getTransaction().commit();
        session.close();
        return ret;
    }
}

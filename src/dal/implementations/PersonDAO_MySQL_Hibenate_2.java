package dal.implementations;

import blogic.entity.Family;
import blogic.entity.Person;
import blogic.entity.PersonData;
import com.mysql.jdbc.exceptions.MySQLDataException;
import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;
import dal.HibernateUtil;
import dal.interfaces.PersonDAO;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.swing.*;
import java.util.ArrayList;

import static view.MainFrame.statusBar;

/**
 * Created by hammer on 14.07.2017.
 */
public class PersonDAO_MySQL_Hibenate_2 implements PersonDAO {

    @Override
    public int countPerson() {
        statusBar.setStatus("Подсчет кол-ва персон в базе...");
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        String str = String.valueOf(session.createQuery("Select count (*) from Person").getResultList());
        int ret = Integer.parseInt(str.substring(1, str.length() - 1));
        session.getTransaction().commit();
        session.close();
        statusBar.setStatus("Полученно " + ret + "персон.");
        return ret;
    }

    @Override
    public int addPerson(Person p) {
        statusBar.setStatus("Запись персоны в БД.");
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        int result = (int) session.save(p);
        session.getTransaction().commit();
        session.close();
        return result;
    }

    @Override
    public ArrayList<Person> getAllPerson() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
//        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        ArrayList<Person> pp = (ArrayList<Person>) session.createQuery("from Person order by id").list();
        session.getTransaction().commit();
        session.close();
        return pp;
    }

    @Override
    public ArrayList<Person> getAllPerson(int first, int max) {
        statusBar.setStatus("Подгрузка таблицы персон ID c " + first + " по " + (first + max));
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("from Person order by id DESC");

        query.setFirstResult(first);
        query.setMaxResults(max);
        ArrayList<Person> pp = (ArrayList<Person>) query.list();
        session.getTransaction().commit();
        session.close();
        return pp;
    }

    @Override
    public ArrayList<Person> getFiltredPerson(int first, int max, ArrayList smena, ArrayList status, ArrayList verification) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        ArrayList<Person> personArrayList = new ArrayList<>();
        Query query = session.createQuery(
                "select distinct p from Person p " +
                        "left join p.smenasList s " +
                        "where p.status in:status " +
                        "and s.id in:smena " +
                        "and p.personData.verificationDocum in:verification " +
                        "order by p.id DESC"
        );
        query.setParameterList("smena", smena);
        query.setParameterList("status", status);
        query.setParameterList("verification", verification);
        query.setFirstResult(first);
        query.setMaxResults(max);
        try {
            personArrayList = (ArrayList<Person>) query.list();
        } catch (Exception e) {
            return null;
        }
        session.getTransaction().commit();
        session.close();
        return personArrayList;
    }

    @Override
    public ArrayList<Person> searchPerson(String search) {
        /**
         * не годится!!! нужно переделывать! пока что отключено.
         */
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        statusBar.setStatus("Поиск данных " + search + " в Имени...");
        Query query = session.createQuery("from Person where fName like :search order by id");
        query.setParameter("search", "%" + search + "%");

        ArrayList<Person> pp = (ArrayList<Person>) query.list();
        ArrayList<Person> result = null;
        statusBar.setStatus("Найдено " + pp.size() + " строк");
        statusBar.setStatus("Всего найдено " + pp.size() + " строк");
        statusBar.setStatus("Поиск данных " + search + " в Фамилии...");

        session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.createQuery("from Person where lName like :search order by id");
        query.setParameter("search", "%" + search + "%");
        ArrayList<Person> pp2 = (ArrayList<Person>) query.list();
        for (Person aPp : pp) {
            for (int j = 0; j < pp2.size(); j++) {
                if (aPp.getId() == pp2.get(j).getId()) {
                    pp2.remove(j);
                }
            }
        }
        if (pp2.size() > 0) {
            result = new ArrayList<>(pp.size() + pp2.size());
            result.addAll(pp);
            result.addAll(pp2);
            pp = result;
        }
        statusBar.setStatus("Найдено " + pp2.size() + " строк");
        statusBar.setStatus("Всего найдено " + pp.size() + " строк");
        statusBar.setStatus("Поиск данных " + search + " в Отчестве...");

        session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.createQuery("from Person where mName like :search order by id");
        query.setParameter("search", "%" + search + "%");
        pp2 = (ArrayList<Person>) query.list();
        for (Person aPp : pp) {
            for (int j = 0; j < pp2.size(); j++) {
                if (aPp.getId() == pp2.get(j).getId()) {
                    pp2.remove(j);
                }
            }
        }
        if (pp2.size() > 0) {
            result = new ArrayList<>(pp.size() + pp2.size());
            result.addAll(pp);
            result.addAll(pp2);
            pp = result;
        }
        session.getTransaction().commit();
        session.close();
        return pp;
    }

    @Override
    public ArrayList<Person> searchPerson(String search, int parametr, boolean strongSearch) {
        /**
         * Принимаемый параметр:
         * 1 - поиск по Фамилии
         * 2 - поиск по ИНН
         * 3 - поиск по Серии паспорта
         * 4 - поиск по идентификатору/коду
         * 5 - поиск по дате рождения - добавленно 20.11.17
         */

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        statusBar.setStatus("Поиск данных ");
        Query query;
        String str = "";

        switch (parametr) {
            case 1:
                str = "from Person where lName like :search order by id";
                break;
            case 2:
                str = "from Person where inn like :search order by id";
                break;
            case 3:
                str = "from Person p where p.personData.sPassport like :search order by id";
                break;
            case 4:
                str = "from Person p where p.code like :search order by id";
                break;
            case 5:
                str = "from Person p where p.bDate like :search order by id";
                break;
        }

        query = session.createQuery(str);
        if (strongSearch)
            query.setParameter("search", search);
        else
            query.setParameter("search", "%" + search + "%");

        ArrayList<Person> pp = (ArrayList<Person>) query.list();
        session.getTransaction().commit();
        session.close();
        return pp;
    }


    @Override
    public ArrayList<Person> findPersonBySmenaAndStatus(ArrayList smenaId, ArrayList status, boolean lazyInitialize) {

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        ArrayList<Person> personArrayList;
        Query query = session.createQuery(
                "select distinct p from Person p left join fetch p.smenasList s where p.status in:status and s.id in:smenaId"
        );

        query.setParameterList("smenaId", smenaId);
        query.setParameterList("status", status);

        personArrayList = (ArrayList<Person>) query.list();

        if (lazyInitialize) {
            for (Person p : personArrayList) {
                Hibernate.initialize(p.getFamilyList());
            }
        }

        session.getTransaction().commit();
        session.close();
        return personArrayList;
    }

    @Override
    public Person getPerson(int personId) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Person result = (Person) session.get(Person.class, personId);
        // Насильная инициализация списка. Вообщем-то не очень хорошая практика так делать
        Hibernate.initialize(result.getFamilyList());
        session.getTransaction().commit();
        session.close();
        return result;
    }

    @Override
    public int updatePerson(Person p) {
        int ret = 1;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            session.saveOrUpdate(p);
            session.getTransaction().commit();
        } catch (Throwable ex) {
            ret = 0;
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Ошибка при обновлении", JOptionPane.OK_OPTION);
        }
        session.close();
        System.out.println(ret);
        return ret;
    }

    @Override
    public int updateFamilyList(Person p, Family families) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        p.getFamilyList().add(families);
        session.saveOrUpdate(p);
        session.getTransaction().commit();
        session.close();
        return 0;
    }

    public PersonData findPersonDataById(int personId) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        PersonData personData = session.get(PersonData.class, personId);
        session.getTransaction().commit();
        session.close();
        return personData;
    }

    @Override
    public void deletePerson(Person p) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.delete(p);
        session.getTransaction().commit();
        session.close();
    }
}

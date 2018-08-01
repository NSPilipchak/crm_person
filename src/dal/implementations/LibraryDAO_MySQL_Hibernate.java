package dal.implementations;

import blogic.entity.*;
import dal.HibernateUtil;
import dal.interfaces.LibraryDAO;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.ArrayList;

/**
 * Created by hammer on 05.09.2017.
 */
public class LibraryDAO_MySQL_Hibernate implements LibraryDAO {
    @Override
    public int addCognate(Cognate c) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        int ret = (int) session.save(c);
        session.getTransaction().commit();
        session.close();
        return ret;
    }

    @Override
    public void updateCognate(Cognate c) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.saveOrUpdate(c);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void deleteCognate(Cognate c) {

    }

    @Override
    public Cognate getCognate(int cognateId) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Cognate result = session.get(Cognate.class, cognateId);
        session.getTransaction().commit();
        session.close();
        return result;
    }

    @Override
    public ArrayList<Cognate> getCognateList() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        ArrayList<Cognate> cognateArrayList = (ArrayList<Cognate>) session.createQuery("from Cognate order by id").list();
        session.getTransaction().commit();
        session.close();
        return cognateArrayList;
    }

    @Override
    public int addDistrict(District c) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        int ret = (int) session.save(c);
        session.getTransaction().commit();
        session.close();
        return ret;
    }

    @Override
    public void updateDistrict(District c) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.saveOrUpdate(c);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void deleteDistrict(District c) {

    }

    @Override
    public District getDistrict(int districtId) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        District result = session.get(District.class, districtId);
        session.getTransaction().commit();
        session.close();
        return result;
    }

    @Override
    public ArrayList<District> getDistrictList() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        ArrayList<District> districtArrayList = (ArrayList<District>) session.createQuery("from District order by id").list();
        session.getTransaction().commit();
        session.close();
        return districtArrayList;
    }

    @Override
    public int addCity(City c) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        int ret = (int) session.save(c);
        session.getTransaction().commit();
        session.close();
        return ret;
    }

    @Override
    public void updateCity(City c) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.saveOrUpdate(c);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void deleteCity(City c) {

    }

    @Override
    public City getCity(int cityId) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        City result = session.get(City.class, cityId);
        session.getTransaction().commit();
        session.close();
        return result;
    }

    @Override
    public ArrayList<City> getCityList() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        ArrayList<City> districtArrayList = (ArrayList<City>) session.createQuery("from City order by id").list();
        session.getTransaction().commit();
        session.close();
        return districtArrayList;
    }

    @Override
    public ArrayList<App> getAppList() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        ArrayList<App> apps = (ArrayList<App>) session.createQuery("from App order by id").list();
        session.getTransaction().commit();
        session.close();
        return apps;
    }

    @Override
    public String getValueByKey(String key) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("from App where keyStr like :search");
        query.setParameter("search", key);
        ArrayList<App> result = (ArrayList<App>) query.getResultList();
        session.getTransaction().commit();
        session.close();
        return result.get(0).getValueStr();
    }

    @Override
    public void updateApp(App a) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.saveOrUpdate(a);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public int addDistrictPassport(DistrictPassport c) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        int ret = (int) session.save(c);
        session.getTransaction().commit();
        session.close();
        return ret;
    }

    @Override
    public void updateDistrictPassport(DistrictPassport c) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.saveOrUpdate(c);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void deleteDistrictPassport(DistrictPassport c) {

    }

    @Override
    public DistrictPassport getDistrictPassport(int districtPassportId) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        DistrictPassport result = session.get(DistrictPassport.class, districtPassportId);
        session.getTransaction().commit();
        session.close();
        return result;
    }

    @Override
    public ArrayList<DistrictPassport> getDistrictPassportList() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        ArrayList<DistrictPassport> result = (ArrayList<DistrictPassport>) session.createQuery("from DistrictPassport order by id").list();
        session.getTransaction().commit();
        session.close();
        return result;
    }
}

package dal.interfaces;

import blogic.entity.*;

import java.util.ArrayList;

public interface LibraryDAO {

    int addCognate(Cognate c);
    void updateCognate(Cognate c);
    void deleteCognate(Cognate c);
    Cognate getCognate(int cognateId);
    ArrayList<Cognate> getCognateList();


    int addDistrict(District c);
    void updateDistrict(District c);
    void deleteDistrict(District c);
    District getDistrict(int districtId);
    ArrayList<District> getDistrictList();

    int addCity(City c);
    void updateCity(City c);
    void deleteCity(City c);
    City getCity(int cityId);
    ArrayList<City> getCityList();

    ArrayList<App> getAppList();
    String getValueByKey(String key);
    void updateApp(App a);

    int addDistrictPassport(DistrictPassport c);
    void updateDistrictPassport(DistrictPassport c);
    void deleteDistrictPassport(DistrictPassport c);
    DistrictPassport getDistrictPassport(int districtPassportId);
    ArrayList<DistrictPassport> getDistrictPassportList();
}
package dal.interfaces;

import blogic.entity.Smena;

import java.util.ArrayList;

/**
 * Created by hammer on 20.07.2017.
 */
public interface SmenaDAO {

    int addSmena(Smena s);

    void updateSmena(Smena s);

    void deleteSmena(Smena s);

    Smena getSmena(int smenaId);

    ArrayList<Smena> getAllSmena();

    ArrayList<Smena> getSmenaByPerson(int personId);

    ArrayList<Smena> getSmenaBySeason(ArrayList seasonId);

    ArrayList<Smena> getSmenaById(ArrayList smenaId);

    int changeCountSmena(int smenaId, int districtId, int count);

    int reCountSmena();
}


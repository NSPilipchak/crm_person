package view.mainPane;


import blogic.entity.PersonDMwithPersonData;

import java.util.ArrayList;

public final class NavigateCommand {

    ArrayList arrStatus;
    ArrayList arrSmena;
    ArrayList arrSeason;
    ArrayList arrVerification;
    NavigateButtonsSmena navigateButtonsSmena;
    PersonDMwithPersonData dm;
    private boolean find = false;
    private String search;
    private int searchParam;


    public NavigateCommand() {
        arrStatus = new ArrayList();
        arrSeason = new ArrayList();
    }

    public void setDm(PersonDMwithPersonData dm) {
        this.dm = dm;
    }

    public void setSearch(boolean find, String search, int searchParam) {
        this.find = find;
        this.search = search;
        this.searchParam = searchParam;
    }

    public boolean isFind() {
        return find;
    }

    public int getSearchParam() {
        return searchParam;
    }

    public String getSearch() {

        return search;
    }

    void updateSmena() {
        navigateButtonsSmena.update();
    }

    final ArrayList addStatusToArray(int status, ArrayList arrayList) {
        boolean deleted;
        if (arrayList.size() == 0) {
            arrayList.add(status);
        } else if (arrayList.size() > 0) {
            deleted = false;
            for (int i = 0; i < arrayList.size(); i++) {
                if ((int) arrayList.get(i) == status) {
                    if (arrayList.size() != 1)
                        arrayList.remove(i);
                    deleted = true;
                    break;
                }
            }
            if (!deleted) {
                arrayList.add(status);
            }
        }
        return arrayList;
    }

    final ArrayList clearStatusArray(ArrayList arrayList) {
        ArrayList ret = new ArrayList();
        ret.add(arrayList.get(0));
        return ret;
    }

    public ArrayList getArrStatus() {
        return arrStatus;
    }

    public ArrayList getArrSmena() {
        return arrSmena;
    }

    public ArrayList getArrVerification() {
        return arrVerification;
    }
}

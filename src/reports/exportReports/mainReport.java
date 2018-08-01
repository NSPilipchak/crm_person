package reports.exportReports;

import blogic.entity.SmenaDM;

/**
 * Created by hammer on 18.08.2017.
 */
public class mainReport {
    public static void main(String[] args) {
        SavePersonListFromSmena savePersonListFromSmena = new SavePersonListFromSmena();
        savePersonListFromSmena.setModal(true);
        savePersonListFromSmena.setVisible(true);
    }
}

package reports.importBase;

import dal.Factory;
import dal.implementations.SmenaDAO_MySQL_Hibernate;
import dal.interfaces.SmenaDAO;

import javax.swing.*;

import java.io.IOException;

import static reports.importBase.ImportFromXLSPerson.readPersonFromExcel;

/**
 * Created by hammer on 10.08.2017.
 */
public class Main {
    public static void main(String... args) throws IOException {

//        JFileChooser fd = new JFileChooser();
//        if ( fd.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
//        {
//            System.out.println("пришли к открытию файла");
//            readPersonFromExcel(fd.getSelectedFile(),0);
////                    System.out.println(Parser.parse(fd.getSelectedFile()));
//        }

//        System.out.print(Factory.getSmenaDAO().getSmena(56));
        SmenaDAO smenaDAO = new SmenaDAO_MySQL_Hibernate();
        System.out.print(smenaDAO.getSmena(56));

    }
}

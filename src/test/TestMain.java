package test;

import blogic.entity.Person;
import blogic.entity.PersonDMwithPersonData;
import blogic.entity.Smena;
import dal.Factory;
import dal.HibernateUtil;
import de.nikem.dataj.swing.DisplayLengthSelectionPanel;
import de.nikem.dataj.swing.PaginationPanel;
import de.nikem.dataj.swing.ServerTableRowSorter;
import org.hibernate.*;
import org.hibernate.query.Query;

import javax.swing.*;
import javax.swing.RowSorter.*;
import javax.swing.event.RowSorterEvent;
import javax.swing.event.RowSorterListener;
import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by hammer on 23.08.2017.
 */
public class TestMain {

//    public static void main(String[] args) {
//        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
//        Transaction tx = session.beginTransaction();
//
//        Query query = session.createQuery("from Person order by id");
//
//        ScrollableResults personResults = (ScrollableResults) query
//                .setCacheMode(CacheMode.IGNORE)
//                .scroll(ScrollMode.FORWARD_ONLY);
//        int count = 0;
//        while (personResults.next()) {
//            Person person = (Person) personResults.get(0);
//            System.out.println(person);
//
//            if(++count % 20 == 0)
//            {
//                session.flush();
//                session.clear();
//            }
//        }
//
//        tx.commit();
//        session.close();
//        System.out.println("List finished.");
//    }

//    public static void main(String[] args) {
//        PersonDMwithPersonData dm = new PersonDMwithPersonData();
//        ServerTableRowSorter sorter = new ServerTableRowSorter(25, dm,
//                new String[]{
//                        "FIRST_NAME",
//                        "LAST_NAME",
//                        "BIRTHDAY"
//                });
//        // initial sorting
//        List<SortKey> initialSortKeys = new ArrayList<SortKey>();
//        initialSortKeys.add(new SortKey(0, SortOrder.ASCENDING));
//        sorter.setSortKeys(initialSortKeys);
//
//        // pagination panel. place it below the table or wherever you want
//        PaginationPanel paginationPanel = new PaginationPanel(sorter);
//
//        // page display length selection combobox. place it above the table...
//        DisplayLengthSelectionPanel dlsPanel = new DisplayLengthSelectionPanel(sorter);
//
//        // table and table model
//
//        JTable table = new JTable(dm);
//        table.setRowSorter(sorter);
//        sorter.addRowSorterListener(new RowSorterListener() {
//            @Override
//            public void sorterChanged(RowSorterEvent e) {
//                Map<String, String[]> parameters = sorter.getParameters();
//                // send parameters to server or call pagination select directly...
//
//                // e.g....
//                ListPage<MyDto> page = getDaoFactory().getMyDao().selectPage(dataSource, parameters);
//                dm.setTableData(page);
//                paginationPanel.update(page);	//updates pagination text (1 to 25 records of totally 387) and previous/next button enabling
//            }
//        });
//
//
//    }

}

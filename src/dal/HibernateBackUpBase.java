package dal;

import dal.HibernateUtil;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.XMLWriter;
import org.hibernate.EntityMode;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 * Created by hammer on 07.09.2017.
 */
public class HibernateBackUpBase {

    public HibernateBackUpBase() {
        String backupFolder = "./backup/";
        String table = "Person";
        try {
            dump(backupFolder, table);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void dump(String backupFolder, String table) throws IOException {
        Document document = DocumentHelper.createDocument();
        Element rootElement = document.addElement(table);

        Iterator<Element> it = queryDOM4J("from " + table).iterator();
        while (it.hasNext()) {
            Element element = it.next();
            rootElement.add(element);
        }

        saveDocument(document, backupFolder, table);
    }

    public List queryDOM4J(String hsql) {
        Transaction tx = null;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Session dom4jSession = HibernateUtil.getSessionFactory().getCurrentSession();
//        Session dom4jSession = session.getSession(EntityMode.DOM4J);  // функция удалена с хибернейт 4
        List list = null;
        try {
            tx = session.beginTransaction();
            list = dom4jSession.createQuery(hsql).list();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return list;
    }

    protected void saveDocument(Document document, String path, String table) throws IOException {
        if (canWrite(path)) {
            File file = new File(getDumpFile(path, table));
            XMLWriter writer = new XMLWriter(new FileWriter(file));
            writer.write(document);
            writer.close();
        }
    }

    protected boolean canWrite(String path) {
        File dir = new File(path);
        if (!dir.exists()) {
            boolean makeDir = dir.mkdirs();
            if (!makeDir) {
                System.out.println("Unable to create directory: " + path);
            }
        }

        return dir.exists();
    }

    protected String getDumpFile(String path, String tableName) {
        return path + "/" + tableName + ".xml";
    }
}

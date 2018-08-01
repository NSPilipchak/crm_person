package dal;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.yaml.snakeyaml.Yaml;

/**
 * Created by hammer on 07.09.2017.
 */
public class JpaBackup {
    public JpaBackup(EntityManagerFactory emf) {
        this.emf = emf;
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void dump(String backupFolder, Class clazz) {

        if (this.canWrite(backupFolder)) {

            EntityManager em = getEntityManager();

            try {

                Query q = em.createQuery("from " + clazz.getSimpleName());

                List it = q.getResultList();

                Logger.getLogger(JpaBackup.class).log(Level.DEBUG, "backup " + it.size() + "items");

                Yaml yaml = new Yaml();

                try (FileWriter fw = new FileWriter(this.getDumpFile(backupFolder, clazz))) {
                    yaml.dump(it, fw);
                } catch (IOException ex) {
                    java.util.logging.Logger.getLogger(JpaBackup.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                }

            } finally {
                em.close();
            }

        }

    }

    protected boolean canWrite(String path) {
        File dir = new File(path);
        if (!dir.exists()) {
            boolean makeDir = dir.mkdirs();
            if (!makeDir) {
                System.out.println("Unable to create directory: "+path);
            }
        }

        return dir.exists();
    }

    protected String getDumpFile(String path, Class clazz) {
        String tableName = clazz.getSimpleName();
        return path + "/"+tableName + ".yml";
    }
}
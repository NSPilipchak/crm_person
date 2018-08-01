package blogic.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "app")
public class App implements Comparable<App>, Serializable {

    @Id
    @Column(name = "app_id", unique = true, length = 10)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "app_gen")
    @SequenceGenerator(name = "app_gen", sequenceName = "app_sequence", allocationSize = 1)
    private int id;
    @Column(unique = true, nullable = false, length = 250)
    private String keyStr;
    @Column(length = 250)
    private String valueStr;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKeyStr() {
        return keyStr;
    }

    public void setKeyStr(String keyStr) {
        this.keyStr = keyStr;
    }

    public String getValueStr() {
        return valueStr;
    }

    public void setValueStr(String valueStr) {
        this.valueStr = valueStr;
    }

    @Override
    public int compareTo(App o) {
        return keyStr.compareToIgnoreCase(o.keyStr);
    }
}

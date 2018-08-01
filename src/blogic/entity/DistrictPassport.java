package blogic.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "districtPassport")
public class DistrictPassport implements Comparable<DistrictPassport>, Serializable {

    @Id
    @Column(name = "distrPassp_id", unique = true, length = 10)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "districtPassport_gen")
    @SequenceGenerator(name = "districtPassport_gen", sequenceName = "districtPassport_sequence", allocationSize = 1)
    private int id;

    @Column(unique = true, nullable = false, length = 50)
    private String districtPassport;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDistrictPassport() {
        return districtPassport;
    }

    public void setDistrictPassport(String districtPassport) {
        this.districtPassport = districtPassport;
    }

    @Override
    public String toString() {
        return "DistrictPassport{" +
                "id='" + id + '\'' +
                ", DistrictPassport='" + districtPassport + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DistrictPassport cognate1 = (DistrictPassport) o;

        if (id != cognate1.id) return false;
        return districtPassport != null ? districtPassport.equals(cognate1.districtPassport) : cognate1.districtPassport == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (districtPassport != null ? districtPassport.hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo(DistrictPassport o) {
        return districtPassport.compareToIgnoreCase(o.districtPassport);
    }
}

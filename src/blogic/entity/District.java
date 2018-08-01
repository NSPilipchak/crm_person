package blogic.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by hammer on 11.09.2017.
 */
@Entity
@Table(name = "district")
public class District implements Comparable<District>, Serializable {

    @Id
    @Column(name = "district_id", unique = true, length = 10)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "district_gen")
    @SequenceGenerator(name = "district_gen", sequenceName = "district_sequence", allocationSize = 1)
    private int id;

    @Column(unique = true, nullable = false, length = 50)
    private String district;

    @Column(nullable = false, length = 16)
    private int quota;

    @Column(length = 2, nullable = false)
    private int status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public int getQuota() {
        return quota;
    }

    public void setQuota(int quota) {
        this.quota = quota;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        District district1 = (District) o;

        if (id != district1.id) return false;
        if (quota != district1.quota) return false;
        if (status != district1.status) return false;
        return district != null ? district.equals(district1.district) : district1.district == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (district != null ? district.hashCode() : 0);
        result = 31 * result + quota;
        result = 31 * result + status;
        return result;
    }

    @Override
    public int compareTo(District o) {
        return district.compareToIgnoreCase(o.district);
    }
}

package blogic.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by hammer on 11.09.2017.
 */
@Entity
@Table(name = "city")
public class City implements Comparable<City>, Serializable {

    @Id
    @Column(name = "city_id", unique = true, length = 10)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "city_gen")
    @SequenceGenerator(name = "city_gen", sequenceName = "city_sequence", allocationSize = 1)
    private int id;

    @Column(unique = true, nullable = false, length = 50)
    private String city;

    @Column(length = 2, nullable = false)
    private int status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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

        City city1 = (City) o;

        if (id != city1.id) return false;
        if (status != city1.status) return false;
        return city != null ? city.equals(city1.city) : city1.city == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + status;
        return result;
    }

    @Override
    public int compareTo(City o) {
        return city.compareToIgnoreCase(o.city);
    }
}

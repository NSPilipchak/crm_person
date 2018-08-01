package blogic.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "cognate")
public class Cognate implements Comparable<Cognate>, Serializable {

    @Id
    @Column(name = "cognate_id", unique = true, length = 10)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cognate_gen")
    @SequenceGenerator(name = "cognate_gen", sequenceName = "cognate_sequence", allocationSize = 1)
    private int id;

    @Column(unique = true, nullable = false, length = 15)
    private String cognate;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCognate() {
        return cognate;
    }

    public void setCognate(String cognate) {
        this.cognate = cognate;
    }

    @Override
    public String toString() {
        return "Cognate{" +
                "id='" + id + '\'' +
                ", cognate='" + cognate + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cognate cognate1 = (Cognate) o;

        if (id != cognate1.id) return false;
        return cognate != null ? cognate.equals(cognate1.cognate) : cognate1.cognate == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (cognate != null ? cognate.hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo(Cognate o) {
        return cognate.compareToIgnoreCase(o.cognate);
    }
}

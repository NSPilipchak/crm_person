package blogic.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * Created by hammer on 14.07.2017.
 * <p>
 * **********КЛАСС СУЩНОСТИ СМЕНА************
 */
@Entity
@Table(name = "smena")
public class Smena implements Comparable<Smena>, Serializable {
    public Smena() {
    }

    @Id
    @Column(name = "smena_id", unique = true)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "smena_gen")
    @SequenceGenerator(name = "smena_gen", sequenceName = "smena_sequence", allocationSize = 1)
    private int id;
    @Column(unique = true, nullable = false, length = 50)
    private String name;
    @Column(nullable = false, length = 2)
    private int number;
    @Column(nullable = false, length = 4)
    private int places;
    @Column(nullable = false, length = 4)
    private int year;
    @Column(nullable = false, length = 10)
    private String dateStart;
    @Column(nullable = false, length = 10)
    private String dateEnd;
    @Column(nullable = false, length = 2)
    private int status;
    @Column(length = 200)
    private String comment;
    @Column(nullable = false, length = 6, columnDefinition = "int default 0")
    private int busyPlaces;    // добавить в формы присоение данных
    @Column(nullable = false, length = 250)
    private String busyPlacesByDistrict; // ложим значения в джейсоне - или может все же по столбцам разбить???


    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY) //EAGER - принудительно выгружает все связи
//    @Fetch(FetchMode.SUBSELECT)
    @JoinTable(name = "PERSON_SMENA",
            joinColumns = @JoinColumn(name = "smena_id"),
            inverseJoinColumns = @JoinColumn(name = "person_id"))
    private Set<Person> personList;

    public Set<Person> getPersonList() {
        return personList;
    }

    public void setPersonList(Set<Person> personList) {
        this.personList = personList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPlaces() {
        return places;
    }

    public void setPlaces(int places) {
        this.places = places;
    }

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getBusyPlaces() {
        return busyPlaces;
    }

    public void setBusyPlaces(int busyPlaces) {
        this.busyPlaces = busyPlaces;
    }

    public String getBusyPlacesByDistrict() {
        return busyPlacesByDistrict;
    }

    public void setBusyPlacesByDistrict(String busyPlacesByDistrict) {
        this.busyPlacesByDistrict = busyPlacesByDistrict;
    }

    @Override
    public String toString() {
        return "Smena{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", number=" + number +
                ", places=" + places +
                ", year=" + year +
                ", dateStart='" + dateStart + '\'' +
                ", dateEnd='" + dateEnd + '\'' +
                ", status=" + status +
                ", comment='" + comment + '\'' +
                '}';
    }

    @Override
    public int compareTo(Smena o) {
        return name.compareToIgnoreCase(o.name);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Smena smena = (Smena) o;

        if (id != smena.id) return false;
        if (number != smena.number) return false;
        if (places != smena.places) return false;
        if (year != smena.year) return false;
        if (status != smena.status) return false;
        if (name != null ? !name.equals(smena.name) : smena.name != null) return false;
        if (dateStart != null ? !dateStart.equals(smena.dateStart) : smena.dateStart != null) return false;
        return dateEnd != null ? dateEnd.equals(smena.dateEnd) : smena.dateEnd == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + number;
        result = 31 * result + year;
        return result;
    }
}

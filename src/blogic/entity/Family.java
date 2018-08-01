package blogic.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * Created by hammer on 14.07.2017.
 * <p>
 * **********КЛАСС СУЩНОСТИ РОДСТВЕННИКИ ОТДЫХАЮЩЕГО***********
 */

@Entity
@Table(name = "families")
public class Family implements Comparable<Family>, Serializable {
    public Family() {
    }

    @Id
    @Column(name = "family_id", unique = true)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "families_gen")
    @SequenceGenerator(name = "families_gen", sequenceName = "families_sequence", allocationSize = 1)
    private int id;
    @Column(nullable = false, length = 50)
    private String name;
    @Column(length = 10)
    private String bDate;
    @Column(nullable = false, length = 17)
    private String cognate;                                                 //родственная связь
    @Column(nullable = false, length = 17)
    private String fPhone;
    @Column(length = 17)
    private String lPhone;
    @Column(length = 50)
    private String email;
    @Column(nullable = false, length = 1)
    private int verificationDocum;
    @Column(length = 2, nullable = false)
    private int status;
    @Column(length = 200)
    private String comment;

    /**
     * привязка таблицы Персон
     */

    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinTable(name = "PERSON_FAMILY",
            joinColumns = @JoinColumn(name = "family_id"),
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

    public String getCognate() {
        return cognate;
    }

    public void setCognate(String cognate) {
        this.cognate = cognate;
    }

    public String getfPhone() {
        return fPhone;
    }

    public void setfPhone(String fPhone) {
        this.fPhone = fPhone;
    }

    public String getlPhone() {
        return lPhone;
    }

    public void setlPhone(String lPhone) {
        this.lPhone = lPhone;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getVerificationDocum() {
        return verificationDocum;
    }

    public void setVerificationDocum(int verificationDocum) {
        this.verificationDocum = verificationDocum;
    }

    public String getbDate() {
        return bDate;
    }

    public void setbDate(String bDate) {
        this.bDate = bDate;
    }

    @Override
    public String toString() {
        return "Family{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", bDate='" + bDate + '\'' +
                ", cognate='" + cognate + '\'' +
                ", fPhone='" + fPhone + '\'' +
                ", lPhone='" + lPhone + '\'' +
                ", email='" + email + '\'' +
                ", verificationDocum=" + verificationDocum +
                ", status=" + status +
                ", comment='" + comment + '\'' +
                '}';
    }

    @Override
    public int compareTo(Family o) {
        return name.compareToIgnoreCase(o.name);
    }
}

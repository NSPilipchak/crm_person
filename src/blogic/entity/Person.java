package blogic.entity;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by hammer on 14.07.2017.
 * <p>
 * *********КЛАСС СУЩНОСТИ КАРТОЧКА ОТДЫХАЮЩЕГО*************
 */

@Entity
@Table(name = "person")
public class Person implements Comparable<Person>, Serializable {
    public Person() {
    }

    @Id
    @Column(name = "person_id", unique = true)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "persons_gen")
    @SequenceGenerator(name = "persons_gen", sequenceName = "persons_sequence", allocationSize = 1)
    private int id;
    @Column(nullable = false, length = 11) //unique = true,
    private String code;
    @Column(length = 50)
    private String createInfo;
    @Column(length = 20)
    private String createUser;
    @Column(name = "firstName", nullable = false, length = 70)
    private String fName;
    @Column(name = "midleName",nullable = false, length = 70)
    private String mName;
    @Column(name = "lastName",nullable = false, length = 70)
    private String lName;
    @Column(nullable = false, length = 10)
    private String bDate;
    @Column(nullable = false, length = 17)
    private String fPhone;
    @Column(length = 17)
    private String lPhone;
    @Column(length = 17)
    private String homePhone;
    @Column(unique = true, nullable = false, length = 10)
    private String inn;
    @Column(length = 50)
    private String email;
    @Column(length = 250)
    private String comment;
    @Column(length = 2, nullable = false)
    private int status; // 0 - Удаленный; 1 - Активный; 2 - черный список; 3 - ВИП

    /**
     * Связные таблицы относящиеся к персоне:
     * <p>
     * 1. Лист родственников
     * 2. Лист Визитов
     * 3. Персональные данные
     */

    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @Fetch(FetchMode.SELECT)
    @JoinTable(name = "PERSON_FAMILY",
            joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "family_id"))
    private Set<Family> familyList = new HashSet<>();

    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @JoinTable(name = "PERSON_SMENA",
            joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "smena_id"))
    private Set<Smena> smenasList = new HashSet<>();

    @Column(name = "fk_pd_id", nullable = false, insertable = false, updatable = false)
    private int personDataId;
    @OneToOne
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "fk_pd_id", referencedColumnName = "personData_id")
    private PersonData personData;


    public int getPersonDataId() {
        return personDataId;
    }

    public void setPersonDataId(int personDataId) {
        this.personDataId = personDataId;
    }

    public PersonData getPersonData() {
        return personData;
    }

    public void setPersonData(PersonData personData) {
        this.personData = personData;
    }

    public Set<Family> getFamilyList() {
        return familyList;
    }

    public void setFamilyList(Set<Family> familyList) {
        this.familyList = familyList;
    }


    public Set<Smena> getSmenasList() {
        return smenasList;
    }

    public void setSmenasList(Set<Smena> smenasList) {
        this.smenasList = smenasList;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getbDate() {
        return bDate;
    }

    public void setbDate(String bDate) {
        this.bDate = bDate;
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

    public String getInn() {
        return inn;
    }

    public void setInn(String inn) {
        this.inn = inn;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    public String getCreateInfo() {
        return createInfo;
    }

    public void setCreateInfo(String createInfo) {
        this.createInfo = createInfo;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    @Override
    public String toString() {
        return "\nPerson{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", createInfo='" + createInfo + '\'' +
                ", createUser='" + createUser + '\'' +
                ", fName='" + fName + '\'' +
                ", mName='" + mName + '\'' +
                ", lName='" + lName + '\'' +
                ", bDate='" + bDate + '\'' +
                ", fPhone='" + fPhone + '\'' +
                ", lPhone='" + lPhone + '\'' +
                ", homePhone='" + homePhone + '\'' +
                ", inn='" + inn + '\'' +
                ", email='" + email + '\'' +
                ", comment='" + comment + '\'' +
                ", status=" + status +
                ", personData=" + personData +
//                ", familyList=" + familyList +
//                ", smenasList=" + smenasList +
                '}';
    }

    @Override
    public int compareTo(Person o) {
        return inn.compareToIgnoreCase(o.inn);
    }
}

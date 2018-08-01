package blogic.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by hammer on 14.07.2017.
 * <p>
 * *********КЛАСС СУЩНОСТИ КАРТОЧКА ОТДЫХАЮЩЕГО*************
 */

@Entity
@Table(name = "personData")
public class PersonData implements Comparable<PersonData>, Serializable {
    public PersonData() {
    }

    @Id
    @Column(name = "personData_id", unique = true, nullable = false, insertable = true, updatable = true)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "personData_gen")
    @SequenceGenerator(name = "personData_gen", sequenceName = "personData_sequence", allocationSize = 1)
    private int id;
    @Column(unique = true, nullable = false, length = 10)
    private String sPassport;
    @Column(nullable = false, length = 10)
    private String dPassport;
    @Column(nullable = false, length = 50)
    private String aPassport;
    @Column(nullable = false, length = 2)
    private int city;
    @Column(nullable = false, length = 2)
    private int district;
    @Column(nullable = false, length = 50)
    private String street;
    @Column(nullable = false, length = 10)
    private String house;
    @Column(length = 6)
    private String corp;
    @Column(length = 8)
    private String flat;
    @Column(length = 200)
    private String oldAdres;
    @Column(length = 16) // по договоренности с Николаем может быть не уникальным //unique = true,
    private String pensionNum;
    @Column(nullable = false, length = 1)
    private int verificationDocum;
    @Column(length = 200)
    private String comment;
    @Column(length = 2, nullable = false)
    private int status;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "personData", fetch = FetchType.LAZY)
    private Person person;


    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        person.setPersonData(this);
        this.person = person;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getsPassport() {
        if (sPassport.length() > 3 && sPassport.substring(2, 3).equals(" ")) {
            return sPassport.substring(0, 2) + sPassport.substring(3, 9);
        }
        return sPassport;
    }

    public void setsPassport(String sPassport) {
        this.sPassport = sPassport;
    }

    public String getdPassport() {
        return dPassport;
    }

    public void setdPassport(String dPassport) {
        this.dPassport = dPassport;
    }

    public String getaPassport() {
        return aPassport;
    }

    public void setaPassport(String aPassport) {
        this.aPassport = aPassport;
    }

    public int getCity() {
        return city;
    }

    public void setCity(int city) {
        this.city = city;
    }

    public int getDistrict() {
        return district;
    }

    public void setDistrict(int district) {
        this.district = district;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getCorp() {
        return corp;
    }

    public void setCorp(String corp) {
        this.corp = corp;
    }

    public String getFlat() {
        return flat;
    }

    public void setFlat(String flat) {
        this.flat = flat;
    }

//    public String getInn() {
//        return inn;
//    }
//
//    public void setInn(String inn) {
//        this.inn = inn;
//    }

    public String getPensionNum() {
        return pensionNum;
    }

    public void setPensionNum(String pensionNum) {
        this.pensionNum = pensionNum;
    }

    public int getVerificationDocum() {
        return verificationDocum;
    }

    public void setVerificationDocum(int verificationDocum) {
        this.verificationDocum = verificationDocum;
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

    public String getOldAdres() {
        return oldAdres;
    }

    public void setOldAdres(String oldAdres) {
        this.oldAdres = oldAdres;
    }

    @Override
    public String toString() {
        return "PersonData{" +
                "id=" + id +
                ", sPassport='" + sPassport + '\'' +
                ", dPassport='" + dPassport + '\'' +
                ", aPassport='" + aPassport + '\'' +
                ", city=" + city +
                ", district=" + district +
                ", street='" + street + '\'' +
                ", house='" + house + '\'' +
                ", corp='" + corp + '\'' +
                ", flat='" + flat + '\'' +
                ", oldAdres='" + oldAdres + '\'' +
                ", pensionNum='" + pensionNum + '\'' +
                ", verificationDocum=" + verificationDocum +
                ", comment='" + comment + '\'' +
                ", status=" + status +
                '}';
    }

    @Override
    public int compareTo(PersonData o) {
        return sPassport.compareToIgnoreCase(o.sPassport);
    }
}

package nl.first8.library.domain;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "member")
@SequenceGenerator(name="member_id_seq", initialValue=1, allocationSize=1000)
public class Member {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="member_id_seq")
    private Long id;

    @Column(name = "firstName", nullable = false)
    private String firstName;

    @Column(name = "lastName", nullable = false)
    private String lastName;

    @Column(name = "street", nullable = false)
    private String street;

    @Column(name = "houseNumber", nullable = false)
    private Integer houseNumber;

    @Column(name = "houseNumberSuffix")
    private String houseNumberSuffix;

    @Column(name = "postalCode", nullable = false)
    private String postalCode;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "country", nullable = false)
    private String country;

    @Column(name = "disabled", columnDefinition = "boolean default false")
    private boolean disabled;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getStreet() {
        return street;
    }
    public void setStreet(String street) {
        this.street = street;
    }
    public Integer getHouseNumber() { return houseNumber;}
    public void setHouseNumber(Integer houseNumber) {
        this.houseNumber = houseNumber;
    }
    public String getHouseNumberSuffix() {
        return houseNumberSuffix;
    }
    public void setHouseNumberSuffix(String houseNumberSuffix) {
        this.houseNumberSuffix = houseNumberSuffix;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public boolean isDisabled() {
        return disabled;
    }
    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }
    public String getPostalCode() {
        return postalCode;
    }
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
}

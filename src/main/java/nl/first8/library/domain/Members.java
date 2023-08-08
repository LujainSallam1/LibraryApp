package nl.first8.library.domain;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "member")
@SequenceGenerator(name="member_id_seq", initialValue=11, allocationSize=1000)
public class Members {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="memeber_id_seq")
    private Long id;

    @OneToMany
    private Set<Book> book;

    @OneToMany
    private Set<BluRay> bluray;

    @OneToMany
    private Set<ComicBook> comicBook;

    @Column(name = "first_last_name", nullable = false)
    private String name;

    @Column(name = "zip_code", nullable = false)
    private String zipCode;

    @Column(name = "street_name", nullable = false)
    private String street;

    @Column(name = "house_number", nullable = false)
    private String houseNumber;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "products", nullable = false)
    private Integer Products = 8;

    @Column(name = "active", columnDefinition = "boolean default true")
    private Boolean Active;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Book> getBook() {
        return book;
    }

    public void setBook(Set<Book> book) {
        this.book = book;
    }

    public Set<BluRay> getBluray() {
        return bluray;
    }

    public void setBluray(Set<BluRay> bluray) {
        this.bluray = bluray;
    }

    public Set<ComicBook> getComicBook() {
        return comicBook;
    }

    public void setComicBook(Set<ComicBook> comicBook) {
        this.comicBook = comicBook;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getProducts() {
        return Products;
    }

    public void setProducts(Integer products) {
        Products = products;
    }

    public Boolean getActive() {
        return Active;
    }

    public void setActive(Boolean active) {
        Active = active;
    }
}

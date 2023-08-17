package nl.first8.library.domain.entity;

import nl.first8.library.domain.entity.Book;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "member")
@SequenceGenerator(name="members_id_seq", initialValue=11, allocationSize=1000)
public class Member {
    @OneToMany
    private List<Book> borrowedbooks;

    public List<Book> getBorrowedbooks() {
        return borrowedbooks;
    }

    public void setBorrowedbooks(List<Book> borrowedbooks) {
        this.borrowedbooks = borrowedbooks;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "member_id_seq")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "adress", nullable = false)
    private String adress;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "maxBorrowableProducts" ,columnDefinition = "integer default 5")
    private int maxBorrowableProducts = 5;

    @Column(name = "disable", columnDefinition = "boolean default true")
    private boolean disable;



    public void setDisable(boolean disable) {
        this.disable = disable;
    }

    public int getMaxBorrowableProducts() {
        return maxBorrowableProducts;
    }

    public void setMaxBorrowableProducts(int maxBorrowableProducts) {
        this.maxBorrowableProducts = maxBorrowableProducts;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isdisable() {
        return disable;
    }

}

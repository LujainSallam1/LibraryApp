package nl.first8.library.domain;

import javax.persistence.*;
import java.util.Set;

import net.minidev.json.annotate.JsonIgnore;
import nl.first8.library.domain.Book;

@Entity
@Table(name = "member")
@SequenceGenerator(name="members_id_seq", initialValue=11, allocationSize=1000)
public class Member {
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "borrowedBy")
    private Set<Book> borrowedbooks;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "member_id_seq")
    private Long id;

    @Column(name = "naam", nullable = false)
    private String naam;

    @Column(name = "adres", nullable = false)
    private String adres;

    @Column(name = "woonplaats", nullable = false)
    private String woonplaats;

    @Column(name = "maxLeenbaarProducten" ,columnDefinition = "integer default 5")
    private int maxLeenbaarProducten = 5;

    @Column(name = "disable", columnDefinition = "boolean default true")
    private boolean disable;



    public void setDisable(boolean disable) {
        this.disable = disable;
    }

    public int getMaxLeenbaarProducten() {
        return maxLeenbaarProducten;
    }

    public void setMaxLeenbaarProducten(int maxLeenbaarProducten) {
        this.maxLeenbaarProducten = maxLeenbaarProducten;
    }

    public String getWoonplaats() {
        return woonplaats;
    }

    public void setWoonplaats(String woonplaats) {
        this.woonplaats = woonplaats;
    }

    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public boolean isdisable() {
        return disable;
    }

    public Set<Book> getBorrowedbooks() {
        return borrowedbooks;
    }

    public void setBorrowedbooks(Set<Book> borrowedbooks) {
        this.borrowedbooks = borrowedbooks;
    }


}

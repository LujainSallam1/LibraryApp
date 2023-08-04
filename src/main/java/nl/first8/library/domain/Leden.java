package nl.first8.library.domain;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "lid")
@SequenceGenerator(name="leden_id_seq", initialValue=11, allocationSize=1000)
public class Leden {
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Book> borrowedbooks;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Bluerays> Blueraysbooks;

    public Set<Bluerays> getBlueraysbooks() {
        return Blueraysbooks;
    }

    public void setBlueraysbooks(Set<Bluerays> blueraysbooks) {
        Blueraysbooks = blueraysbooks;
    }

    public Set<Book> getBorrowedbooks() {
        return borrowedbooks;
    }

    public void setBorrowedbooks(Set<Book> borrowedbooks) {
        this.borrowedbooks = borrowedbooks;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lid_id_seq")
    private Long id;

    @Column(name = "naam", nullable = false)
    private String naam;

    @Column(name = "adres", nullable = false)
    private String adres;

    @Column(name = "woonplaats", nullable = false)
    private String woonplaats;

    @Column(name = "maxLeenbaarProducten")
    private int maxLeenbaarProducten = 5;

    @Column(name = "disable")
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

}

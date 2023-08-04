package nl.first8.library.domain;


import javax.persistence.*;
import java.awt.*;
import java.util.Set;


@Entity
@Table(name = "lid")
@SequenceGenerator(name="lid_id_seq")
public class Lid {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="lid_id_seq")
    private Long id;

    @Column(name = "voornaam", nullable = false)
    private String voornaam;

    @Column(name = "achternaam", nullable = false)
    private String achternaam;

    @Column(name = "adress", nullable = false)
    private String adress;

    @Column(name = "woonplaats", nullable = false)
    private String woonplaats;

    @Column(name = "maximum_leenbare_producten", nullable = false)
    private Long maxLeenbareProducten = 3L;

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;

    @JoinColumn(name = "borrowed_books")
    @OneToMany()
    private Set<Book> borrowedBooks;


    public Long getId() {
        return id;
    }

    public void setId( Long id ) {
        this.id = id;
    }

    public String getVoornaam() {
        return this.voornaam;
    }

    public void setVoornaam(String voornaam) {
        this.voornaam = voornaam;
    }

    public String getAchternaam() {
        return this.achternaam;
    }

    public void setAchternaam(String achternaam) {
        this.achternaam = achternaam;
    }

    public String getAdress() {
        return this.adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getWoonplaats() {
        return this.woonplaats;
    }

    public void setWoonplaats(String woonplaats) {
        this.woonplaats = woonplaats;
    }

    public void setMaxLeenbareProducten(Long num) {
        this.maxLeenbareProducten = num;
    }

    public Long getMaxLeenbareProducten() {
        return this.maxLeenbareProducten;
    }

    public boolean isActive() {return this.isActive;}
    public void setActive(boolean active) {this.isActive = active;}

    public Set<Book> getBorrowedBooks() {
        return this.borrowedBooks;
    }

    public void addBorrowedBook(Book book) {
        this.borrowedBooks.add(book);
    }

    public void removeBorrowedBook(Book book) {
        this.borrowedBooks.remove(book);
    }
}

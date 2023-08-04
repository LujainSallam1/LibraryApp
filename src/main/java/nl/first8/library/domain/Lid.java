package nl.first8.library.domain;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "lid")
@SequenceGenerator(name="lid_id_seq", initialValue=4, allocationSize=1000)

public class Lid {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lid_id_seq")
    private Long id;

    @Column(name = "naam", nullable = false)
    private String naam;

    @Column(name = "adres", nullable = false)
    private String adres;

    @Column(name = "woonplaats", nullable = false)
    private String woonplaats;

    @Column(name = "maxProducts", columnDefinition = "integer default 5")
    private int maxProducts;

    @Column(name = "active", columnDefinition = "boolean default true")
    private boolean active;

    @PreRemove
    public void UserRemovalAttempt() {
        throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    }

    @OneToMany()
    @JoinColumn(name = "borrowedBooks")
    private Set<Book> borrowedBooks;

    @OneToMany()
    @JoinColumn(name = "borrowedBlurays")
    private Set<BluRay> borrowedBlurays;

    public Lid(){}
//    public Lid(Long id, String naam, String adres, String woonplaats, int maxProducts, boolean active, Set<Book> borrowedBooks, Set<BluRay> borrowedBlurays) {
//        this.id = id;
//        this.naam = naam;
//        this.adres = adres;
//        this.woonplaats = woonplaats;
//        this.maxProducts = maxProducts;
//        this.active = active;
//        this.borrowedBooks = borrowedBooks;
//        this.borrowedBlurays = borrowedBlurays;
//    }

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

    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    public String getWoonplaats() {
        return woonplaats;
    }

    public void setWoonplaats(String woonplaats) {
        this.woonplaats = woonplaats;
    }

    public int getMaxProducts() {
        return maxProducts;
    }

    public void setMaxProducts(int maxProducts) {
        this.maxProducts = maxProducts;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Set<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void setBorrowedBooks(Set<Book> borrowedBooks) {
        this.borrowedBooks = borrowedBooks;
    }

    public void addBook(Book book){
        this.borrowedBooks.add(book);
    }

    public void removeBook(Book book){
        this.borrowedBooks.remove(book);
    }

    public Set<BluRay> getBorrowedBlurays() {
        return borrowedBlurays;
    }

    public void setBorrowedBlurays(Set<BluRay> borrowedBlurays) {
        this.borrowedBlurays = borrowedBlurays;
    }

    public void addBluray(BluRay bluray){
        this.borrowedBlurays.add(bluray);
    }

    public void removeBluray(BluRay bluray){
        this.borrowedBlurays.remove(bluray);
    }
}





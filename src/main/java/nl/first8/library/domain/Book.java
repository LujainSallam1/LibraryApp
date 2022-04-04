package nl.first8.library.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "isbn", nullable = false)
    private String isbn;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "authors", nullable = false)
    private String authors;

    @Column(name = "publish_date", nullable = false)
    private LocalDate publishdDate;

    @Column(name = "borrowed", columnDefinition = "boolean default false")
    private boolean borrowed;

    public Long getId() {
        return id;
    }

    public void setId( Long id ) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn( String isbn ) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle( String title ) {
        this.title = title;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors( String authors ) {
        this.authors = authors;
    }

    public LocalDate getPublishdDate() {
        return publishdDate;
    }

    public void setPublishdDate( LocalDate publishdDate ) {
        this.publishdDate = publishdDate;
    }

    public boolean isBorrowed() {
        return borrowed;
    }

    public void setBorrowed( boolean borrowed ) {
        this.borrowed = borrowed;
    }
}

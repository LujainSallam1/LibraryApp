package nl.first8.library.domain.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;


@Entity
@Table(name = "book")
@SequenceGenerator(name="book_id_seq", initialValue=11, allocationSize=1000)
public class Book {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="book_id_seq")
    private Long id;

    @Column(name = "isbn", nullable = false)
    private String isbn;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "authors", nullable = false)
   private  String authors;

    @Column(name = "publish_date")
    private Date publishDate;

    @Column(name = "borrowed", columnDefinition = "boolean default false")
    private boolean borrowed;

    @Column(name = "summary")
    private String summary;
    @Column(name = "returnDate")
    private LocalDate returnDate;

    @Column(name = "outcheckDate")
    private LocalDate borrowDate;

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(LocalDate outcheckDate) {
        this.borrowDate = outcheckDate;
    }

    public void setReturnDate(LocalDate incheckDate) {
        this.returnDate = incheckDate;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

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

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishdDate ) {
        this.publishDate = publishdDate;
    }

    public boolean isBorrowed() {
        return borrowed;
    }

    public void setBorrowed( boolean borrowed ) {
        this.borrowed = borrowed;
    }
}

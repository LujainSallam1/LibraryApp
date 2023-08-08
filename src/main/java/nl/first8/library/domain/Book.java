package nl.first8.library.domain;

import javax.persistence.*;
import java.time.LocalDate;

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
    private String authors;

    @Column(name = "publish_date", nullable = false)
    private LocalDate publishDate;

    @Column(name = "borrowed", columnDefinition = "boolean default false")
    private boolean borrowed;

    @Column(name = "summary")
    private String summary;

//    @ManyToOne()
//    @JoinColumn(name = "lid_id", insertable = false, updatable = false)
//    private Lid lid;

    public Book(){}

//    public Book(String isbn, String title, String authors, LocalDate publishDate, boolean borrowed, String summary){
//        this.isbn = isbn;
//        this.title = title;
//        this.authors = authors;
//        this.publishDate = publishDate;
//        this.borrowed = borrowed;
//        this.summary = summary;
//    }

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

    public LocalDate getPublishDate() {
        return publishDate;
    }

    public void setPublishDate( LocalDate publishdDate ) {
        this.publishDate = publishdDate;
    }

    public boolean isBorrowed() {
        return borrowed;
    }

    public void setBorrowed( boolean borrowed ) {
        this.borrowed = borrowed;
    }

    public String getSummary(){
        return summary;
    }

    public void setSummary( String summary ) {
        this.summary = summary;
    }
}

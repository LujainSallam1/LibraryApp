package nl.first8.library.domain;

import javax.persistence.*;
import java.time.LocalDate;

@MappedSuperclass
public abstract class Product {

    @Id
    @GeneratedValue
    private Long id;



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

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

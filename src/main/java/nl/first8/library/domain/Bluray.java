package nl.first8.library.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "bluray")
@SequenceGenerator(name="bluray_id_seq", initialValue=11, allocationSize=1000)
public class Bluray {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="bluray_id_seq")
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "director", nullable = false)
    private String director;

    @Column(name = "publish_date", nullable = false)
    private LocalDate publishDate;

    @Column(name = "borrowed", columnDefinition = "boolean default false")
    private boolean borrowed;

    @Column(name = "summary")
    private String summary;

    public Long getBorrowerId() {
        return borrowerId;
    }

    @Column(name = "borrowerId")
    private Long borrowerId;

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle( String title ) {
        this.title = title;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector( String director ) {
        this.director = director;
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

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setBorrowerId(Long id) {

    }
}

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
@Table(name = "blueray")
@SequenceGenerator(name="blueray_id_seq")
public class BlueRay {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="blueray_id_seq")
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "authors", nullable = false)
    private String authors;

    @Column(name = "summary", nullable = true)
    private String summary;

    @Column(name = "publish_date", nullable = false)
    private LocalDate publishDate;


    public Long getId() {
        return id;
    }

    public void setId( Long id ) {
        this.id = id;
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

    public String getSummary() { return this.summary;}

    public void setSummary(String summary) {this.summary = summary;}

    public LocalDate getPublishDate() {
        return publishDate;
    }

    public void setPublishDate( LocalDate publishdDate ) {
        this.publishDate = publishdDate;
    }
}

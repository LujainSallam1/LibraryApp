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
public class BluRay {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="bluray_id_seq")
    private Long blurayid;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "directors", nullable = false)
    private String directors;

    @Column(name = "actors", nullable = false)
    private String actors;

    @Column(name = "release_date", nullable = false)
    private LocalDate releaseDate;

    @Column(name = "borrowed", columnDefinition = "boolean default false")
    private boolean borrowed;

    @Column(name = "summary", columnDefinition = "") // nullable = true
    private String summary;

    public Long getId() {
        return blurayid;
    }

    public void setId( Long blurayid ) {
        this.blurayid = blurayid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle( String title ) {
        this.title = title;
    }

    public String getDirectors() {
        return directors;
    }

    public void setDirectors( String directors ) { this.directors = directors; }

    public String getActors() {
        return actors;
    }

    public void setActors( String actors ) { this.actors = actors; }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setPublishDate( LocalDate ReleaseDate ) {
        this.releaseDate = releaseDate;
    }

    public boolean isBorrowed() {
        return borrowed;
    }

    public void setBorrowed( boolean borrowed ) {
        this.borrowed = borrowed;
    }

    public String getSummary() {return summary; }

    public void setSummary() { this.summary = summary; }
}

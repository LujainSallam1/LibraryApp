package nl.first8.library.domain;

import javax.persistence.*;

@Entity
@Table(name = "bluray")
@SequenceGenerator(name="bluray_id_seq", initialValue=11, allocationSize=1000)
public class BluRay {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bluray_id_seq")
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "PG", nullable = false)
    private String PG;

    @Column(name = "publisher", nullable = false)
    private String publisher;

    @Column(name = "duration", nullable = false)
    private String duration;

    @Column(name = "releasedate", nullable = false)
    private String releaseDate;

    @Column(name = "genre", nullable = false)
    private String genre;

    @Column(name = "borrowed", columnDefinition = "boolean default false")
    private boolean borrowed;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPG() {
        return PG;
    }

    public void setPG(String PG) {
        this.PG = PG;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public boolean isBorrowed() {
        return borrowed;
    }

    public void setBorrowed(boolean borrowed) {
        this.borrowed = borrowed;
    }
}
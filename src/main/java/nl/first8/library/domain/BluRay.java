package nl.first8.library.domain;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "bluray")
@SequenceGenerator(name="bluray_id_seq", initialValue=11, allocationSize=1000)
public class BluRay {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="bluray_id_seq")
    private Long id;

    @ManyToOne
    @JoinColumn(name="member_id_seq")
    private Members member;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "summary")
    private String summary;

    @Column(name = "actor", nullable = false)
    private String actor;

    @Column(name = "publish_date", nullable = false)
    private LocalDate publishDate;

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

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public LocalDate getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(LocalDate publishDate) {
        this.publishDate = publishDate;
    }

    public boolean isBorrowed() {
        return borrowed;
    }

    public void setBorrowed(boolean borrowed) {
        this.borrowed = borrowed;
    }
}

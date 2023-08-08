package nl.first8.library.domain;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "games")
@SequenceGenerator(name="games_id_seq", initialValue=11, allocationSize=1000)
public class Games {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="games_id_seq")
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "developer", nullable = false)
    private String developer;

    @Column(name = "publish_date", nullable = false)
    private LocalDate publishDate;

    @Column(name = "borrowed", columnDefinition = "boolean default false")
    private boolean borrowed;

    @Column(name = "summary")
    private String summary;

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    @Column(name = "platform")
    private String platform;

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

    public String getDeveloper() {
        return developer;
    }

    public void setDeveloper( String developer ) {
        this.developer = developer;
    }

    public LocalDate getPublishDate() {
        return publishDate;
    }

    public void setPublishDate( LocalDate publishDate ) {
        this.publishDate = publishDate;
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

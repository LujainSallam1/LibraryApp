package nl.first8.library.domain;

import javax.persistence.*;
import java.time.LocalDate;


@Entity
@Table(name = "bookInstance")
@SequenceGenerator(name="bookInstance_id_seq", initialValue=11, allocationSize=1000)
public class BookInstance {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="bookInstance_id_seq")
    private Long id;

    @Column(name = "isbn", nullable = false)
    private String isbn;

    @Column(name = "borrowed", columnDefinition = "boolean default false")
    private boolean borrowed;

    @Column(name = "incheckDate")
    private LocalDate incheckDate;

    @Column(name = "outcheckDate")
    private LocalDate outcheckDate;

    public LocalDate getIncheckDate() {
        return incheckDate;
    }

    public LocalDate getOutcheckDate() {
        return outcheckDate;
    }

    public void setOutcheckDate(LocalDate outcheckDate) {
        this.outcheckDate = outcheckDate;
    }

    public void setIncheckDate(LocalDate incheckDate) {
        this.incheckDate = incheckDate;
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

    public boolean isBorrowed() {
        return borrowed;
    }

    public void setBorrowed( boolean borrowed ) {
        this.borrowed = borrowed;
    }
}

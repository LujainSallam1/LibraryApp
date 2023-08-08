package nl.first8.library.domain;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "cook")
public class Cook extends Product{
    @Column(name = "isbn", nullable = false)
    private String isbn;

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
}

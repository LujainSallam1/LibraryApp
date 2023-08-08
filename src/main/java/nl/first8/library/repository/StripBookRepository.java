package nl.first8.library.repository;

import nl.first8.library.domain.Book;
import nl.first8.library.domain.StripBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface StripBookRepository extends JpaRepository<StripBook, Long> {

    @Transactional
    void deleteByIsbn(String isbn);

    List<StripBook> findAllByIsbn(String isbn);
    void deleteAllByIsbn(String isbn);
}

package nl.first8.library.repository;

import nl.first8.library.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Transactional
    public void deleteByIsbn(String isbn);


    List<Book> findByIsbn(String isbn);
}

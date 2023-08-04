package nl.first8.library.repository;

import nl.first8.library.domain.ComicBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComicBookRepository extends JpaRepository<ComicBook, Long> {

}

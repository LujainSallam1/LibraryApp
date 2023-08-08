package nl.first8.library.repository;

import nl.first8.library.domain.Cook;
import nl.first8.library.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CookRepository extends JpaRepository<Cook, Long> {

    @Transactional
    void deleteByIsbn(String isbn);

    List<Cook> findByIsbn(String number);
}

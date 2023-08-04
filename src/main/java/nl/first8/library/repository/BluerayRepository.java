package nl.first8.library.repository;

import nl.first8.library.domain.Blueray;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

public interface BluerayRepository extends JpaRepository<Blueray, Long> {
    @Transactional
    void deleteByIsbn(String isbn);
}

package nl.first8.library.repository;

import nl.first8.library.domain.Bluray;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface BlurayRepository extends JpaRepository<Bluray, Long> {
    List<Bluray> findByTitle(String title);
}

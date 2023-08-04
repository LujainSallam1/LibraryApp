package nl.first8.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import nl.first8.library.domain.Stripbook;
import org.springframework.stereotype.Repository;

@Repository
public interface StriobookRepository extends JpaRepository<Stripbook,Long> {

}

package nl.first8.library.repository;

import nl.first8.library.domain.BluRay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface BluRayRepository extends JpaRepository<BluRay, Long> {


}

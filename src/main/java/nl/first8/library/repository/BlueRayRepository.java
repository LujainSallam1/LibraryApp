package nl.first8.library.repository;

import nl.first8.library.domain.BlueRay;
import nl.first8.library.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface BlueRayRepository extends JpaRepository<BlueRay, Long> {


}

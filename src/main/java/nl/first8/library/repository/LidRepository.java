package nl.first8.library.repository;

import nl.first8.library.domain.Lid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LidRepository extends JpaRepository<Lid, Long> {


}
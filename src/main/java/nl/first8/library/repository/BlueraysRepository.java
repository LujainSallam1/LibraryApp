package nl.first8.library.repository;

import nl.first8.library.domain.Bluerays;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
    public interface BlueraysRepository extends JpaRepository<Bluerays, Long> {


    }



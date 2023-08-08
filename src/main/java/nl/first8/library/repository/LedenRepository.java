package nl.first8.library.repository;


import nl.first8.library.domain.Leden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LedenRepository extends JpaRepository<Leden, Long> {


    List<Leden> findAllByVoornaam(String voornaam);

    List<Leden> findAllByAgternaam(String agternaam);

    List<Leden> findAllByVoornaamAndAgternaam(String voornaam, String agternaam);
}

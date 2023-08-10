package nl.first8.library.repository;

import nl.first8.library.domain.Book;
import nl.first8.library.domain.Leden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface LedenRepository extends JpaRepository<Leden, Long> {


}
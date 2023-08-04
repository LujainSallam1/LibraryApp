package nl.first8.library.repository;

import nl.first8.library.domain.BluRay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface BluRayRepository extends JpaRepository<BluRay, Long> {

    @Transactional
    void deleteBluRayById(Long id);
}

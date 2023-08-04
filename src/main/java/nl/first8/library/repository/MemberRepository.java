package nl.first8.library.repository;

import nl.first8.library.domain.BluRay;
import nl.first8.library.domain.Members;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Members, Long> {
}

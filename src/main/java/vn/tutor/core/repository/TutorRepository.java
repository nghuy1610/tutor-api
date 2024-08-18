package vn.tutor.core.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.tutor.core.entity.Tutor;

public interface TutorRepository extends JpaRepository<Tutor, String>, TutorCustomRepository {
  Optional<Tutor> findByUserId(String userId);

  @Query(value = """
      select t from Tutor t left join fetch t.tutorSpecialties ts
      left join fetch ts.specialty
      where t.user.id = :userId
      """)
  Optional<Tutor> findFullByUserId(String userId);
}

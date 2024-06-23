package vn.tutor.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.tutor.core.entity.Tutor;

public interface TutorRepository extends JpaRepository<Tutor, String> {
}

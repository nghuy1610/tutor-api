package vn.tutor.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.tutor.core.entity.Specialty;

public interface SpecialtyRepository extends JpaRepository<Specialty, String> {
}

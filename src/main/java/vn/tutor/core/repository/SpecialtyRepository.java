package vn.tutor.core.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import vn.tutor.core.entity.Specialty;

public interface SpecialtyRepository extends JpaRepository<Specialty, String> {
  List<Specialty> findByNameIn(List<String> names);
}

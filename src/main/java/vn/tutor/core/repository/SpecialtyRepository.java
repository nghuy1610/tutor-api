package vn.tutor.core.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.tutor.core.entity.Specialty;

public interface SpecialtyRepository extends JpaRepository<Specialty, String> {
  @Query(value = """
  select * from specialty
  where lower(name) like concat('% ', lower(:name),'%') or lower(name) like concat(lower(:name),'%')
  order by updated_on desc
  limit 10""", nativeQuery = true)
  List<Specialty> findFirstTenByNameLikeOrderByUpdatedOnDesc(String name);
}

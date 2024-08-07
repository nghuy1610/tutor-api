package vn.tutor.core.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import vn.tutor.core.entity.SpecialtyDemand;
import vn.tutor.core.enums.SpecialtyDemandStatus;

public interface SpecialtyDemandRepository extends JpaRepository<SpecialtyDemand, String> {

  Page<SpecialtyDemand> findPagingByStatus(SpecialtyDemandStatus status, Pageable pageable);
}

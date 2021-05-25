package vn.tutor.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.tutor.core.entity.ClassOrder;

public interface ClassOrderRepository extends JpaRepository<ClassOrder, String> {
}

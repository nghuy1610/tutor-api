package vn.tutor.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.tutor.core.entity.UserPermission;

public interface UserPermissionRepository extends JpaRepository<UserPermission, String> {
}

package vn.tutor.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.tutor.core.entity.Permission;
import vn.tutor.core.enums.PermissionType;

public interface PermissionRepository extends JpaRepository<Permission, String> {

  Permission findByPermissionType(PermissionType permissionType);
}

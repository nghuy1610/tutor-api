package vn.tutor.core.repository;

import java.util.Optional;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import vn.tutor.core.entity.UserProfile;
import vn.tutor.core.enums.PermissionType;

public interface UserProfileRepository extends JpaRepository<UserProfile, String>,
    JpaSpecificationExecutor<UserProfile> {

  @Query("select up from UserProfile up where up.user.id = :userId")
  Optional<UserProfile> findByUserId(String userId);

  @Query("""
      select up from UserProfile up left join up.user.userPermissions userPerms left join userPerms.permission p
      where up.id = :profileId and p.permissionType in :permissionTypes
      """)
  Optional<UserProfile> findByIdAndPermissionType(String profileId, Set<PermissionType> permissionTypes);
}

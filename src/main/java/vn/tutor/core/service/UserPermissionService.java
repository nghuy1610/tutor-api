package vn.tutor.core.service;

import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.tutor.core.entity.User;
import vn.tutor.core.entity.UserPermission;
import vn.tutor.core.enums.PermissionType;
import vn.tutor.core.repository.UserPermissionRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class UserPermissionService {

  private final PermissionService permissionService;
  private final UserPermissionRepository userPermissionRepository;

  public List<UserPermission> createUserPermissionWithAuthoritiesForUser(List<PermissionType> permissionTypes,
      User user) {
    List<UserPermission> userPermissions = permissionTypes.stream()
        .map(permissionType -> UserPermission.builder().user(user)
            .permission(permissionService.findPermissionByType(permissionType)).build())
        .toList();
    user.setUserPermissions(userPermissions);
    return userPermissionRepository.saveAll(userPermissions);
  }
}

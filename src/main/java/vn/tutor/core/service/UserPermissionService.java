package vn.tutor.core.service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.tutor.core.entity.User;
import vn.tutor.core.entity.UserPermission;
import vn.tutor.core.enums.PermissionType;
import vn.tutor.core.enums.UserType;
import vn.tutor.core.repository.UserPermissionRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class UserPermissionService {

  private final PermissionService permissionService;
  private final UserPermissionRepository userPermissionRepository;

  public List<UserPermission> createUserPermissionForUser(User user, UserType userType) {
    List<UserPermission> userPermissions = Stream.of(userType)
        .map(PermissionType::fromUserType)
        .map(permissionType -> UserPermission.builder().user(user)
            .permission(permissionService.findPermissionByType(permissionType)).build())
        .toList();
    user.setUserPermissions(userPermissions);
    return userPermissionRepository.saveAll(userPermissions);
  }
}

package vn.tutor.core.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.tutor.core.entity.User;
import vn.tutor.core.entity.UserPermission;
import vn.tutor.core.enums.PermissionType;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UserPermissionService {
    private final PermissionService permissionService;

    List<UserPermission> createUserPermissionWithAuthoritiesForUser(List<PermissionType> permissionTypes, User user) {
        return permissionTypes.stream()
                .map(permissionType -> UserPermission.builder().user(user)
                        .permission(permissionService.findPermissionByType(permissionType)).build())
                .collect(Collectors.toList());
    }

}

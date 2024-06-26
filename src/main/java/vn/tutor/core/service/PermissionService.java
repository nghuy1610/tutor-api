package vn.tutor.core.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.tutor.core.dto.request.PermissionReq;
import vn.tutor.core.dto.response.PermissionResp;
import vn.tutor.core.entity.Permission;
import vn.tutor.core.enums.PermissionType;
import vn.tutor.core.repository.PermissionRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class PermissionService {

  private final PermissionRepository permissionRepository;

  public Permission findPermissionByType(PermissionType permissionType) {
    return permissionRepository.findByPermissionType(permissionType);
  }

  public PermissionResp createAndRetrievePermission(PermissionReq requestDto) {
    Permission permission = Permission.from(requestDto);
    permissionRepository.save(permission);
    return PermissionResp.from(permission);
  }
}

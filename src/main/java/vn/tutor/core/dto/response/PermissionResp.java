package vn.tutor.core.dto.response;

import vn.tutor.core.entity.Permission;
import vn.tutor.core.enums.PermissionType;

public record PermissionResp(
    String id,
    PermissionType permissionType
) {

  public static PermissionResp from(Permission permission) {
    return new PermissionResp(permission.getId(), permission.getPermissionType());
  }
}

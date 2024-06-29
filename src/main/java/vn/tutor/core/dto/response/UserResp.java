package vn.tutor.core.dto.response;

import java.util.List;
import vn.tutor.core.entity.User;

public record UserResp(
    String id,
    String email,
    List<String> permissionType
) {

  public static UserResp from(User user) {
    return new UserResp(user.getId(), user.getEmail(),
        user.getUserPermissions().stream().map(up -> up.getPermission().getPermissionType().name()).toList());
  }
}

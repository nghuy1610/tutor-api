package vn.tutor.core.dto.request;

import lombok.Getter;
import lombok.Setter;
import vn.tutor.core.enums.PermissionType;

@Getter
@Setter
public class PermissionRequestDto {
    private PermissionType permissionType;
}

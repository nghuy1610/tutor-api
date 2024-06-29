package vn.tutor.core.dto.response;

import lombok.Getter;
import lombok.Setter;
import vn.tutor.core.enums.PermissionType;

@Getter
@Setter
public class PermissionResp {
    private String id;
    private PermissionType permissionType;
}

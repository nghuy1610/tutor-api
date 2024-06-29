package vn.tutor.core.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class UserResp {
    private String id;
    private String email;
    private List<String> permissionType;
}

package vn.tutor.core.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class UserResponseDto {
    private String id;
    private String email;
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private List<String> permissionType;
}

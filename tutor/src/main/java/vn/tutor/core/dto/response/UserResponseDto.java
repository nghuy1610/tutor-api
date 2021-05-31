package vn.tutor.core.dto.response;

import lombok.Data;

@Data
public class UserResponseDto {
    private String id;
    private String email;
    private String password;
    private String phoneNumber;
    private String firstName;
    private String lastName;
}

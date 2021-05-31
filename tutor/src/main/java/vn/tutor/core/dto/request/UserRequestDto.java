package vn.tutor.core.dto.request;

import lombok.Data;

@Data
public class UserRequestDto {
    private String email;
    private String password;
    private String phoneNumber;
    private String firstName;
    private String lastName;
}

package vn.tutor.core.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserRequestDto {
    @NotBlank
    private String email;
    @NotBlank
    private String password;
    private String phoneNumber;
    private String firstName;
    private String lastName;
    @NotBlank
    private String role;
}

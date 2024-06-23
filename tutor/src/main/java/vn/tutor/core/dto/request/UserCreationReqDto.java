package vn.tutor.core.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserCreationReqDto {
    @NotBlank(message = "Email is required")
    private String email;
    @NotBlank (message = "Password is required")
    private String password;
    private String phoneNumber;
    private String firstName;
    private String lastName;
    @NotBlank(message = "Role is required")
    private String role;
}

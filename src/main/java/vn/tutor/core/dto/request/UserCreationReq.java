package vn.tutor.core.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserCreationReq(
    @NotBlank(message = "Email is required")
    @Email
    String email,
    @NotBlank (message = "Password is required")
    String password,
    @NotBlank(message = "Type is required")
    String type
){}

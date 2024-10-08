package vn.tutor.core.dto.request;

import jakarta.validation.constraints.NotBlank;

public record LoginReq(
    @NotBlank(message = "Email is required")
    String email,
    @NotBlank(message = "Password is required")
    String password) {

}


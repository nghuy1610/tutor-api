package vn.tutor.core.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UserProfileReq(
    @Pattern(regexp = "^(\\+84|0)\\d{9,10}$", message = "Invalid format")
    String phoneNumber,
    @NotBlank @Max(value = 20)
    String firstName,
    @NotBlank @Max(value = 20)
    String lastName
) {

}

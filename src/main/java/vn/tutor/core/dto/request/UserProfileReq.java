package vn.tutor.core.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserProfileReq(
    @Pattern(regexp = "^(\\+84|0)\\d{9,10}$", message = "Invalid format")
    String phoneNumber,
    @NotBlank @Size(max = 20)
    String firstName,
    @NotBlank @Size(max = 20)
    String lastName
) {

}

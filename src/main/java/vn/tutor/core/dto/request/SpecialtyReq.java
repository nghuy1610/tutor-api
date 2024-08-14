package vn.tutor.core.dto.request;

import jakarta.validation.constraints.NotBlank;

public record SpecialtyReq(
    @NotBlank(message = "Specialty name is required")
    String name
) {

}

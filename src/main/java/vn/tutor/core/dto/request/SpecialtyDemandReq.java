package vn.tutor.core.dto.request;

import jakarta.validation.constraints.NotBlank;

public record SpecialtyDemandReq(
    @NotBlank(message = "Specialty name is required")
    String specialtyName
) {

}

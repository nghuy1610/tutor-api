package vn.tutor.core.dto.request;

import jakarta.validation.constraints.NotBlank;

public record SpecialtyDemandAssessmentReq(
    @NotBlank(message = "Specialty name is required")
    String specialtyName,
    @NotBlank(message = "Status is required")
    String status
) {

}

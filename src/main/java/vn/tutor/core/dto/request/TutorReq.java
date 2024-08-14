package vn.tutor.core.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public record TutorReq(
    String introduction,
    String address,
    String gender,
    @NotEmpty(message = "Specialties must not be empty")
    List<@NotBlank String> specialties
) {

}

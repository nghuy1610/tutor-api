package vn.tutor.core.dto.response;

import java.util.List;
import java.util.Optional;
import vn.tutor.core.entity.Tutor;

public record TutorResp(
    String id,
    String introduction,
    String address,
    String gender,
    String verificationStatus,
    List<String> specialties
) {
  public static TutorResp from(Tutor tutor) {
    List<String> specialties = tutor.getTutorSpecialties().stream().map(ts -> ts.getSpecialty().getName()).toList();
    String gender = Optional.ofNullable(tutor.getGender()).map(Enum::name).orElse("");
    String verificationStatus = Optional.ofNullable(tutor.getVerificationStatus()).map(Enum::name).orElse("");
    return new TutorResp(tutor.getId(), tutor.getIntroduction(), tutor.getAddress(), gender,
                         verificationStatus, specialties);
  }
}

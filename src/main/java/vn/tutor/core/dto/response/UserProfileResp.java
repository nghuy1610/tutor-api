package vn.tutor.core.dto.response;

import vn.tutor.core.entity.UserProfile;

public record UserProfileResp(
    String id,
    String firstName,
    String lastName,
    String email,
    String phoneNumber,
    String userType
) {

  public static UserProfileResp from(UserProfile userProfile) {
    return new UserProfileResp(userProfile.getId(), userProfile.getFirstName(), userProfile.getLastName(),
        userProfile.getEmail(), userProfile.getPhoneNumber(), userProfile.getUserType().name());
  }
}

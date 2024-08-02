package unit.vn.tutor.core.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import vn.tutor.core.dto.request.UserProfileReq;
import vn.tutor.core.dto.response.UserProfileResp;
import vn.tutor.core.entity.User;
import vn.tutor.core.entity.UserProfile;
import vn.tutor.core.enums.UserType;
import vn.tutor.core.exception.ResourceNotFoundException;
import vn.tutor.core.repository.UserProfileRepository;
import vn.tutor.core.service.UserProfileService;

@ExtendWith(MockitoExtension.class)
public class UserProfileServiceTest {

  @InjectMocks
  UserProfileService userProfileService;

  @Mock
  UserProfileRepository userProfileRepository;

  @Test
  void givenUser_whenCreateUserProfile_thenReturnUserProfile() {
    User user = User.builder().email("email").build();

    when(userProfileRepository.save(any())).then(AdditionalAnswers.returnsFirstArg());

    UserProfile userProfile = userProfileService.createUserProfile(user, UserType.STUDENT);

    assertThat(userProfile.getEmail()).isEqualTo(user.getEmail());
    assertThat(userProfile.getUserType()).isEqualTo(UserType.STUDENT);
  }

  @Test
  void givenUserId_whenGetUserProfile_thenReturnUserProfile() {
    String userId = "userId";
    UserProfile storedUserProfile = UserProfile.builder()
        .email("email")
        .phoneNumber("phoneNumber")
        .firstName("firstName")
        .lastName("lastName")
        .userType(UserType.TUTOR)
        .build();
    storedUserProfile.setId("id");
    UserProfileResp expectedResp = new UserProfileResp("id", "firstName", "lastName",
                                                       "email", "phoneNumber", UserType.TUTOR.name());

    when(userProfileRepository.findByUserId(eq(userId)))
        .thenReturn(Optional.of(storedUserProfile));

    UserProfileResp userProfileResp = userProfileService.retrieveUserProfile(userId);

    assertThat(userProfileResp).isEqualTo(expectedResp);
  }

  @Test
  void givenUserIdWithoutLinkedUserProfile_whenGetUserProfile_thenThrowResourceNotFoundException() {
    String userId = "userId";

    when(userProfileRepository.findByUserId(eq(userId)))
        .thenReturn(Optional.empty());

    assertThatThrownBy(() -> userProfileService.retrieveUserProfile(userId))
        .isInstanceOf(ResourceNotFoundException.class)
        .hasMessage("User profile not found");
  }

  @Test
  void givenUserIdAndUserProfileRequest_whenUpdateUserProfile_thenReturnUserProfile() {
    String userId = "userId";
    UserProfile userProfile = UserProfile.builder()
        .email("email")
        .phoneNumber("phoneNumber")
        .firstName("firstName")
        .lastName("lastName")
        .userType(UserType.OPERATOR)
        .build();
    userProfile.setId("id");
    UserProfileReq userProfileReq = new UserProfileReq("newPhoneNumber", "newFirstName", "newLastName");
    UserProfileResp expectedResp = new UserProfileResp("id", "newFirstName", "newLastName",
        "email", "newPhoneNumber", "OPERATOR");

    when(userProfileRepository.findByUserId(eq(userId)))
        .thenReturn(Optional.of(userProfile));

    UserProfileResp userProfileResp = userProfileService.updateUserProfile(userId, userProfileReq);

    assertThat(userProfileResp).isEqualTo(expectedResp);
  }

  @Test
  void givenUserIdWithoutLinkedUserProfile_whenUpdateUserProfile_thenThrowResourceNotFoundException() {
    String userId = "userId";
    UserProfileReq userProfileReq = new UserProfileReq("newPhoneNumber", "newFirstName", "newLastName");

    when(userProfileRepository.findByUserId(eq(userId)))
        .thenReturn(Optional.empty());

    assertThatThrownBy(() -> userProfileService.updateUserProfile(userId, userProfileReq))
        .isInstanceOf(ResourceNotFoundException.class)
        .hasMessage("User profile not found");
  }
}

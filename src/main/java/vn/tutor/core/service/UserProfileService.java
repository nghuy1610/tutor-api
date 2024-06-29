package vn.tutor.core.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.tutor.core.dto.request.UserProfileReq;
import vn.tutor.core.dto.response.UserProfileResp;
import vn.tutor.core.entity.User;
import vn.tutor.core.entity.UserProfile;
import vn.tutor.core.exception.ResourceNotFoundException;
import vn.tutor.core.repository.UserProfileRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class UserProfileService {

  private final UserProfileRepository userProfileRepository;

  public UserProfile createUserProfile(User user) {
    UserProfile userProfile = new UserProfile();
    userProfile.setUser(user);
    userProfile.setEmail(user.getEmail());
    return userProfileRepository.save(userProfile);
  }

  public UserProfileResp retrieveUserProfile(String userId) {
    return userProfileRepository.findByUserId(userId)
        .map(UserProfileResp::from)
        .orElseThrow(() -> new ResourceNotFoundException("User profile not found"));
  }

  public UserProfileResp updateUserProfile(String userId, UserProfileReq userProfileReq) {
    Optional<UserProfile> optUserProfile = userProfileRepository.findByUserId(userId);
    if (optUserProfile.isPresent()) {
      optUserProfile.get().apply(userProfileReq);
      return UserProfileResp.from(optUserProfile.get());
    } else {
      throw new ResourceNotFoundException("User profile not found");
    }
  }
}

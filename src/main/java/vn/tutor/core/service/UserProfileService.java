package vn.tutor.core.service;

import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.tutor.core.dto.request.UserProfileReq;
import vn.tutor.core.dto.response.PagingResponse;
import vn.tutor.core.dto.response.UserProfileResp;
import vn.tutor.core.entity.User;
import vn.tutor.core.entity.UserProfile;
import vn.tutor.core.enums.PermissionType;
import vn.tutor.core.enums.UserType;
import vn.tutor.core.exception.ResourceNotFoundException;
import vn.tutor.core.repository.UserProfileRepository;
import vn.tutor.core.repository.specification.UserProfileSpecification;

@Service
@RequiredArgsConstructor
@Transactional
public class UserProfileService {

  private final UserProfileRepository userProfileRepository;
  private final UserProfileSpecification userProfileSpecification;

  public UserProfile createUserProfile(User user, UserType userType) {
    UserProfile userProfile = new UserProfile();
    userProfile.setUser(user);
    userProfile.setUserType(userType);
    userProfile.setEmail(user.getEmail());
    return userProfileRepository.save(userProfile);
  }

  public UserProfile retrieveUserProfile(String userId) {
    return userProfileRepository.findByUserId(userId)
        .orElseThrow(() -> new ResourceNotFoundException("User profile not found"));
  }

  public PagingResponse<UserProfileResp> retrieveNormalUserProfiles(String email, String name,
                                                                    int pageNum, int pageSize) {
    Specification<UserProfile> spec = userProfileSpecification.isNormalUser();
    if (StringUtils.isNotBlank(email)) {
      spec = spec.and(userProfileSpecification.hasEmail(email));
    }
    if (StringUtils.isNotBlank(name)) {
      spec = spec.and(userProfileSpecification.hasName(name));
    }
    Page<UserProfile> userProfilePage = userProfileRepository.findAll(spec, PageRequest.of(pageNum, pageSize));
    List<UserProfileResp> userProfileRespList = userProfilePage.stream().map(UserProfileResp::from).toList();
    return new PagingResponse<>(userProfileRespList, userProfilePage.getNumber(),
        userProfilePage.getTotalPages(), userProfilePage.getTotalElements());
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

  public UserProfileResp updateNormalUserProfile(String profileId, UserProfileReq profileReq) {
    Optional<UserProfile> optUserProfile = userProfileRepository.findByIdAndPermissionType(profileId,
        EnumSet.of(PermissionType.TUTOR, PermissionType.STUDENT));
    if (optUserProfile.isPresent()) {
      optUserProfile.get().apply(profileReq);
      return UserProfileResp.from(optUserProfile.get());
    } else {
      throw new ResourceNotFoundException("User profile not found");
    }
  }
}

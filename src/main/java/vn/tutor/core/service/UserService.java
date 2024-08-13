package vn.tutor.core.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.tutor.core.dto.request.LoginReq;
import vn.tutor.core.dto.request.UserCreationReq;
import vn.tutor.core.dto.response.LoginResp;
import vn.tutor.core.dto.response.UserResp;
import vn.tutor.core.entity.User;
import vn.tutor.core.enums.UserType;
import vn.tutor.core.repository.UserRepository;
import vn.tutor.core.security.CustomUserDetails;
import vn.tutor.core.security.JwtTokenInfo;
import vn.tutor.core.security.JwtUtils;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final JwtUtils jwtUtils;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;
  private final UserPermissionService userPermissionService;
  private final TutorService tutorService;
  private final StudentService studentService;
  private final UserProfileService userProfileService;

  public UserResp createAndRetrieveNormalUser(UserCreationReq requestDto) {
    if (UserType.isNormalUserType(requestDto.type())) {
      return UserResp.from(createAndRetrieveUser(requestDto, UserType.valueOf(requestDto.type())));
    } else {
      throw new IllegalArgumentException("Invalid user type: " + requestDto.type());
    }
  }

  public UserResp createAndRetrieveOperator(UserCreationReq requestDto) {
    if (UserType.isOperatorUserType(requestDto.type())) {
      return UserResp.from(createAndRetrieveUser(requestDto, UserType.valueOf(requestDto.type())));
    } else {
      throw new IllegalArgumentException("Invalid user type: " + requestDto.type());
    }
  }

  private User createAndRetrieveUser(UserCreationReq requestDto, UserType userType) {
    User user = User.from(requestDto);
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    user.setUserPermissions(
        userPermissionService.createUserPermissionForUser(user, userType));
    userRepository.save(user);
    user.setUserProfile(userProfileService.createUserProfile(user, userType));

    switch (userType) {
      case TUTOR -> tutorService.createTutor(user);
      case STUDENT -> studentService.createStudent(user);
    }
    return user;
  }

  public LoginResp login(LoginReq loginReq) {
    Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginReq.email(), loginReq.password()));
    CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
    return new LoginResp(jwtUtils.generateToken(new JwtTokenInfo(userDetails.userId(), userDetails.email(), userDetails.authorities())),
                         userDetails.authorities());
  }
}

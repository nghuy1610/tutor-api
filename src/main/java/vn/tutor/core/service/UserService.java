package vn.tutor.core.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.tutor.core.dto.request.LoginReq;
import vn.tutor.core.dto.request.UserCreationReq;
import vn.tutor.core.dto.response.LoginResp;
import vn.tutor.core.dto.response.UserResp;
import vn.tutor.core.entity.User;
import vn.tutor.core.enums.PermissionType;
import vn.tutor.core.repository.UserRepository;

import java.util.List;
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

    public UserResp createAndRetrieveUser(UserCreationReq requestDto) {
        if (PermissionType.isNormalUserPermission(requestDto.role())) {
            return createAndRetrieveUser(requestDto, PermissionType.valueOf(requestDto.role()));
        } else {
            throw new IllegalArgumentException("Invalid role: " + requestDto.role());
        }
    }

    public UserResp createAndRetrieveOperator(UserCreationReq requestDto) {
        if (PermissionType.isOperatorPermission(requestDto.role())) {
            return createAndRetrieveUser(requestDto, PermissionType.valueOf(requestDto.role()));
        } else {
            throw new IllegalArgumentException("Invalid role: " + requestDto.role());
        }
    }

    private UserResp createAndRetrieveUser(UserCreationReq requestDto, PermissionType permissionType) {
        User user = User.from(requestDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setUserPermissions(userPermissionService.createUserPermissionWithAuthoritiesForUser(List.of(permissionType), user));
        userRepository.save(user);
        user.setUserProfile(userProfileService.createUserProfile(user));

        switch (permissionType) {
            case TUTOR -> tutorService.createTutor(user);
            case STUDENT -> studentService.createStudent(user);
        }
        return UserResp.from(user);
    }

    public LoginResp login(LoginReq loginReq) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginReq.email(), loginReq.password()));
        User user = userRepository.findFullUserByEmail(loginReq.email());
        List<String> authorities = user.getUserPermissions().stream().map(up -> up.getPermission().getPermissionType().name()).toList();
        return new LoginResp(jwtUtils.generateToken(new JwtTokenInfo(user.getId(), user.getEmail(), authorities)), authorities);
    }
}

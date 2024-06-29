package vn.tutor.core.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.tutor.core.dto.request.LoginReq;
import vn.tutor.core.dto.request.UserCreationReqDto;
import vn.tutor.core.dto.response.LoginResp;
import vn.tutor.core.dto.response.UserResp;
import vn.tutor.core.entity.User;
import vn.tutor.core.enums.PermissionType;
import vn.tutor.core.mapper.Mapper;
import vn.tutor.core.repository.UserRepository;

import java.util.List;
import vn.tutor.core.security.JwtTokenInfo;
import vn.tutor.core.security.JwtUtils;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final Mapper mapper;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserPermissionService userPermissionService;
    private final TutorService tutorService;
    private final StudentService studentService;
    private final UserProfileService userProfileService;

    public UserResp createAndRetrieveUser(UserCreationReqDto requestDto) {
        if (PermissionType.isNormalUserPermission(requestDto.getRole())) {
            return createAndRetrieveUser(requestDto, PermissionType.valueOf(requestDto.getRole()));
        } else {
            throw new IllegalArgumentException("Invalid role: " + requestDto.getRole());
        }
    }

    public UserResp createAndRetrieveOperator(UserCreationReqDto requestDto) {
        if (PermissionType.isOperatorPermission(requestDto.getRole())) {
            return createAndRetrieveUser(requestDto, PermissionType.valueOf(requestDto.getRole()));
        } else {
            throw new IllegalArgumentException("Invalid role: " + requestDto.getRole());
        }
    }

    private UserResp createAndRetrieveUser(UserCreationReqDto requestDto, PermissionType permissionType) {
        User user = mapper.map(requestDto, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setUserPermissions(userPermissionService.createUserPermissionWithAuthoritiesForUser(List.of(permissionType), user));
        userRepository.save(user);
        user.setUserProfile(userProfileService.createUserProfile(user));

        switch (permissionType) {
            case TUTOR -> tutorService.createTutor(user);
            case STUDENT -> studentService.createStudent(user);
        }
        return mapper.map(user, UserResp.class);
    }

    public LoginResp login(LoginReq loginReq) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginReq.getEmail(), loginReq.getPassword()));
        User user = userRepository.findFullUserByEmail(loginReq.getEmail());
        List<String> authorities = user.getUserPermissions().stream().map(up -> up.getPermission().getPermissionType().name()).toList();
        return new LoginResp(jwtUtils.generateToken(new JwtTokenInfo(user.getId(), user.getEmail(), authorities)), authorities);
    }
}

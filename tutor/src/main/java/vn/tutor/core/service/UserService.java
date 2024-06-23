package vn.tutor.core.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.tutor.core.dto.request.LoginRequestDto;
import vn.tutor.core.dto.request.UserRequestDto;
import vn.tutor.core.dto.response.LoginResponseDto;
import vn.tutor.core.dto.response.UserResponseDto;
import vn.tutor.core.entity.User;
import vn.tutor.core.enums.PermissionType;
import vn.tutor.core.mapper.Mapper;
import vn.tutor.core.repository.UserRepository;

import java.util.List;
import vn.tutor.core.security.AuthToken;
import vn.tutor.core.security.JwtUtils;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserPermissionService userPermissionService;
    private final Mapper mapper;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final TutorService tutorService;
    private final StudentService studentService;
    private final PasswordEncoder passwordEncoder;

    public UserResponseDto createAndRetrieveUser(UserRequestDto requestDto) {
        if (PermissionType.isNormalUserPermission(requestDto.getRole())) {
            return createAndRetrieveUser(requestDto, PermissionType.valueOf(requestDto.getRole()));
        } else {
            throw new IllegalArgumentException("Invalid role: " + requestDto.getRole());
        }
    }

    public UserResponseDto createAndRetrieveOperator(UserRequestDto requestDto) {
        if (PermissionType.isOperatorPermission(requestDto.getRole())) {
            return createAndRetrieveUser(requestDto, PermissionType.valueOf(requestDto.getRole()));
        } else {
            throw new IllegalArgumentException("Invalid role: " + requestDto.getRole());
        }
    }

    private UserResponseDto createAndRetrieveUser(UserRequestDto requestDto, PermissionType permissionType) {
        User user = mapper.map(requestDto, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setUserPermissions(userPermissionService.createUserPermissionWithAuthoritiesForUser(List.of(permissionType), user));
        userRepository.save(user);
        switch (permissionType) {
            case TUTOR -> tutorService.createTutor(user);
            case STUDENT -> studentService.createStudent(user);
        }
        return mapper.map(user, UserResponseDto.class);
    }

    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword()));
        User user = userRepository.findFullUserByEmail(loginRequestDto.getEmail());
        List<String> authorities = user.getUserPermissions().stream().map(up -> up.getPermission().getPermissionType().name()).toList();
        return new LoginResponseDto(jwtUtils.generateToken(new AuthToken(user.getId(), user.getEmail(), authorities)), authorities);
    }
}

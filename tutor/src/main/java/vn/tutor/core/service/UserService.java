package vn.tutor.core.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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

    public UserResponseDto createAndRetrieveTutorUser(UserRequestDto requestDto) {
        return createAndRetrieveUser(requestDto, PermissionType.TUTOR);
    }

    public UserResponseDto createAndRetrieveOperatorUser(UserRequestDto requestDto) {
        return createAndRetrieveUser(requestDto, PermissionType.OPERATOR);
    }

    private UserResponseDto createAndRetrieveUser(UserRequestDto requestDto, PermissionType permissionType) {
        User user = mapper.map(requestDto, User.class);
        user.setUserPermissions(userPermissionService.createUserPermissionWithAuthoritiesForUser(List.of(permissionType), user));
        userRepository.save(user);
        return mapper.map(user, UserResponseDto.class);
    }

    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword()));
        User user = userRepository.findByEmail(loginRequestDto.getEmail());
        List<String> authorities = user.getUserPermissions().stream().map(up -> up.getPermission().getPermissionType().name()).toList();
        return new LoginResponseDto(jwtUtils.generateToken(new AuthToken(user.getId(), user.getEmail(), authorities)), authorities);
    }
}

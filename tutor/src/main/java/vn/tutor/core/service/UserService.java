package vn.tutor.core.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.tutor.core.dto.request.UserRequestDto;
import vn.tutor.core.dto.response.UserResponseDto;
import vn.tutor.core.entity.User;
import vn.tutor.core.enums.PermissionType;
import vn.tutor.core.mapper.Mapper;
import vn.tutor.core.repository.UserRepository;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PermissionService permissionService;
    private final Mapper mapper;

    public UserResponseDto createAndRetrieveTutorUser(UserRequestDto requestDto) {
        return createAndRetrieveUser(requestDto, PermissionType.TUTOR);
    }

    public UserResponseDto createAndRetrieveOperatorUser(UserRequestDto requestDto) {
        return createAndRetrieveUser(requestDto, PermissionType.OPERATOR);
    }

    private UserResponseDto createAndRetrieveUser(UserRequestDto requestDto, PermissionType permissionType) {
        User user = mapper.map(requestDto, User.class);
        user.setPermission(List.of(permissionService.findPermissionByType(permissionType)));
        userRepository.save(user);
        return mapper.map(user, UserResponseDto.class);
    }
}

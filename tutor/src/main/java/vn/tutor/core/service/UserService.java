package vn.tutor.core.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.tutor.core.dto.request.UserRequestDto;
import vn.tutor.core.dto.response.UserResponseDto;
import vn.tutor.core.entity.User;
import vn.tutor.core.mapper.Mapper;
import vn.tutor.core.repository.UserRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final Mapper mapper;

    public UserResponseDto createAndRetrieveUser(UserRequestDto requestDto) {
        User user = mapper.map(requestDto, User.class);
        userRepository.save(user);
        return mapper.map(user, UserResponseDto.class);
    }
}

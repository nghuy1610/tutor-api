package vn.tutor.core.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.tutor.core.dto.request.LoginRequestDto;
import vn.tutor.core.dto.request.UserRequestDto;
import vn.tutor.core.dto.response.LoginResponseDto;
import vn.tutor.core.dto.response.UserResponseDto;
import vn.tutor.core.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @PostMapping(path="", produces = "application/json", consumes = "application/json")
    public ResponseEntity<UserResponseDto> registerUser(@RequestBody UserRequestDto requestDto) {
        UserResponseDto user = userService.createAndRetrieveTutorUser(requestDto);
        return ResponseEntity.ok(user);
    }

    @PostMapping(path = "login", produces = "application/json", consumes = "application/json")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        LoginResponseDto responseDto = userService.login(loginRequestDto);
        return ResponseEntity.ok(responseDto);
    }
}

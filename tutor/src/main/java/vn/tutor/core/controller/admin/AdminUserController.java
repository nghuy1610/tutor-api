package vn.tutor.core.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.tutor.core.dto.request.LoginRequestDto;
import vn.tutor.core.dto.request.UserRequestDto;
import vn.tutor.core.dto.response.UserResponseDto;
import vn.tutor.core.service.UserService;

@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
public class AdminUserController {
    private final UserService userService;

    @PostMapping(path = "", produces = "application/json", consumes = "application/json")
    public ResponseEntity<UserResponseDto> createAndRetrieveUser(@RequestBody UserRequestDto requestDto) {
         UserResponseDto responseDto = userService.createAndRetrieveOperatorUser(requestDto);
         return ResponseEntity.ok(responseDto);
    }

    @PostMapping(path = "login", produces = "application/json", consumes = "application/json")
    public ResponseEntity<UserResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        UserResponseDto responseDto = userService.login(loginRequestDto);
        return ResponseEntity.ok(responseDto);
    }
}

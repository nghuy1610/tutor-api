package vn.tutor.core.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.tutor.core.dto.request.LoginReq;
import vn.tutor.core.dto.request.UserCreationReqDto;
import vn.tutor.core.dto.response.LoginResp;
import vn.tutor.core.dto.response.UserResp;
import vn.tutor.core.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @PostMapping(path="", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResp> registerUser(@RequestBody UserCreationReqDto requestDto) {
        UserResp user = userService.createAndRetrieveUser(requestDto);
        return ResponseEntity.ok(user);
    }

    @PostMapping(path = "login", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoginResp> login(@RequestBody LoginReq loginReq) {
        LoginResp responseDto = userService.login(loginReq);
        return ResponseEntity.ok(responseDto);
    }
}

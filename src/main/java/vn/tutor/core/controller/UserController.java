package vn.tutor.core.controller;

import java.util.concurrent.Callable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.tutor.core.common.Constant;
import vn.tutor.core.dto.request.LoginReq;
import vn.tutor.core.dto.request.UserCreationReq;
import vn.tutor.core.dto.response.LoginResp;
import vn.tutor.core.dto.response.UserResp;
import vn.tutor.core.service.UserService;
import vn.tutor.core.validation.RequiredHeaders;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

  private final UserService userService;

  @PostMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  public Callable<ResponseEntity<UserResp>> registerUser(
      @RequestHeader @RequiredHeaders(headers = {Constant.X_CORRELATION_ID}) HttpHeaders headers,
      @RequestBody UserCreationReq requestDto) {
    return () -> ResponseEntity.ok(userService.createAndRetrieveNormalUser(requestDto));
  }

  @PostMapping(path = "login", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  public Callable<ResponseEntity<LoginResp>> login(
      @RequestHeader @RequiredHeaders(headers = {Constant.X_CORRELATION_ID}) HttpHeaders headers,
      @RequestBody LoginReq loginReq) {
    return () -> {
      LoginResp responseDto = userService.login(loginReq);
      return ResponseEntity.ok(responseDto);
    };
  }
}

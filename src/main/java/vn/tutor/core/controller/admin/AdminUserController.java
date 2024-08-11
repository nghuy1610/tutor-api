package vn.tutor.core.controller.admin;

import jakarta.validation.Valid;
import java.util.concurrent.Callable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.tutor.core.common.Constant;
import vn.tutor.core.dto.request.UserCreationReq;
import vn.tutor.core.dto.response.UserResp;
import vn.tutor.core.service.UserService;
import vn.tutor.core.validation.RequiredHeaders;

@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
public class AdminUserController {

  private final UserService userService;

  @PreAuthorize("hasAuthority('SUPER_ADMIN')")
  @PostMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  public Callable<ResponseEntity<UserResp>> createAndRetrieveUser(
      @RequestHeader @RequiredHeaders(headers = {Constant.X_CORRELATION_ID}) HttpHeaders headers,
      @RequestBody @Valid UserCreationReq requestDto) {
    return () -> ResponseEntity.ok(userService.createAndRetrieveOperator(requestDto));
  }
}

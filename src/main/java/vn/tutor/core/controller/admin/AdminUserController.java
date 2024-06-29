package vn.tutor.core.controller.admin;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.tutor.core.dto.request.UserCreationReq;
import vn.tutor.core.dto.response.UserResp;
import vn.tutor.core.service.UserService;

@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
public class AdminUserController {

  private final UserService userService;

  @PreAuthorize("hasAuthority('SUPER_ADMIN')")
  @PostMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<UserResp> createAndRetrieveUser(@RequestBody @Valid UserCreationReq requestDto) {
    UserResp responseDto = userService.createAndRetrieveOperator(requestDto);
    return ResponseEntity.ok(responseDto);
  }
}

package vn.tutor.core.controller;

import java.util.concurrent.Callable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.tutor.core.common.Constant;
import vn.tutor.core.dto.request.UserProfileReq;
import vn.tutor.core.dto.response.UserProfileResp;
import vn.tutor.core.security.CustomUserDetails;
import vn.tutor.core.service.UserProfileService;
import vn.tutor.core.validation.RequiredHeaders;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class UserProfileController {
  private final UserProfileService userProfileService;

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public Callable<ResponseEntity<UserProfileResp>> retrieveCurrentUserProfile(
      @RequestHeader @RequiredHeaders(headers = {Constant.X_CORRELATION_ID}) HttpHeaders headers,
      @AuthenticationPrincipal CustomUserDetails userDetails) {
    return () -> ResponseEntity.ok(userProfileService.retrieveUserProfile(userDetails.userId()));
  }

  @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public Callable<ResponseEntity<UserProfileResp>> updateCurrentUserProfile(
      @RequestHeader @RequiredHeaders(headers = {Constant.X_CORRELATION_ID}) HttpHeaders headers,
      @RequestBody UserProfileReq userProfileReq,
      @AuthenticationPrincipal CustomUserDetails userDetails) {
    return () -> ResponseEntity.ok(userProfileService.updateUserProfile(userDetails.userId(), userProfileReq));
  }

}

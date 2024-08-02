package vn.tutor.core.controller;

import java.util.concurrent.Callable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.tutor.core.dto.request.UserProfileReq;
import vn.tutor.core.dto.response.UserProfileResp;
import vn.tutor.core.service.UserProfileService;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class UserProfileController {
  private final UserProfileService userProfileService;

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public Callable<ResponseEntity<UserProfileResp>> retrieveCurrentUserProfile(@AuthenticationPrincipal String userId) {
    return () -> ResponseEntity.ok(userProfileService.retrieveUserProfile(userId));
  }

  @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public Callable<ResponseEntity<UserProfileResp>> updateCurrentUserProfile(
      @RequestBody UserProfileReq userProfileReq, @AuthenticationPrincipal String userId) {
    return () -> ResponseEntity.ok(userProfileService.updateUserProfile(userId, userProfileReq));
  }

}

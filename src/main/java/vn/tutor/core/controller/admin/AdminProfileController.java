package vn.tutor.core.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.tutor.core.dto.request.UserProfileReq;
import vn.tutor.core.dto.response.PagingResponse;
import vn.tutor.core.dto.response.UserProfileResp;
import vn.tutor.core.service.UserProfileService;

@RestController
@RequestMapping("/api/admin/profiles")
@PreAuthorize("hasAnyAuthority('SUPER_ADMIN', 'OPERATOR')")
@RequiredArgsConstructor
public class AdminProfileController {
  private final UserProfileService userProfileService;

  @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<PagingResponse<UserProfileResp>> retrieveUserProfiles(@RequestParam(required = false) String email,
                                                                              @RequestParam(required = false) String name,
                                                                              @RequestParam(required = false, defaultValue = "0") int pageNum,
                                                                              @RequestParam(required = false, defaultValue = "10") int pageSize) {
    return ResponseEntity.ok(userProfileService.retrieveNormalUserProfiles(email, name, pageNum, pageSize));
  }

  @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<UserProfileResp> updateUserProfile(@PathVariable String id, @RequestBody UserProfileReq profileReq) {
    return ResponseEntity.ok(userProfileService.updateNormalUserProfile(id, profileReq));
  }

}

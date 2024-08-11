package vn.tutor.core.controller;

import java.util.concurrent.Callable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.tutor.core.common.Constant;
import vn.tutor.core.dto.request.TutorReq;
import vn.tutor.core.dto.response.TutorResp;
import vn.tutor.core.service.TutorService;
import vn.tutor.core.validation.RequiredHeaders;

@RestController
@RequestMapping("/api/tutor")
@PreAuthorize("hasAnyAuthority('TUTOR')")
@RequiredArgsConstructor
public class TutorController {

  private final TutorService tutorService;

  @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
  public Callable<ResponseEntity<TutorResp>> getTutor(
      @RequestHeader @RequiredHeaders(headers = {Constant.X_CORRELATION_ID}) HttpHeaders headers,
      @AuthenticationPrincipal String userId) {
    return () -> ResponseEntity.ok(tutorService.retrieveTutorByUserId(userId));
  }

  @PutMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public Callable<ResponseEntity<TutorResp>> updateTutor(
      @RequestHeader @RequiredHeaders(headers = {Constant.X_CORRELATION_ID}) HttpHeaders headers,
      @RequestBody TutorReq tutorReq,
      @AuthenticationPrincipal String userId) {
    return () -> ResponseEntity.ok(tutorService.updateTutor(tutorReq, userId));
  }
}

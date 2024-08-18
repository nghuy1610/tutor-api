package vn.tutor.core.controller;

import jakarta.validation.Valid;
import java.util.List;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.tutor.core.common.Constant;
import vn.tutor.core.dto.request.TutorReq;
import vn.tutor.core.dto.response.PagingResponse;
import vn.tutor.core.dto.response.TutorResp;
import vn.tutor.core.security.CustomUserDetails;
import vn.tutor.core.service.TutorService;
import vn.tutor.core.validation.RequiredHeaders;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TutorController {

  private final TutorService tutorService;

  @PreAuthorize("hasAnyAuthority('TUTOR')")
  @GetMapping(path = "/tutor", produces = MediaType.APPLICATION_JSON_VALUE)
  public Callable<ResponseEntity<TutorResp>> getTutor(
      @RequestHeader @RequiredHeaders(headers = {Constant.X_CORRELATION_ID}) HttpHeaders headers,
      @AuthenticationPrincipal CustomUserDetails userDetails) {
    return () -> ResponseEntity.ok(tutorService.retrieveTutorByUserId(userDetails.userId()));
  }

  @PreAuthorize("hasAnyAuthority('TUTOR')")
  @PutMapping(path = "/tutor", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public Callable<ResponseEntity<TutorResp>> updateTutor(
      @RequestHeader @RequiredHeaders(headers = {Constant.X_CORRELATION_ID}) HttpHeaders headers,
      @RequestBody @Valid TutorReq tutorReq,
      @AuthenticationPrincipal CustomUserDetails userDetails) {
    return () -> ResponseEntity.ok(tutorService.updateTutor(tutorReq, userDetails.userId()));
  }

  @GetMapping(value = "/tutors", produces = MediaType.APPLICATION_JSON_VALUE)
  public Callable<ResponseEntity<PagingResponse<TutorResp>>> getTutors(
      @RequestHeader @RequiredHeaders(headers = {Constant.X_CORRELATION_ID}) HttpHeaders headers,
      @RequestParam(required = false, defaultValue = "") List<String> specialtyIds,
      @RequestParam(required = false, defaultValue = "") List<String> addresses,
      @RequestParam(required = false, defaultValue = "0") int pageNum,
      @RequestParam(required = false, defaultValue = "10") int pageSize
  ) {
    return () -> ResponseEntity.ok(tutorService.retrieveTutors(specialtyIds, addresses, pageNum, pageSize));
  }
}

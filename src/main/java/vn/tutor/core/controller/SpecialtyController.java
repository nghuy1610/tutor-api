package vn.tutor.core.controller;

import jakarta.validation.Valid;
import java.util.concurrent.Callable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.tutor.core.common.Constant;
import vn.tutor.core.dto.request.SpecialtyDemandReq;
import vn.tutor.core.dto.response.PagingResponse;
import vn.tutor.core.dto.response.SpecialtyDemandResp;
import vn.tutor.core.dto.response.SpecialtyResp;
import vn.tutor.core.service.SpecialtyService;
import vn.tutor.core.validation.RequiredHeaders;

@RestController
@RequestMapping("/api/specialties")
@RequiredArgsConstructor
public class SpecialtyController {

  private final SpecialtyService specialtyService;

  @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
  public Callable<ResponseEntity<PagingResponse<SpecialtyResp>>> getSpecialties(
      @RequestHeader @RequiredHeaders(headers = {Constant.X_CORRELATION_ID}) HttpHeaders headers,
      @RequestParam(required = false, defaultValue = "0") int pageNum,
      @RequestParam(required = false, defaultValue = "10") int pageSize) {
    return () -> ResponseEntity.ok(specialtyService.retrieveAll(pageNum, pageSize));
  }

  @PostMapping(path = "/demands", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public Callable<ResponseEntity<SpecialtyDemandResp>> requestSpecialty(
      @RequestHeader @RequiredHeaders(headers = {Constant.X_CORRELATION_ID}) HttpHeaders headers,
      @RequestBody @Valid SpecialtyDemandReq req) {
    return () -> ResponseEntity.ok(specialtyService.createNewSpecialtyRequest(req));
  }
}

package vn.tutor.core.controller.admin;

import java.util.concurrent.Callable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.tutor.core.dto.request.SpecialtyDemandAssessmentReq;
import vn.tutor.core.dto.request.SpecialtyReq;
import vn.tutor.core.dto.response.PagingResponse;
import vn.tutor.core.dto.response.SpecialtyDemandResp;
import vn.tutor.core.dto.response.SpecialtyResp;
import vn.tutor.core.service.SpecialtyService;

@RestController
@RequestMapping("/api/admin/specialties")
@PreAuthorize("hasAnyAuthority('SUPER_ADMIN', 'OPERATOR')")
@RequiredArgsConstructor
public class AdminSpecialtyController {

  private final SpecialtyService specialtyService;

  @PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public Callable<ResponseEntity<SpecialtyResp>> addSpecialty(@RequestBody SpecialtyReq specialty) {
    return () -> ResponseEntity.ok(specialtyService.createSpecialty(specialty));
  }

  @GetMapping(path = "/demands", produces = MediaType.APPLICATION_JSON_VALUE)
  public Callable<ResponseEntity<PagingResponse<SpecialtyDemandResp>>> retrieveSpecialtyDemands(
      @RequestParam String status,
      @RequestParam(required = false, defaultValue = "0") int pageNum,
      @RequestParam(required = false, defaultValue = "10") int pageSize) {
    return () -> ResponseEntity.ok(specialtyService.retrieveSpecialtyDemandRequest(status, pageNum, pageSize));
  }

  @PutMapping(path = "/demands/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Callable<ResponseEntity<SpecialtyDemandResp>> updateSpecialtyDemand(
      @PathVariable String id,
      @RequestBody SpecialtyDemandAssessmentReq request) {
    return () -> ResponseEntity.ok(specialtyService.updateSpecialtyDemand(id, request));
  }
}

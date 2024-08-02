package vn.tutor.core.controller.admin;

import java.util.concurrent.Callable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.tutor.core.dto.request.SpecialtyReq;
import vn.tutor.core.dto.response.SpecialtyResp;
import vn.tutor.core.service.SpecialtyService;

@RestController
@RequestMapping("/api/admin/specialties")
@PreAuthorize("hasAnyAuthority('SUPER_ADMIN', 'OPERATOR')")
@RequiredArgsConstructor
public class AdminSpecialtyController {

  private final SpecialtyService specialtyService;

  @PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public Callable<ResponseEntity<SpecialtyResp>> batchSpecialties(@RequestBody SpecialtyReq specialty) {
    return () -> ResponseEntity.ok(specialtyService.createSpecialty(specialty));
  }
}

package vn.tutor.core.controller;

import java.util.List;
import java.util.concurrent.Callable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.tutor.core.dto.response.SpecialtyResp;
import vn.tutor.core.service.SpecialtyService;

@RestController
@RequestMapping("/api/specialties")
@RequiredArgsConstructor
public class SpecialtyController {

  private final SpecialtyService specialtyService;

  @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
  public Callable<ResponseEntity<List<SpecialtyResp>>> getSpecialties() {
    return () -> ResponseEntity.ok(specialtyService.retrieveAll());
  }
}

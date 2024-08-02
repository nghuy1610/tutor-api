package vn.tutor.core.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.tutor.core.dto.request.SpecialtyReq;
import vn.tutor.core.dto.response.SpecialtyResp;
import vn.tutor.core.entity.Specialty;
import vn.tutor.core.repository.SpecialtyRepository;

@Service
@RequiredArgsConstructor
public class SpecialtyService {

  private final SpecialtyRepository specialtyRepository;

  List<Specialty> findSpecialties(List<String> ids) {
    return specialtyRepository.findAllById(ids);
  }

  public List<SpecialtyResp> retrieveAll() {
    return specialtyRepository.findAll().stream().map(SpecialtyResp::from).toList();
  }

  public SpecialtyResp createSpecialty(SpecialtyReq specialtyReq) {
    return SpecialtyResp.from(specialtyRepository.save(Specialty.from(specialtyReq)));
  }
}

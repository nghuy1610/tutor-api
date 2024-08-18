package vn.tutor.core.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import vn.tutor.core.dto.request.SpecialtyDemandAssessmentReq;
import vn.tutor.core.dto.request.SpecialtyDemandReq;
import vn.tutor.core.dto.request.SpecialtyReq;
import vn.tutor.core.dto.response.PagingResponse;
import vn.tutor.core.dto.response.SpecialtyDemandResp;
import vn.tutor.core.dto.response.SpecialtyResp;
import vn.tutor.core.entity.Specialty;
import vn.tutor.core.entity.SpecialtyDemand;
import vn.tutor.core.enums.SpecialtyDemandStatus;
import vn.tutor.core.exception.ResourceNotFoundException;
import vn.tutor.core.repository.SpecialtyDemandRepository;
import vn.tutor.core.repository.SpecialtyRepository;

@Service
@RequiredArgsConstructor
public class SpecialtyService {

  private final SpecialtyRepository specialtyRepository;
  private final SpecialtyDemandRepository specialtyDemandRepository;

  List<Specialty> findSpecialties(List<String> ids) {
    return specialtyRepository.findAllById(ids);
  }

  public PagingResponse<SpecialtyResp> retrieveAll(int pageNum, int pageSize) {
    Page<Specialty> pageSpecialty = specialtyRepository.findAll(PageRequest.of(pageNum, pageSize));
    List<SpecialtyResp> specialtyRespList = pageSpecialty.stream().map(SpecialtyResp::from).toList();
    return new PagingResponse<>(specialtyRespList, pageSpecialty.getNumber(), pageSpecialty.getTotalPages(), pageSpecialty.getTotalElements());
  }

  public List<SpecialtyResp> autoCompleteSpecialtyByName(String searchName) {
    return specialtyRepository.findFirstTenByNameLikeOrderByUpdatedOnDesc(searchName).stream().map(SpecialtyResp::from).toList();
  }

  public SpecialtyResp createSpecialty(SpecialtyReq specialtyReq) {
    return SpecialtyResp.from(specialtyRepository.save(Specialty.from(specialtyReq)));
  }

  public SpecialtyDemandResp createNewSpecialtyRequest(SpecialtyDemandReq req) {
    return SpecialtyDemandResp.from(specialtyDemandRepository.save(SpecialtyDemand.from(req)));
  }

  public PagingResponse<SpecialtyDemandResp> retrieveSpecialtyDemandRequest(String status, int pageNum, int pageSize) {
    Page<SpecialtyDemand> pageSpecialDemand
        = specialtyDemandRepository.findPagingByStatus(SpecialtyDemandStatus.valueOf(status), PageRequest.of(pageNum, pageSize));
    List<SpecialtyDemandResp> specialtyDemandRespList = pageSpecialDemand.stream().map(SpecialtyDemandResp::from).toList();
    return new PagingResponse<>(specialtyDemandRespList, pageSpecialDemand.getNumber(), pageSpecialDemand.getTotalPages(),
                                pageSpecialDemand.getTotalElements());
  }

  public SpecialtyDemandResp updateSpecialtyDemand(String id, SpecialtyDemandAssessmentReq request) {
    SpecialtyDemand specialtyDemand = specialtyDemandRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Specialty not found by id = " + id));
    SpecialtyDemandStatus currentStatus = specialtyDemand.getStatus();
    specialtyDemand.apply(request);
    if (!currentStatus.equals(SpecialtyDemandStatus.APPROVED) && specialtyDemand.getStatus().equals(SpecialtyDemandStatus.APPROVED)) {
      createSpecialty(new SpecialtyReq(specialtyDemand.getSpecialtyName()));
    }
    return SpecialtyDemandResp.from(specialtyDemandRepository.save(specialtyDemand));
  }
}

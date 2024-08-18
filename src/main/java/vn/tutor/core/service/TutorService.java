package vn.tutor.core.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.tutor.core.dto.request.TutorReq;
import vn.tutor.core.dto.response.PagingResponse;
import vn.tutor.core.dto.response.TutorResp;
import vn.tutor.core.entity.Specialty;
import vn.tutor.core.entity.Tutor;
import vn.tutor.core.entity.TutorSpecialty;
import vn.tutor.core.entity.User;
import vn.tutor.core.enums.VerificationStatus;
import vn.tutor.core.exception.ResourceNotFoundException;
import vn.tutor.core.repository.TutorRepository;
import vn.tutor.core.repository.TutorSpecialtyRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class TutorService {
  private final TutorRepository tutorRepository;
  private final TutorSpecialtyRepository tutorSpecialtyRepository;
  private final SpecialtyService specialtyService;

  public Tutor createTutor(User user) {
    Tutor tutor = new Tutor();
    tutor.setUser(user);
    tutor.setVerificationStatus(VerificationStatus.PENDING);
    return tutorRepository.save(tutor);
  }

  public TutorResp retrieveTutorByUserId(String userId) {
    Tutor tutor = tutorRepository.findFullByUserId(userId)
        .orElseThrow(() -> new ResourceNotFoundException("Tutor data not found"));
    return TutorResp.from(tutor);
  }

  public PagingResponse<TutorResp> retrieveTutors(List<String> specialtyIds, List<String> addresses, int pageNum,
                                                  int pageSize) {
    Page<Tutor> pageTutor = tutorRepository.findTutorBySpecialtiesAndAddresses(specialtyIds, addresses, pageNum, pageSize);
    List<TutorResp> tutorRespList = pageTutor.getContent().stream().map(TutorResp::from).toList();
    return new PagingResponse<>(tutorRespList, pageTutor.getNumber(), pageTutor.getTotalPages(), pageTutor.getTotalElements());
  }

  public TutorResp updateTutor(TutorReq tutorReq, String userId) {
    Tutor tutor = tutorRepository.findFullByUserId(userId)
        .orElseThrow(() -> new ResourceNotFoundException("Tutor data not found"));
    tutor.apply(tutorReq);
    List<TutorSpecialty> removedTutorSpecialties = tutor.getTutorSpecialties().stream()
        .filter(tutorSpecialty -> !tutorReq.specialties().contains(tutorSpecialty.getSpecialty().getId()))
        .toList();
    tutor.getTutorSpecialties().removeAll(removedTutorSpecialties);
    tutorSpecialtyRepository.deleteAll(removedTutorSpecialties);
    List<String> currentSpecialtyIds = tutor.getTutorSpecialties().stream().map(ts -> ts.getSpecialty().getId()).toList();
    List<String> newSpecialtyIds = tutorReq.specialties().stream().filter(s -> !currentSpecialtyIds.contains(s)).toList();
    List<Specialty> specialties = specialtyService.findSpecialties(newSpecialtyIds);
    List<TutorSpecialty> tutorSpecialties = specialties.stream().map(s -> new TutorSpecialty(tutor, s)).toList();
    tutor.getTutorSpecialties().addAll(tutorSpecialties);
    return TutorResp.from(tutor);
  }
}

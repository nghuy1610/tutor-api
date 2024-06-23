package vn.tutor.core.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.tutor.core.entity.Tutor;
import vn.tutor.core.entity.User;
import vn.tutor.core.repository.TutorRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class TutorService {
  private final TutorRepository tutorRepository;

  public Tutor createTutor(User user) {
    Tutor tutor = new Tutor();
    tutor.setUser(user);
    return tutorRepository.save(tutor);
  }
}

package vn.tutor.core.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.tutor.core.entity.Student;
import vn.tutor.core.entity.User;
import vn.tutor.core.repository.StudentRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class StudentService {
  private final StudentRepository studentRepository;

  public Student createStudent(User user) {
    Student student = new Student();
    student.setUser(user);
    return studentRepository.save(student);
  }
}

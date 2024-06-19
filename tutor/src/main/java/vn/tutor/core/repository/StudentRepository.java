package vn.tutor.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.tutor.core.entity.Student;

public interface StudentRepository extends JpaRepository<Student, String> {

}

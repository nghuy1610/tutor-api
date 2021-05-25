package vn.tutor.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.tutor.core.entity.User;

public interface UserRepository extends JpaRepository<User, String> {
}

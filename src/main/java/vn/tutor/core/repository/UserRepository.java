package vn.tutor.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.tutor.core.entity.User;

public interface UserRepository extends JpaRepository<User, String> {

  @Query("""
      select u from User u\s
      left join fetch u.userPermissions
      where u.email = :email
      """)
  User findFullUserByEmail(String email);
}

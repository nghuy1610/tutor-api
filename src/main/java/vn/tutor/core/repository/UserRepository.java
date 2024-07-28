package vn.tutor.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.tutor.core.entity.User;

public interface UserRepository extends JpaRepository<User, String> {

  @Query("""
      select u from User u
      left join fetch u.userProfile
      left join fetch u.userPermissions up
      left join fetch up.permission
      where u.email = :email
      """)
  User findFullUserByEmail(String email);
}

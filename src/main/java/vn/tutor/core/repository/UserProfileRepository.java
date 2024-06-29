package vn.tutor.core.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.tutor.core.entity.UserProfile;

public interface UserProfileRepository extends JpaRepository<UserProfile, String> {
  @Query("select up from UserProfile up where up.user.id = :userId")
  Optional<UserProfile> findByUserId(String userId);
}

package vn.tutor.core.repository.specification;

import jakarta.persistence.criteria.JoinType;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import vn.tutor.core.entity.Permission_;
import vn.tutor.core.entity.UserPermission_;
import vn.tutor.core.entity.UserProfile;
import vn.tutor.core.entity.UserProfile_;
import vn.tutor.core.entity.User_;
import vn.tutor.core.enums.PermissionType;

@Component
public class UserProfileSpecification {
  public Specification<UserProfile> hasEmail(String email) {
    return (root, query, builder) -> builder.like(root.get(UserProfile_.email), "%" + email + "%");
  }

  public Specification<UserProfile> hasName(String name) {
    return (root, query, builder) -> {
      String namePattern = name + "%";
      return builder.or(builder.like(root.get(UserProfile_.firstName), namePattern),
          builder.like(root.get(UserProfile_.lastName), namePattern));
    };
  }

  public Specification<UserProfile> isNormalUser() {
    return (root, query, builder) -> root.join(UserProfile_.user, JoinType.INNER)
                                         .join(User_.userPermissions, JoinType.INNER)
                                         .join(UserPermission_.permission, JoinType.INNER)
                                         .get(Permission_.permissionType)
                                         .in(List.of(PermissionType.TUTOR.name(), PermissionType.STUDENT.name()));
  }
}
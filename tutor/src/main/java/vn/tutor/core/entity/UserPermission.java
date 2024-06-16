package vn.tutor.core.entity;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

@Entity
@Getter
@Setter
public class UserPermission extends BaseEntity {
    @ManyToOne
    private User user;
    @ManyToOne
    private Permission permission;

    public static UserPermissionBuilder builder() {
        return new UserPermissionBuilder();
    }

    public static class UserPermissionBuilder {
        private User user;
        private Permission permission;

        public UserPermissionBuilder user(User user) {
            this.user = user;
            return this;
        }

        public UserPermissionBuilder permission(Permission permission) {
            this.permission = permission;
            return this;
        }

        public UserPermission build() {
            UserPermission userPermission = new UserPermission();
            userPermission.setPermission(permission);
            userPermission.setUser(user);
            return userPermission;
        }
    }
}

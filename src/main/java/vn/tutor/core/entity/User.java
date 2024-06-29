package vn.tutor.core.entity;

import lombok.*;

import jakarta.persistence.*;
import java.util.List;
import vn.tutor.core.dto.request.UserCreationReq;

@Entity
@Table(name = "user")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseEntity {
    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "reset_password_key")
    private String resetPasswordKey;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    private List<UserPermission> userPermissions;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private UserProfile userProfile;

    public static User from(UserCreationReq userCreationReq) {
        return User.builder()
            .email(userCreationReq.email())
            .password(userCreationReq.password())
            .build();
    }
}

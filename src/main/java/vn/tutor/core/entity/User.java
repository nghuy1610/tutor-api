package vn.tutor.core.entity;

import lombok.*;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "user")
@Getter
@Setter
public class User extends BaseEntity {
    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "reset_password_key")
    private String resetPasswordKey;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    List<UserPermission> userPermissions;
}

package vn.tutor.core.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
public class User extends BaseEntity {
    private String email;
    private String password;
    private String phoneNumber;
    private String resetPasswordKey;
    private String firstName;
    private String lastName;

    @ManyToMany
    @JoinTable(name = "user_permission", joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name="permission_id"))
    List<Permission> permission;
}

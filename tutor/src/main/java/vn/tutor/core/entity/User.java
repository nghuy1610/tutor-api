package vn.tutor.core.entity;

import lombok.*;

import javax.persistence.Entity;

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
}

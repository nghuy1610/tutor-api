package vn.tutor.core.entity;

import lombok.*;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
public class User extends BaseEntity {
    private String username;
    private String password;
    private String email;
    private String phoneNumber;
}
